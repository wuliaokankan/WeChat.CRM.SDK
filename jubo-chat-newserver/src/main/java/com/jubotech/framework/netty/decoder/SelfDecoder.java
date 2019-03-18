package com.jubotech.framework.netty.decoder;

import java.util.List;

import com.google.protobuf.MessageLite;

import Jubo.JuLiao.IM.Wx.Proto.TransportMessageOuterClass;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
 
public class SelfDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        while (in.readableBytes() > 4) { // 如果可读长度小于包头长度，退出。
            in.markReaderIndex();

            // 获取包头中的body长度
            byte b3 = in.readByte();
            byte b2 = in.readByte();
            byte b1 = in.readByte();
            byte b0 = in.readByte();

            int length = (b0&0xff)|((b1<<8)&0xff00)|((b2<<16)&0xff0000)|((b3<<24)&0xff000000);


            // 如果可读长度小于body长度，恢复读指针，退出。
            if (in.readableBytes() < length) {
                in.resetReaderIndex();
                return;
            }

            // 读取body
            ByteBuf bodyByteBuf = in.readBytes(length);

            byte[] array;
            int offset;

            int readableLen= bodyByteBuf.readableBytes();
            if (bodyByteBuf.hasArray()) {
                array = bodyByteBuf.array();
                offset = bodyByteBuf.arrayOffset() + bodyByteBuf.readerIndex();
            } else {
                array = new byte[readableLen];
                bodyByteBuf.getBytes(bodyByteBuf.readerIndex(), array, 0, readableLen);
                offset = 0;
            }

            //反序列化
            MessageLite result = decodeBody(array, offset, readableLen);
            out.add(result);
        }
    }

    public MessageLite decodeBody(byte[] array, int offset, int length) throws Exception {

            return TransportMessageOuterClass.TransportMessage.getDefaultInstance().
                    getParserForType().parseFrom(array, offset, length);

    }
}
