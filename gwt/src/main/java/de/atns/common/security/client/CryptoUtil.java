package de.atns.common.security.client;

import com.googlecode.gwt.crypto.bouncycastle.InvalidCipherTextException;
import com.googlecode.gwt.crypto.client.TripleDesCipher;

/**
 * @author tbaum
 * @since 18.06.2010
 */
public class CryptoUtil {

    public static final byte[] DES_KEY = {
            37, 16, -88, -20, 110, 69, -38, -63, -122, -60, -85, 94, -45, 103, -17, 55, 97, 69, -111, 25, -85, -125, 16,
            49
    };

    public static String encrypt(final String l) throws InvalidCipherTextException {
        final TripleDesCipher cipher = new TripleDesCipher();
        cipher.setKey(DES_KEY);
        return cipher.encrypt(l);
    }

    public static String decrypt(final String l) throws InvalidCipherTextException {
        final TripleDesCipher cipher = new TripleDesCipher();
        cipher.setKey(DES_KEY);
        return cipher.decrypt(l);
    }
}
