package de.atns.common.gwt.base64;

import com.google.gwt.user.server.Base64Utils;

public class Base64 {
// -------------------------- STATIC METHODS --------------------------

    public static String encodeString(String data) {
        return Base64Utils.toBase64(data.getBytes());
    }

    public static String decodeString(String data) {
        return new String(Base64Utils.fromBase64(data));
    }
}