package de.atns.shop.tray.action;

import com.google.inject.Inject;
import com.google.inject.servlet.LocalRequestScoped;
import de.atns.shop.tray.Action;
import de.atns.shop.tray.RemoteShopService;
import de.atns.shop.tray.data.BankBuchung;
import de.atns.shop.tray.data.Result;
import de.atns.shop.tray.data.ShopConfiguration;
import org.kapott.hbci.GV.HBCIJob;
import org.kapott.hbci.GV_Result.GVRKUms;
import org.kapott.hbci.callback.AbstractHBCICallback;
import org.kapott.hbci.manager.HBCIHandler;
import static org.kapott.hbci.manager.HBCIUtils.init;
import static org.kapott.hbci.manager.HBCIUtils.setParam;
import org.kapott.hbci.passport.HBCIPassport;
import org.kapott.hbci.passport.HBCIPassportPinTan;
import org.kapott.hbci.status.HBCIExecStatus;
import org.kapott.hbci.structures.Konto;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.*;

@LocalRequestScoped public class BankabgleichAction implements Action {
// ------------------------------ FIELDS ------------------------------

    private final RemoteShopService remote;
    private final Result result;
    private final String pin;
    private final String login;
    private final String blz;
    private final String kto;
    private final String password;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public BankabgleichAction(final ShopConfiguration configuration,
                                      final RemoteShopService remote,
                                      final Result result) {
        this.remote = remote;
        this.result = result;

        pin = configuration.getPin();
        login = configuration.getLogin();
        blz = configuration.getBlz();
        kto = configuration.getKto();

        password = createPassword();
    }

    private String createPassword() {
        final byte[] random = new byte[12];
        final Random rand = new Random();
        rand.nextBytes(random);
        return new BigInteger(random).toString(36);
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Action ---------------------

    @Override public boolean localOnly() {
        return true;
    }

    public void service() throws IOException {
        final List<BankBuchung> records = fetchRecords();
        final long[] res = remote.synchronizeRecords(kto, records);
        result.put("ok", res[0]);
        result.put("fail", res[1]);
    }

// -------------------------- OTHER METHODS --------------------------

    public List<BankBuchung> fetchRecords() {
        HBCIHandler hbciHandle = null;
        HBCIPassport passport = null;
        try {
            final String tempFileName = createTempFile1();
            passport = createPassport(tempFileName);
            hbciHandle = new HBCIHandler("220", passport);

            final HBCIJob hbciJob = hbciHandle.newJob("KUmsAll");
            hbciJob.setParam("my", getAccount(kto, passport));
            setQueryDate(hbciJob);
            hbciJob.addToQueue();

            final HBCIExecStatus ret = hbciHandle.execute();
            final GVRKUms result = (GVRKUms) hbciJob.getJobResult();

            if (!result.isOK()) {
                throw new RuntimeException("Job-Error:" + result.getJobStatus().getErrorString() + " Global Error:" + ret.getErrorString());
            }

            return createList(result);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching records",e);
        }
        finally {
            if (hbciHandle != null) {
                hbciHandle.close();
            }
            if (passport != null) {
                passport.close();
            }
        }
    }

    protected String createTempFile1() {
        try {
            final File result = File.createTempFile("pintanfile", null);
            result.delete();
            result.deleteOnExit();
            return result.getCanonicalPath();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    protected HBCIPassport createPassport(final String tempFileName) {
        init(new Properties(), new AbstractHBCICallback() {
            @Override
            public void callback(final HBCIPassport passport, final int reason, final String msg, final int dataType, final StringBuffer retData) {
                switch (reason) {
                    case NEED_PASSPHRASE_LOAD:
                        retData.replace(0, retData.length(), password);
                        return;
                    case NEED_PT_PIN:
                        retData.replace(0, retData.length(), pin);
                        return;
                    case NEED_COUNTRY:
                        // clear(retData);
                        // retData.append("DE");
                        return;
                    case NEED_BLZ:
                        retData.replace(0, retData.length(), blz);
                        return;
                    case NEED_HOST:
                        // clear(retData);
                        // retData.append(host);
                        return;

                    case NEED_FILTER:
                        // clear(retData);
                        // retData.append("None");
                        return;

                    case NEED_USERID:
                        retData.replace(0, retData.length(), login);
                        return;

                    case NEED_CUSTOMERID:
                        retData.replace(0, retData.length(), login);
                        return;

                    case NEED_PASSPHRASE_SAVE:
                        retData.replace(0, retData.length(), password);
                        return;

                    case NEED_CONNECTION:
                    case CLOSE_CONNECTION:
                        return;
                    default:
                        System.out.println(MessageFormat.format("unknown callback!{0}/{1}/{2}", reason, msg, dataType));
                        return;
                }
                //throw new RuntimeException(MessageFormat.format("unknown callback!{0}/{1}/{2}", reason, msg, dataType));
            }

            @Override
            @SuppressWarnings({"DesignForExtension"})
            public void log(final String msg, final int level, final Date date, final StackTraceElement trace) {
                // System.err.println(trace + " " + msg);
            }

            @Override
            @SuppressWarnings({"DesignForExtension"})
            public void status(final HBCIPassport passport, final int statusTag, final Object[] o) {
                //
            }
        });

        setParam("log.loglevel.default", "5");

        setParam("client.passport.default", "PinTan");
        setParam("client.passport.PinTan.filename", tempFileName);
        setParam("client.passport.PinTan.checkcert", "1");
        setParam("client.passport.PinTan.init", "1");
        setParam("client.passport.hbciversion.default", "220");   // ?? 210

        final HBCIPassport passport = HBCIPassportPinTan.getInstance();

        passport.clearBPD();
        passport.clearUPD();
        return passport;
    }

    private static Konto getAccount(final String kto, final HBCIPassport passport) {
        for (final Konto konto : passport.getAccounts()) {
            System.err.println("found " + konto.number);
        }
        for (final Konto konto : passport.getAccounts()) {
            if (konto.number.equals(kto)) {
                return konto;
            }
        }
        throw new RuntimeException("Konto Nicht Gefunden!");
    }

    protected void setQueryDate(final HBCIJob auszug) {
        final Date lastQuery = remote.getLastQuery();
        if (lastQuery != null) {
            final Calendar cal = GregorianCalendar.getInstance();
            cal.setTime(lastQuery);
            cal.add(Calendar.DATE, -4);
            auszug.setParam("startdate", cal.getTime());
        }
    }

    private List<BankBuchung> createList(final GVRKUms result) {
        final GVRKUms.UmsLine[] umsLines = result.getFlatData();
        final List<BankBuchung> records = new ArrayList<BankBuchung>();
        for (final GVRKUms.UmsLine line : umsLines) {
            records.add(new BankBuchung(line));
        }
        return records;
    }
}
