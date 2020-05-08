package io.netty.example;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import javax.xml.transform.Source;
import java.util.Date;


/**
 * this handler sometimes will refuse to work raising an IndexOutOfBoundsException
 * */
public class TimeClientHandler  extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("channelRead");
        // ByteBuf m = (ByteBuf) msg;
        // try {
        //     long currentTimeMillis = (m.readUnsignedInt() - 2208988800L) * 1000L;
        //     System.out.println(new Date(currentTimeMillis));
        //     ctx.close();
        // } finally {
        //     m.release();
        // }

        UnixTime m = (UnixTime)msg;
        System.out.println(m);
        ctx.close();
        // do not create ByteBuf, so do not call release()
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
