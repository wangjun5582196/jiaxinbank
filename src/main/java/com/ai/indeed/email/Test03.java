package com.ai.indeed.email;

import cn.hutool.core.thread.ThreadFactoryBuilder;

import java.math.BigDecimal;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Test03 {

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            5,
            256,
            60L,
            TimeUnit.SECONDS,
            new PriorityBlockingQueue<>(1000),
            new ThreadFactoryBuilder().setNamePrefix("retry-task-pool-%d").build());
    public static void main(String[] args) {
        BigDecimal a = new BigDecimal("2.63");

        BigDecimal b = new BigDecimal("2.62");

        System.out.println(a.subtract(b));
    }
}
