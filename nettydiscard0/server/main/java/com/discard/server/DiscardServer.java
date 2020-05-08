package com.discard.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Discards any incoming data.
 */
public class DiscardServer {

    private int port;

    public DiscardServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        // accept incoming connection
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // handle the traffic of the accepted connection
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // set up the server using a Channel
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    // class NioServerSocketChannel, is used to instantiate a new channel
                    .channel(NioServerSocketChannel.class)
                    // help user configure new channel. most likely that you want to configure the ChannelPipeline
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            // configure the ChannelPipeline. add some handlers such as discardserverhandler
                            ch.pipeline().addLast(new DiscardServerHandler());
                        }
                    })
                    // for the NioServerSocketChannel that accepts incoming connection
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // for the channels accepted by NioServerSocketChannel
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync();

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        new DiscardServer(port).run();
    }
}