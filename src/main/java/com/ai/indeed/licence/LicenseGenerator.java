package com.ai.indeed.licence;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LicenseGenerator {

    public static String generateLicense(String macAddress) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(macAddress.getBytes());

        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        try {
            String macAddress = LicenceUtil.getMacAddress();
            String license = generateLicense(macAddress);
            System.out.println("Generated License: " + license);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
