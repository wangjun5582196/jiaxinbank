package com.ai.indeed.licence.rsa;

import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyPair;

public class KeySaver {

    public static void saveKeyPairToFiles(KeyPair keyPair, String publicKeyFile, String privateKeyFile) throws IOException {
        String publicKeyString = KeyEncoder.encodePublicKey(keyPair.getPublic());
        String privateKeyString = KeyEncoder.encodePrivateKey(keyPair.getPrivate());

        try (FileWriter publicKeyWriter = new FileWriter(publicKeyFile);
             FileWriter privateKeyWriter = new FileWriter(privateKeyFile)) {
            publicKeyWriter.write(publicKeyString);
            privateKeyWriter.write(privateKeyString);
        }
    }
}
