package com.ai.indeed.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/*
 * Netty的Server
 * 功能:接收client发送的消息并在消息的前缀加上一些内容后返回给客户端
 * */
public class NettyServer {
    private final int port;

    public static void main(String[] args) {
        int port = Constant.PORT;
        new NettyServer(port).start();
    }

    public NettyServer(int port) {
        this.port = port;
    }

    public void start() {
        // 创建两个事件循环组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 创建服务器启动对象
            ServerBootstrap bootstrap = new ServerBootstrap();

            // 设置两个处理器，用于接收和返回信息
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // 使用NioServerSocketChannel作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128) // 设置线程队列等待连接的个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // 设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 创建通道初始化对象，设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 给处理器添加对应的ChannelHandler
                            ch.pipeline().addLast(new StringDecoder()); // 添加字符串解码器
                            ch.pipeline().addLast(new StringEncoder()); // 添加字符串编码器
                            ch.pipeline().addLast(new ServerHandler()); // 添加服务处理器
                        }
                    });

            System.out.println("服务端启动，等待客户端连接...");
            // 绑定端口并启动服务
            ChannelFuture future = bootstrap.bind(8888).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭两个事件循环组，释放资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class ServerHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            System.out.println(Constant.CLIENT_PREFIX + msg); // 打印收到的客户端信息
//            ctx.writeAndFlush(Constant.SERVER_PREFIX + msg); // 将收到的信息加上前缀返回给客户端
        }
    }

}