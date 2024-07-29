package com.ai.indeed.email;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test5 {
    public static void main(String[] args)  {
        List<Map<String, String>> param = new ArrayList<>();

        Map<String, String> map1 = new HashMap<>();
        map1.put("name", "John");
        map1.put("age", "30");
        map1.put("gender", "Male");
        param.add(map1);

        Map<String, String> map2 = new HashMap<>();
        map2.put("name", "Jane");
        map2.put("age", "25");
        map2.put("gender", "Female");
        param.add(map2);

        System.out.println(JSON.toJSON(param));


    }
}
