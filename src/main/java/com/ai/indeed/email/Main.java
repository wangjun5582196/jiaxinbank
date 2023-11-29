package com.ai.indeed.email;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        // 添加示例数据
        Map<String, Object> map1 = new HashMap<>();
        map1.put("id", 1);
        map1.put("value", "其他");
        map1.put("证券代码", "11223");
        resultList.add(map1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("id", 2);
        map2.put("value", "本日新增");
        map2.put("证券代码", "22");
        resultList.add(map2);

        Map<String, Object> map3 = new HashMap<>();
        map3.put("id", 3);
        map3.put("value", "其他");
        map3.put("证券代码", "332");
        resultList.add(map3);

        Map<String, Object> map4 = new HashMap<>();
        map4.put("id", 4);
        map4.put("value", "本日新增");
        map4.put("证券代码", "21a2");
        resultList.add(map4);

        // 对resultList进行排序
        Collections.sort(resultList, (map11, map21) -> {
            String value1 = (String) map11.get("value");
            String value2 = (String) map21.get("value");

            // 判断是否为"本日新增"，是的话排在前面
            if (value1.equals("本日新增") && !value2.equals("本日新增")) {
                return -1; // map1排在前面
            } else if (!value1.equals("本日新增") && value2.equals("本日新增")) {
                return 1; // map2排在前面
            } else {
                return 0; // 保持原有顺序
            }
        });

        // 打印排序后的结果
        for (Map<String, Object> map : resultList) {
            System.out.println(map);
        }
    }
}
