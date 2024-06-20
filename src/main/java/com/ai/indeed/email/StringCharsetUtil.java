package com.ai.indeed.email;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StringCharsetUtil {

    public static boolean isMessyCode(String str, Charset charset) {
        String decodedStr = new String(str.getBytes(charset), charset);
        return !decodedStr.equals(str);
    }

    public static void main(String[] args) {

        //String str = "乱码字符";
        String str = "��ͨ����-δ����3��-090165-20240130.zip";
        System.out.println(isMessyCode(str, StandardCharsets.UTF_8)); // 输出 false，表示不包含乱码字符
    }
}