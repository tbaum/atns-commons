package de.atns.common.mail;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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
        specialTag.put("ouml", 'ö');
        specialTag.put("uuml", 'ü');
        specialTag.put("auml", 'ä');
        specialTag.put("Ouml", 'Ö');
        specialTag.put("Uuml", 'Ü');
        specialTag.put("Auml", 'Ä');
        specialTag.put("szlig", 'ß');
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

    public static String toPlainText(String html) {
        final Document document = Jsoup.parse(html);
        final Element body = document.normalise().body();
        return new Html2Text(body.toString()).getPlainText();
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
            StringBuilder specialTagValue = new StringBuilder();
            int tmpValue;
            while ((tmpValue = super.read()) != -1 && tmpValue != ';') {
                specialTagValue.append((char) tmpValue);
            }
            String s = specialTagValue.toString();

            if (s.isEmpty()) {
                return -1;
            }
            return specialTag.containsKey(s) ? specialTag.get(s) : '?';
//            return   (s.startsWith("#")) ? (char) Integer.parseInt(s.substring(1))
//                    new String(specialTagValue, 1, indice - 1)) : specialTag.get(
//                    new String(specialTagValue, 0, indice));
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
