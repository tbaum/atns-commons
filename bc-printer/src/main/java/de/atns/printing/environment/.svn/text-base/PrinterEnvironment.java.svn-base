package de.atns.printing.environment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrinterEnvironment {
// ------------------------------ FIELDS ------------------------------

    protected List<Printer> printers = new ArrayList<Printer>();

    private ImagePrinter imagePrinter = new ImagePrinter();

// --------------------- GETTER / SETTER METHODS ---------------------

    public ImagePrinter getImagePrinter() {
        return this.imagePrinter;
    }

    public void setImagePrinter(final ImagePrinter imagePrinter) {
        this.imagePrinter = imagePrinter;
    }

    public List<Printer> getPrinters() {
        return this.printers;
    }

// -------------------------- OTHER METHODS --------------------------

    /**
     * Adds a printer to the environment
     *
     * @param p
     */
    public void addPrinter(final Printer p) {
        if (!this.printers.contains(p))
            this.printers.add(p);
    }

    /**
     * Returns a <code>List</code> of printers which are capable of printing the given material
     *
     * @param material
     * @return
     */
    public List<Printer> getPrintersForMaterial(final String material) {
        final List<Printer> result = new ArrayList<Printer>();
        for (final Printer p : this.printers) {
            if (p.getMaterial().equals(getMaterialByName(material))) {
                result.add(p);
            }
        }
        return result;
    }

    public Material getMaterialByName(final String name) {
        try {
            return Material.valueOf(name);
        }
        catch (IllegalArgumentException e) {
            return Material.NONE;
        }
    }

    /**
     * Trys to print <code>label</code> on the <code>printer</code> <code>quantity</code> times
     *
     * @param label
     * @param printer
     * @param quantity
     * @return true if successfully rendered, false otherwise
     */
    public boolean print(final Label label, final Printer printer, final int quantity) {
        try {
            printer.getDevice().renderDocument(label.getDocument(), quantity);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public void reset() {
        this.printers.clear();
    }
}
