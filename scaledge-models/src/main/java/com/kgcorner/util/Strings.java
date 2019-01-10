package com.kgcorner.util;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Strings {

    private Strings(){}
    /**
     * Checks whether string is empty or null
     * @param string String to check
     * @return false if null or empty and true otherwise
     */
    public static boolean isNullOrEmpty(String string) {
        return string == null || string.trim().length() == 0;
    }

    /**
     * Generates a random string of given length
     * @param length
     * @return
     */
    public static String generateRandomString(int length) {
        return new BigInteger(130, new SecureRandom()).toString(length);
    }
}
