package com.phei.netty.frame.msgpack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * @author shanghang
 * @title: MsgPackDecoder
 * @projectName nettyStudy
 * @description: msgPack解码器
 * @date 2020.12.27-17:23
 */
@Slf4j
public class MsgPackDecoder extends MessageToMessageDecoder<ByteBuf> {
    /**
     * 从数据包msg中获取需要节码的byte数组，
     * 然后调用MessagePack的read方法将其反序列化为Object，
     * 将解码的对象加入到节码列表arg2中
     * @param ctx
     * @param msg
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        log.error("decode---------------------------------------------------");
        final byte[] array;
        final int length = msg.readableBytes();
        array = new byte[length];
        msg.getBytes(msg.readerIndex(),array,0,length);
        MessagePack msgPack = new MessagePack();
        out.add(msgPack.read(array));
    }
}
