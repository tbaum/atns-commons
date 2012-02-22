package de.atns.printing.environment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrinterEnvironment {

    protected List<Printer> printers = new ArrayList<Printer>();

    private ImagePrinter imagePrinter = new ImagePrinter();

    public ImagePrinter getImagePrinter() {
        return this.imagePrinter;
    }

    public void setImagePrinter(final ImagePrinter imagePrinter) {
        this.imagePrinter = imagePrinter;
    }

    public List<Printer> getPrinters() {
        return this.printers;
    }

    /**
     * Adds a printer to the environment
     */
    public void addPrinter(final Printer p) {
        if (!this.printers.contains(p))
            this.printers.add(p);
    }

    /**
     * Returns a <code>List</code> of printers which are capable of printing the given material
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
        } catch (IllegalArgumentException e) {
            return Material.NONE;
        }
    }

    /**
     * Trys to print <code>label</code> on the <code>printer</code> <code>quantity</code> times
     *
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
