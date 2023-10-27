package com.ai.indeed.email;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Test1000 {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String a="{\"fundId\":\"33,34,35\",\"fundIds\":\"33_FD33,34_FD34,35_FD35\",\"securityCode\":\"202001\",\"securityName\":\"测试1\",\"securityType\":\"6\",\"marketNo\":\"3\",\"userOperate\":\"\",\"dtimeOperate\":\"\",\"securityCodeOut\":\"45678\",\"securityNameOut\":\"\",\"calendarMarket\":\"\",\"pushModel\":\"1\",\"sourceFlag\":\"\"}";
        String b="[{\"fundIds\":\"33_FD33,34_FD34,35_FD35\",\"fundId\":\"33,34,35\",\"securityCode\":\"202001\",\"securityName\":\"测试1\",\"beginDate\":\"20231018\",\"endDate\":\"\",\"netSource\":\"2\",\"netFreq\":\"2\",\"truster\":\"\",\"securityType\":\"6\",\"marketNo\":\"3\",\"calendarMarket\":\"\",\"pushModel\":\"1\",\"nestedFundId\":\"\",\"nestedFundName\":\"\",\"nestedFundFlag\":\"0\",\"sourceFlag\":\"\"}]";
        System.out.println(URLEncoder.encode(a,"utf-8"));
        System.out.println(URLEncoder.encode(b,"utf-8"));

        Set<String> hashSet = new HashSet<>();

        System.out.println(hashSet.add("1"));
        System.out.println(hashSet.add("1"));


        List<Integer> longList = new ArrayList<>();


        for (int i = 0; i < 1100; i++) {
            longList.add(i+1);
        }
      //  System.out.println(longList.toString().trim().replace(" ",""));

        StringBuilder stringBuilder = new StringBuilder();



        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }

        list.forEach(v->{
            list.remove(2);
            list.remove(3);
            list.remove(4);

        });

        System.out.println(list);

    }




}
