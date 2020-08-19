package ru.carSales.storage.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {
    private MessageDigest md5;
    private static Encryption ourInstance = new Encryption();

    public static Encryption getInstance() {
        return ourInstance;
    }

    public String encrypt(String element) {
        checkMD5();
        StringBuilder builder = new StringBuilder();
        byte[] bytes = this.md5.digest(element.getBytes());
        for (byte b : bytes) {
            builder.append(String.format("%02X", b));
        }
        return builder.toString();
    }

    private void checkMD5() {
        if (this.md5 == null) {
            try {
                this.md5 = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }
}

