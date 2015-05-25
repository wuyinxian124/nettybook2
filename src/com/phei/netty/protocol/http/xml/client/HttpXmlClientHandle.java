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
package com.phei.netty.protocol.http.xml.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.phei.netty.protocol.http.xml.codec.HttpXmlRequest;
import com.phei.netty.protocol.http.xml.codec.HttpXmlResponse;
import com.phei.netty.protocol.http.xml.pojo.OrderFactory;

/**
 * @author Administrator
 * @date 2014年2月16日
 * @version 1.0
 */
public class HttpXmlClientHandle extends
	SimpleChannelInboundHandler<HttpXmlResponse> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
	HttpXmlRequest request = new HttpXmlRequest(null,
		OrderFactory.create(123));
	ctx.writeAndFlush(request);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
	cause.printStackTrace();
	ctx.close();
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx,
	    HttpXmlResponse msg) throws Exception {
	System.out.println("The client receive response of http header is : "
		+ msg.getHttpResponse().headers().names());
	System.out.println("The client receive response of http body is : "
		+ msg.getResult());
    }
}
