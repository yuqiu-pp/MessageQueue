package io.netty.my;

import io.netty.channel.*;

public class ZhangServerHandler extends ChannelInboundHandlerAdapter {

    // channelActive() method will be invoked when a connection is established and ready to generate traffic
    // 服务端张大爷先说话
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ChannelFuture f = ctx.writeAndFlush((new Language()).getFirst());
        f.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }
}
