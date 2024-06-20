package com.ai.indeed.email;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test5 {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        for (int i = 0; i < 100; i++) {
            System.out.println( Random.class.newInstance().nextInt(10)+1);
        }
    }
    public static void handleList(List<Long> testList){
        testList.add(2L);
    }
}
