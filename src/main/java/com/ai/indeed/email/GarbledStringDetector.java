package com.ai.indeed.email;

import java.nio.charset.StandardCharsets;

public class GarbledStringDetector {
    public static boolean isGarbled(String input,String chatSet) {
        try {
            // Try decoding the string with UTF-8 encoding
            byte[] bytes = input.getBytes(chatSet);
            String decoded = new String(bytes, chatSet);

            // Check if the decoded string matches the original
            return !decoded.equals(input);
        } catch (Exception e) {
            // Exception occurred during decoding
            return true; // Treat as garbled
        }
    }

    public static void main(String[] args) {
        String testString = "�Զ�ת��:��ͨ����-δ����3��-090165-20240130";
        boolean garbled = isGarbled(testString,"gbk");
        System.out.println("Is string garbled? " + garbled);
    }
}
