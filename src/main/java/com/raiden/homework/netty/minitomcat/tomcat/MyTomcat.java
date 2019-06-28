package com.raiden.homework.netty.minitomcat.tomcat;

import com.raiden.homework.netty.minitomcat.tomcat.handler.MyTomcatHandler;
import com.raiden.homework.netty.minitomcat.tomcat.http.MyRequest;
import com.raiden.homework.netty.minitomcat.tomcat.http.MyResponse;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Author: Raiden
 * Date: 2019/6/27
 */
public class MyTomcat {
    int port = 8080;

    public void start() {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
//                            // 服务端，对请求解码
//                            socketChannel.pipeline().addLast("http-decoder",
//                                    new HttpRequestDecoder());
//                            // 聚合器，把多个消息转换为一个单一的FullHttpRequest或是FullHttpResponse
//                            socketChannel.pipeline().addLast("http-aggregator",
//                                    new HttpObjectAggregator(65536));
//                            // 服务端，对响应编码
//                            socketChannel.pipeline().addLast("http-encoder",
//                                    new HttpResponseEncoder());
//                            // 块写入处理器
//                            socketChannel.pipeline().addLast("http-chunked",
//                                    new ChunkedWriteHandler());
                            socketChannel.pipeline().addLast(new HttpResponseEncoder());
                            socketChannel.pipeline().addLast(new HttpRequestDecoder());
                            socketChannel.pipeline().addLast(new MyTomcatHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128);
            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("端口:" + port + "监听已启动.");
            future.channel().closeFuture().sync();
        } catch (Exception e) {
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }

    public void process(Socket socket) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            MyRequest request = new MyRequest(inputStream);
            MyResponse response = new MyResponse(outputStream);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
