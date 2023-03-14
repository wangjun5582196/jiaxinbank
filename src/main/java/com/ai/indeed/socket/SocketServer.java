package com.ai.indeed.socket;

import cn.hutool.core.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.util.Properties;
import java.util.concurrent.*;

public class SocketServer {
    private static final int THREAD_POOL_SIZE = 10;
    private  static int port;


    private static final int QUEUE_SIZE = 10000;
    private static final ExecutorService executor = new ThreadPoolExecutor(THREAD_POOL_SIZE, THREAD_POOL_SIZE, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(QUEUE_SIZE));
    public static void main(String[] args) throws IOException {
        Logger logger = LoggerFactory.getLogger(SocketServer.class);
        ServerSocket serverSocket = null;
        boolean listening = true;
        initParams();
        try {
            serverSocket = new ServerSocket(port);
            logger.info(DateUtil.now() + "  服务端已启动，等待客户端连接...");
        } catch (IOException e) {
            logger.error("无法在端口 4444 上启动服务端.");
            System.exit(-1);
        }

        while (listening) {
            try {
                executor.execute(new ServerTask(serverSocket.accept()));
            } catch (RejectedExecutionException e) {
                logger.error("线程池已满，无法处理新的任务.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        }
        executor.shutdown();
        serverSocket.close();
    }

    public static void initParams() throws IOException {
        InputStream in = new FileInputStream("properties/param.properties");
        Properties ps = new Properties();
        try {
            ps.load(in);
            port = Integer.valueOf(ps.getProperty("port"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }
}

