package io.netty.example;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

// ByteToMessageDecoder is an implementation of ChannelInboundHandler
// ByteToMessageDecoder calls the decode() method with an internally maintained cumulative buffer 使用内部维护的累积缓冲区
// when new data is received.
// ByteToMessageDecoder will call decode() again when there is more data received.
public class TimeDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out){
        System.out.println("decode" + in.readableBytes());
        if (in.readableBytes() < 4) {
            return; // (3)
        }

        // decode() adds an object to out, it means decoded a message successfully.
        // ByteToMessageDecoder will discard the read part of the cumulative buffer.

        // out.add(in.readBytes(4));
        // produce a UnixTime instead of a ByteBuf. TimeClientHandler does not use ByteBuf anymore
        out.add(new UnixTime(in.readUnsignedInt()));
    }
}
