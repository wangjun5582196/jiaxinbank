package com.ai.indeed.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.util.Scanner;

/*
 * Netty的Client
 * 功能:向server发送控制台输入的消息，并接收server发回的消息并显示
 * */
public class NettyClient {
    private final String host;
    private final int port;

    public static void main(String[] args) throws Exception {
        String host = Constant.HOST;
        int port = Constant.PORT;

        new NettyClient(host, port).start();
    }

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception { // 定义一个名为start的方法，这个方法抛出Exception异常
        EventLoopGroup group = new NioEventLoopGroup(); // 创建一个NioEventLoopGroup对象，它负责处理I/O操作的多线程事件循环
        try { // 开始try-catch块，用于捕获可能的异常
            Bootstrap bootstrap = new Bootstrap(); // 创建一个Bootstrap对象，它是Netty应用程序的入口点
            bootstrap.group(group) // 设置EventLoopGroup，用于处理I/O操作
                    .channel(NioSocketChannel.class) // 指定用于通信的Channel类型
                    .handler(new ChannelInitializer<SocketChannel>() { // 添加一个ChannelInitializer，用于初始化新连接的Channel
                        @Override // 覆盖ChannelInitializer中的初始化方法
                        protected void initChannel(SocketChannel ch) throws Exception { // 初始化Channel
                            ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8)); // 添加一个StringDecoder，用于将字节流解码为字符串，使用UTF-8编码
                            ch.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8)); // 添加一个StringEncoder，用于将字符串编码为字节流，使用UTF-8编码
                            ch.pipeline().addLast(new ClientHandler()); // 添加一个ClientHandler，用于处理业务逻辑
                        }
                    });



//            Scanner scanner = new Scanner(System.in); // 创建一个Scanner对象，用于从控制台接收用户输入
//            while (true) { // 无限循环，直到用户输入exit命令
//                System.out.print("请输入信息（exit退出）:"); // 向控制台输出提示信息
//                String message = scanner.nextLine(); // 从控制台读取用户输入的行，并存储在message变量中
//                channel.writeAndFlush(message + "\n"); // 将用户输入的信息通过Channel发送到服务器，并在信息末尾添加换行符以保证服务器能正确接收信息
//                if (message.equals("exit")) { // 如果用户输入的信息是exit，则退出循环
//                    break;
//                }
//                Thread.sleep(1000); // 等待1秒，等待客户端接收并打印服务器发送的消息
//            }
//            scanner.close(); // 关闭Scanner对象，释放资源
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            SocketChannel channel = (SocketChannel) channelFuture.channel(); // 使用Bootstrap连接服务器，同步连接并获取到Channel
            for (int i = 0; i < 1000; i++) {
                channel.writeAndFlush("这是一个完成的测试消息");
            }
            channel.close(); // 关闭Channel对象，释放资源
        } finally { // finally块用于无论try块中的代码是否发生异常都会执行的操作
            group.shutdownGracefully(); // 优雅地关闭EventLoopGroup，释放资源
        }
    }

    private class ClientHandler extends SimpleChannelInboundHandler<String> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            System.out.println(Constant.CLIENT_PREFIX + msg);  // 将服务器的响应打印到控制台
        }
    }
}