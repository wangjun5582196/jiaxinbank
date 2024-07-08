package com.ai.indeed.licence.rsa;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.util.Base64;

public class RSADecryption {

    public static String decrypt(byte[] encryptedData, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedData = cipher.doFinal(encryptedData);
        return new String(decryptedData);
    }


    public static String decrypt2(String encryptedData, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedData = cipher.doFinal(encryptedBytes);
        return new String(decryptedData);
    }

}
