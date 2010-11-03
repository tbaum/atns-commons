package de.atns.printing.renderer.zpl;

import de.atns.printing.Converter;
import de.atns.printing.document.AbstractElement;
import de.atns.printing.document.AbstractElement.Rotation;
import de.atns.printing.renderer.Renderer;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRenderer<E extends AbstractElement> implements Renderer<E> {
// ------------------------------ FIELDS ------------------------------

    protected static final Map<Rotation, String> rotations = new HashMap<Rotation, String>();

    protected DocumentRenderer dr;

// -------------------------- STATIC METHODS --------------------------

    static {
        rotations.put(Rotation.NORMAL, "^FWN");
        rotations.put(Rotation.ROTATED, "^FWR");
        rotations.put(Rotation.INVERTED, "^FWI");
        rotations.put(Rotation.BOTTOMUP, "^FWB");
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public AbstractRenderer(final DocumentRenderer dr) {
        this.dr = dr;
    }

// -------------------------- OTHER METHODS --------------------------

    public void appendEscaped(final StringBuffer buffer, final String txt) throws UnsupportedEncodingException {
        for (int i = 0; i < txt.length(); i++) {
            final char c = txt.charAt(i);
            if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                buffer.append(c);
            } else {
                final byte[] bb = txt.substring(i, i + 1).getBytes("850");
                buffer.append("_").append(Integer.toString((bb[0] + 256) & 0xff, 16));
            }
        }
    }

    protected StringBuffer getPositionString(final E element) {
        final StringBuffer buffer = new StringBuffer();
        final int resolution = this.dr.getResolution();
        final int x = Converter.convertMMToDots(element.getX(), resolution);
        final int y = Converter.convertMMToDots(element.getY(), resolution);
        buffer.append("^FO").append(x).append(",").append(y);
        return buffer;
    }

    public String getRotation(final Rotation rot) {
        return rotations.get(rot);
    }
}
