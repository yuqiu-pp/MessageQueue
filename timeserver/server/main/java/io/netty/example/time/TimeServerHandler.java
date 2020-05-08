package io.netty.example.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    // channelActive() method will be invoked when a connection is established and ready to generate traffic
    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        // allocate a new buff via ctx.alloc.
        // to write a 32-bit integer, we need a ByteBuf whose capacity is at least 4 bytes
        final ByteBuf time = ctx.alloc().buffer(4);
        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));

        // ChannelFuture represents an I/O operation which has not yet occurred
        // therefore, need to call close() and notifies its listeners
        final ChannelFuture f = ctx.writeAndFlush(time);
        // How do wo get notifies when write request is finished ?
        // 1.create a new anonymous ChannelFutureListener which close the channel when operation is done
        // 2.simplify the code using a pre-defined listener ChannelFutureListener.CLOSE
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                assert f == future;
                ctx.close();
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
