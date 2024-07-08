package com.ai.indeed.licence.rsa;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class KeyEncoder {

    public static String encodePublicKey(PublicKey publicKey) {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    public static String encodePrivateKey(PrivateKey privateKey) {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }
}
