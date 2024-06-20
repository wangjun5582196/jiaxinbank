package com.ai.indeed.email;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test2 {
    public static void main(String[] args) {
        List<Map<Integer, String>> sheet = new ArrayList<>();
        Map<Integer, String> integerStringMap  = new HashMap<>();
        integerStringMap.put(1, "a");
        integerStringMap.put(2, "b");
        integerStringMap.put(3, "c");
        integerStringMap.put(4, "d");
        integerStringMap.put(5, null);
        sheet.add(integerStringMap);

        Map<Integer, String> integerStringMap2  = new HashMap<>();
        integerStringMap2.put(1, "a2");
        integerStringMap2.put(2, "b2");
        integerStringMap2.put(3, "c2");
        integerStringMap2.put(4, "d2");
        integerStringMap2.put(5, null);
        sheet.add(integerStringMap2);

        Map<Integer, String> integerStringMap3  = new HashMap<>();
        integerStringMap3.put(1, "a3");
        integerStringMap3.put(2, "b3");
        integerStringMap3.put(3, "c3");
        integerStringMap3.put(4, "d3");
        integerStringMap3.put(5, null);
        sheet.add(integerStringMap3);


        Map<Integer, String> integerStringMap4  = new HashMap<>();
        integerStringMap4.put(1, "a4");
        integerStringMap4.put(2, "b4");
        integerStringMap4.put(3, "c4");
        integerStringMap4.put(4, "d4");
        integerStringMap4.put(5, "2223334");
        sheet.add(integerStringMap4);

        String balance="";
        for (int i = 0; i < sheet.size(); i++) {
            Map<Integer, String> temp  = sheet.get(i);

            balance = temp.get(5) == null ? "" : temp.get(5);
            if ("".equals(balance)) {
                for(int j = i+1; j < sheet.size(); j++) {
                    balance = sheet.get(j).get(5) == null ? "" : sheet.get(j).get(5);
                    if (!"".equals(balance)) {
                        break;
                    }
                }
            }
            System.out.println(balance);

        }
    }
}
