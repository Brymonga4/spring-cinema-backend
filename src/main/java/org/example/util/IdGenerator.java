package org.example.util;

import java.util.Random;

public class IdGenerator {
    private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMERICS = "0123456789";
    private static final Random rnd = new Random();

    public static String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    public static String randomNumericString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(NUMERICS.charAt(rnd.nextInt(NUMERICS.length())));
        return sb.toString();
    }
}
