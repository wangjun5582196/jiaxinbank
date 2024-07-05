package com.ai.indeed.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TestBlockQueue {
    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1000);

        // 生产者线程
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 2000; i++) {
                    // 往队列中放入元素
                    queue.put(i);
                    System.out.println("生产者放入元素: " + i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 消费者线程
        Thread consumer = new Thread(() -> {
            try {
                Thread.sleep(3000); // 等待2秒，让生产者线程先行
                while (!queue.isEmpty()) {
                    // 从队列中取出元素并打印
                    int element = queue.take();
                    System.out.println("消费者取出元素: " + element);
                    Thread.sleep(1000); // 模拟消费者的处理时间
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 启动生产者和消费者线程
        producer.start();
        consumer.start();

    }
}
