package com.ai.indeed.email;


public class TestRepeat {
    public static void main(String[] args) {
        Integer startTime = 20231019;
        Integer endTime = 20251019;



        Integer [] testCase = new Integer[] {20251019,29991231};
        boolean matched = match(startTime, endTime, testCase[0], testCase[1]);
        System.out.println("重复申报:"+matched);
    }

    public static boolean match(Integer startTime1, Integer endTime1,Integer startTime2,Integer endTime2)
    {
        return !(startTime2>endTime1||startTime1>endTime2);
    }
}
