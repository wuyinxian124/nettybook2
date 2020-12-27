package com.phei.netty.frame.msgpack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import org.msgpack.MessagePack;

/**
 * @author shanghang
 * @title: MsgPackEncoder
 * @projectName nettyStudy
 * @description: msg编码器
 * @date 2020.12.27-17:20
 */
@Slf4j
public class MsgPackEncoder extends MessageToByteEncoder<Object> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        log.error("encode---------------------------------------------------");
        MessagePack msgPack = new MessagePack();
        byte[] raw = msgPack.write(msg);
        out.writeBytes(raw);
    }
}
