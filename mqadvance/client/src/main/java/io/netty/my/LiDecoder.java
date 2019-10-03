package io.netty.my;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class LiDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("decode" + in.readableBytes());
        if (in.readableBytes() < 4) {
            return;
        }
        int len = in.readInt();

        if (in.hasArray()){
            out.add(new String(in.array()));
        }else {
            byte[] bytes = new byte[in.readableBytes()];
            in.getBytes(in.readerIndex(), bytes);
            out.add(new String(bytes));
        }



    }
}
