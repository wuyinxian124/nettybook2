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
package com.phei.netty.protocol.netty.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.phei.netty.protocol.netty.MessageType;
import com.phei.netty.protocol.netty.struct.Header;
import com.phei.netty.protocol.netty.struct.NettyMessage;

/**
 * @author Lilinfeng
 * @date 2014年3月15日
 * @version 1.0
 */
public class LoginAuthRespHandler extends ChannelHandlerAdapter {

    private Map<String, Boolean> nodeCheck = new ConcurrentHashMap<String, Boolean>();
    private String[] whitekList = { "127.0.0.1", "192.168.1.104" };

    /**
     * Calls {@link ChannelHandlerContext#fireChannelRead(Object)} to forward to
     * the next {@link ChannelHandler} in the {@link ChannelPipeline}.
     * 
     * Sub-classes may override this method to change behavior.
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
	    throws Exception {
	NettyMessage message = (NettyMessage) msg;

	// 如果是握手请求消息，处理，其它消息透传
	if (message.getHeader() != null
		&& message.getHeader().getType() == MessageType.LOGIN_REQ
			.value()) {
	    String nodeIndex = ctx.channel().remoteAddress().toString();
	    NettyMessage loginResp = null;
	    // 重复登陆，拒绝
	    if (nodeCheck.containsKey(nodeIndex)) {
		loginResp = buildResponse((byte) -1);
	    } else {
		InetSocketAddress address = (InetSocketAddress) ctx.channel()
			.remoteAddress();
		String ip = address.getAddress().getHostAddress();
		boolean isOK = false;
		for (String WIP : whitekList) {
		    if (WIP.equals(ip)) {
			isOK = true;
			break;
		    }
		}
		loginResp = isOK ? buildResponse((byte) 0)
			: buildResponse((byte) -1);
		if (isOK)
		    nodeCheck.put(nodeIndex, true);
	    }
	    System.out.println("The login response is : " + loginResp
		    + " body [" + loginResp.getBody() + "]");
	    ctx.writeAndFlush(loginResp);
	} else {
	    ctx.fireChannelRead(msg);
	}
    }

    private NettyMessage buildResponse(byte result) {
	NettyMessage message = new NettyMessage();
	Header header = new Header();
	header.setType(MessageType.LOGIN_RESP.value());
	message.setHeader(header);
	message.setBody(result);
	return message;
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
	    throws Exception {
	cause.printStackTrace();
	nodeCheck.remove(ctx.channel().remoteAddress().toString());// 删除缓存
	ctx.close();
	ctx.fireExceptionCaught(cause);
    }
}
