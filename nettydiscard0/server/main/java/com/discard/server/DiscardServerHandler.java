package com.discard.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * Handles a server-side channel.
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter { // (1)

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
        // Discard the received data silently.
        // ByteBuf is a reference-counted object, has to released
        // do release
        // ((ByteBuf) msg).release();


        ByteBuf in = (ByteBuf) msg;
        try {
            // in.Readable = System.out.println(in.toString(io.netty.util.CharsetUtil.US_ASCII)) inefficient loop
            while (in.isReadable()) {
                System.out.print((char) in.readByte());
                System.out.flush();
            }
        } finally {
            // do release
            ReferenceCountUtil.release(msg);
            // could do in.release()
        }


        // ChannelHandlerContext provides various operation, to trigger I/O events and operations
        // Netty will release msg when it is written out to the wire
        // ctx.write(msg);
        // ctx.flush();
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}