package de.atns.common.mail;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

import static java.util.Arrays.asList;

/**
 * @author tbaum
 * @since 26.05.11 20:02
 */
public class Html2Text extends StringReader {
// ------------------------------ FIELDS ------------------------------

    private static final HashMap<String, Character> specialTag = new HashMap<String, Character>();
    private static final HashMap<String, Character> basicTag = new HashMap<String, Character>();

    StringBuffer plainText = new StringBuffer();
    private boolean escapeCharacter = false;

// -------------------------- STATIC METHODS --------------------------

    static {
        specialTag.put("quot", '"');
        specialTag.put("amp", '&');
        specialTag.put("lt", '<');
        specialTag.put("gt", '>');
        specialTag.put("nbsp", ' ');
        for (String s : asList("br", "/br", "/p", "blockquote", "/blockquote", "pre", "/pre", "h1", "/h1", "h2", "/h2",
                "h3", "/h3", "h4", "/h4", "h5", "/h5", "h6", "/h6", "hr", "/hr", "img", "/img", "area", "/area", "map",
                "/map", "table", "/table", "/tr", "th", "/th", "ul", "/ul", "/li", "embed", "/embed")) {
            basicTag.put(s, '\n');
        }
        for (String s : asList("td", "li", "p")) {
            basicTag.put(s, '\t');
        }


        for (String s : asList("form", "input", "select", "option", "textarea")) {
            basicTag.put(s, ' ');
        }
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public Html2Text(String htmlString) {
        super(htmlString);


        int c;
        try {
            while ((c = this.read()) != -1) {
                plainText.append((char) c);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public int read() throws IOException {
        int c = super.read();

        while (c == (int) '<') {
            char[] basicTagValue = new char[20];
            int indice = 0;

            if ((basicTagValue[indice++] = (char) super.read()) == '!') {
                while (((char) super.read()) != '>') ;
                return read();
            }
            while ((basicTagValue[indice] = (char) super.read()) != '>' && basicTagValue[indice++] != ' ') ;
            if (basicTagValue[indice - 1] == ' ') {
                while (((char) super.read()) != '>') ;
            }
            Character specialTagValue = basicTag.get(new String(basicTagValue, 0, indice).toLowerCase());
            if (specialTagValue == null) {
                return read();
            }
            return specialTagValue;
        }

        if (c == '&') {
            char[] specialTagValue = new char[10];
            int tmpValue, indice = 0;
            while ((tmpValue = super.read()) != -1 && tmpValue != ';') {
                specialTagValue[indice++] = (char) tmpValue;
            }
            if (tmpValue == -1) {
                return -1;
            }
            return specialTagValue[0] != '#' ? specialTag.get(
                    new String(specialTagValue, 0, indice)) :
                    (char) Integer.parseInt(new String(specialTagValue, 1, indice - 1));
        }

        if (escapeCharacter && Character.isWhitespace((char) c)) {
            escapeCharacter = true;
            return read();
        } else if (Character.isWhitespace((char) c)) {
            escapeCharacter = true;
            c = ' ';
        } else {
            escapeCharacter = false;
        }

        return c;
    }

// -------------------------- OTHER METHODS --------------------------

    public String getPlainText() {
        return plainText.toString().replaceAll("\n*\n\n", "\n\n");
    }

    public int read(char[] tab, int off, int len) throws IOException {
        int indice = off;
        int c;
        while ((indice - off) < len) {
            c = read();
            if (c != -1) {
                tab[indice++] = (char) c;
            } else {
                return -1;
            }
        }
        return indice - off;
    }
}
