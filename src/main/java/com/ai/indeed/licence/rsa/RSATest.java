package com.ai.indeed.licence.rsa;

import java.io.FileWriter;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class RSATest {

    public static void main(String[] args) throws Exception {
//        // 生成密钥对
//        KeyPair keyPair = RSAKeyPairGenerator.generateKeyPair();
//        PublicKey publicKey = keyPair.getPublic();


        // 生成密钥对
        // 保存公钥和私钥到文件
       // KeySaver.saveKeyPairToFiles(keyPair, "publicKey.txt", "privateKey.txt");

        // 从文件读取公钥和私钥
        String licenseContent = DecodeUtil.readKeyFromFile("privateKey.txt");
        System.out.println(licenseContent);

        PrivateKey privateKey = DecodeUtil.decodePrivateKey(licenseContent);

        String publicKeyContent = DecodeUtil.readKeyFromFile("publicKey.txt");

        PublicKey publicKey = DecodeUtil.decodePublicKey(publicKeyContent);


        // 要加密的数据
        String originalData = "Hel大大啊111111111111111111111111111111111啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊lo, RSA!";

        // 使用公钥加密
        String encryptedData = RSAEncryption.encrypt2(originalData, publicKey);

        FileWriter writer = new FileWriter("drill.lic");
        writer.write(encryptedData);
        writer.flush();
//
        String licece = DecodeUtil.readKeyFromFile("drill.lic");

        String decryptedData = RSADecryption.decrypt2(licece, privateKey);

        System.out.println("Original Data: " + originalData);
        System.out.println("Decrypted Data: " + decryptedData);
    }
}
