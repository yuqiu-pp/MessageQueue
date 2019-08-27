package io.netty.my;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class LiClient {

    public static void main(String[] args) throws Exception {
        String host = "127.0.0.1"; //args[0];
        int port = 8080; //Integer.parseInt(args[1]);

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // Bootstrap is similar to serverBootstrap
            Bootstrap b = new Bootstrap();
            // only one EventLoopGroup, its used both as boss and work group.
            // but boss is not used for the client side
            b.group(workerGroup);
            // create a client-side channel
            b.channel(NioSocketChannel.class);
            // do not use childOption, because the client-side SocketChannel does not have a parent
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    // add more than one ChannelHandler
                    // TimeDecoder deals with the fragmentation issue, that raising an IndexOutOfBoundsException
                    ch.pipeline().addLast(new LiDecoder(), new LiClientHandler());
                    // 放入pipe的顺序，就是收到数据后的调用顺序
                }
            });

            // Start the client.
            ChannelFuture f = b.connect(host, port).sync();

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
