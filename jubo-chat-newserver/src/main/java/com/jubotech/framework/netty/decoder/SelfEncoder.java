package com.jubotech.framework.netty.decoder;

import com.google.protobuf.MessageLite;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
 
public class SelfEncoder extends MessageToByteEncoder<MessageLite> {

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageLite msg, ByteBuf out) throws Exception {

        byte[] body = msg.toByteArray();
        byte[] header = encodeHeader(msg, (short) body.length);

        out.writeBytes(header);
        out.writeBytes(body);

    }

    private byte[] encodeHeader(MessageLite msg, int value) {

        byte[] src = new byte[4];
        src[0] = (byte) ((value >> 24) & 0xFF);
        src[1] = (byte) ((value >> 16) & 0xFF);
        src[2] = (byte) ((value >> 8) & 0xFF);
        src[3] = (byte) (value & 0xFF);

        return src;

    }
}
