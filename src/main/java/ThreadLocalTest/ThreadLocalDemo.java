package ThreadLocalTest;

public class ThreadLocalDemo {
    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        // 创建两个线程
        Thread thread1 = new Thread(() -> {
            threadLocal.set(1); // 设置线程1的变量副本为1
            System.out.println("Thread 1: " + threadLocal.get()); // 获取线程1的变量副本
        });

        Thread thread2 = new Thread(() -> {
            threadLocal.set(2); // 设置线程2的变量副本为2
            System.out.println("Thread 2: " + threadLocal.get()); // 获取线程2的变量副本
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}
