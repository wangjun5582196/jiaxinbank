package com.ai.indeed.email;

import java.io.UnsupportedEncodingException;

public class Test {
    public static void main(String[] args) {
        String a="��ͨ����-δ����3��-090165-20240130.zip";
        System.out.println(isGarbled(a,"gbk"));
    }

    public static boolean hasNoSpecialCharacters(String str) {
        String pattern = "^[\\p{L}\\p{N}]+$";
        return str.matches(pattern);
    }



    public static boolean isGarbled(String str, String charset) {
        try {
            byte[] originalBytes = str.getBytes(charset);
            String decodedStr = new String(originalBytes, charset);
            return str.equals(decodedStr);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
    }

}
