package io.netty.my;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ZhangEncoder extends MessageToByteEncoder<String> {

    @Override
    protected void encode(ChannelHandlerContext ctx, String s, ByteBuf out) throws Exception {
        System.out.println("encode");
        // 4byte int len  + data
        out.writeInt(s.length());

        byte[] data = s.getBytes("UTF-8");
        // out = Unpooled.buffer(data.length);
        out.writeBytes(data);
    }
}
