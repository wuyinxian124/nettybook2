/*
 * Copyright 2013-2018 Lilinfeng.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.phei.netty.protocol.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.phei.netty.protocol.netty.struct.Header;
import com.phei.netty.protocol.netty.struct.NettyMessage;

/**
 * @author Administrator
 * @date 2014年3月15日
 * @version 1.0
 */
public class TestCodeC {

    MarshallingEncoder marshallingEncoder;
    MarshallingDecoder marshallingDecoder;

    public TestCodeC() throws IOException {
	marshallingDecoder = new MarshallingDecoder();
	marshallingEncoder = new MarshallingEncoder();
    }

    public NettyMessage getMessage() {
	NettyMessage nettyMessage = new NettyMessage();
	Header header = new Header();
	header.setLength(123);
	header.setSessionID(99999);
	header.setType((byte) 1);
	header.setPriority((byte) 7);
	Map<String, Object> attachment = new HashMap<String, Object>();
	for (int i = 0; i < 10; i++) {
	    attachment.put("ciyt --> " + i, "lilinfeng " + i);
	}
	header.setAttachment(attachment);
	nettyMessage.setHeader(header);
	nettyMessage.setBody("abcdefg-----------------------AAAAAA");
	return nettyMessage;
    }

    public ByteBuf encode(NettyMessage msg) throws Exception {
	ByteBuf sendBuf = Unpooled.buffer();
	sendBuf.writeInt((msg.getHeader().getCrcCode()));
	sendBuf.writeInt((msg.getHeader().getLength()));
	sendBuf.writeLong((msg.getHeader().getSessionID()));
	sendBuf.writeByte((msg.getHeader().getType()));
	sendBuf.writeByte((msg.getHeader().getPriority()));
	sendBuf.writeInt((msg.getHeader().getAttachment().size()));
	String key = null;
	byte[] keyArray = null;
	Object value = null;

	for (Map.Entry<String, Object> param : msg.getHeader().getAttachment()
		.entrySet()) {
	    key = param.getKey();
	    keyArray = key.getBytes("UTF-8");
	    sendBuf.writeInt(keyArray.length);
	    sendBuf.writeBytes(keyArray);
	    value = param.getValue();
	    marshallingEncoder.encode(value, sendBuf);
	}
	key = null;
	keyArray = null;
	value = null;
	if (msg.getBody() != null) {
	    marshallingEncoder.encode(msg.getBody(), sendBuf);
	} else
	    sendBuf.writeInt(0);
	sendBuf.setInt(4, sendBuf.readableBytes());
	return sendBuf;
    }

    public NettyMessage decode(ByteBuf in) throws Exception {
	NettyMessage message = new NettyMessage();
	Header header = new Header();
	header.setCrcCode(in.readInt());
	header.setLength(in.readInt());
	header.setSessionID(in.readLong());
	header.setType(in.readByte());
	header.setPriority(in.readByte());

	int size = in.readInt();
	if (size > 0) {
	    Map<String, Object> attch = new HashMap<String, Object>(size);
	    int keySize = 0;
	    byte[] keyArray = null;
	    String key = null;
	    for (int i = 0; i < size; i++) {
		keySize = in.readInt();
		keyArray = new byte[keySize];
		in.readBytes(keyArray);
		key = new String(keyArray, "UTF-8");
		attch.put(key, marshallingDecoder.decode(in));
	    }
	    keyArray = null;
	    key = null;
	    header.setAttachment(attch);
	}
	if (in.readableBytes() > 4) {
	    message.setBody(marshallingDecoder.decode(in));
	}
	message.setHeader(header);
	return message;
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
	TestCodeC testC = new TestCodeC();
	NettyMessage message = testC.getMessage();
	System.out.println(message + "[body ] " + message.getBody());

	for (int i = 0; i < 5; i++) {
	    ByteBuf buf = testC.encode(message);
	    NettyMessage decodeMsg = testC.decode(buf);
	    System.out.println(decodeMsg + "[body ] " + decodeMsg.getBody());
	    System.out
		    .println("-------------------------------------------------");

	}

    }

}
