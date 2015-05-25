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
package com.phei.netty.protocol.http.xml.server;

import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.ArrayList;
import java.util.List;

import com.phei.netty.protocol.http.xml.codec.HttpXmlRequest;
import com.phei.netty.protocol.http.xml.codec.HttpXmlResponse;
import com.phei.netty.protocol.http.xml.pojo.Address;
import com.phei.netty.protocol.http.xml.pojo.Order;

/**
 * @author lilinfeng
 * @date 2014年2月14日
 * @version 1.0
 */
public class HttpXmlServerHandler extends
	SimpleChannelInboundHandler<HttpXmlRequest> {

    @Override
    public void messageReceived(final ChannelHandlerContext ctx,
	    HttpXmlRequest xmlRequest) throws Exception {
	HttpRequest request = xmlRequest.getRequest();
	Order order = (Order) xmlRequest.getBody();
	System.out.println("Http server receive request : " + order);
	dobusiness(order);
	ChannelFuture future = ctx.writeAndFlush(new HttpXmlResponse(null,
		order));
	if (!isKeepAlive(request)) {
	    future.addListener(new GenericFutureListener<Future<? super Void>>() {
		public void operationComplete(Future future) throws Exception {
		    ctx.close();
		}
	    });
	}
    }

    private void dobusiness(Order order) {
	order.getCustomer().setFirstName("狄");
	order.getCustomer().setLastName("仁杰");
	List<String> midNames = new ArrayList<String>();
	midNames.add("李元芳");
	order.getCustomer().setMiddleNames(midNames);
	Address address = order.getBillTo();
	address.setCity("洛阳");
	address.setCountry("大唐");
	address.setState("河南道");
	address.setPostCode("123456");
	order.setBillTo(address);
	order.setShipTo(address);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
	    throws Exception {
	cause.printStackTrace();
	if (ctx.channel().isActive()) {
	    sendError(ctx, INTERNAL_SERVER_ERROR);
	}
    }

    private static void sendError(ChannelHandlerContext ctx,
	    HttpResponseStatus status) {
	FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
		status, Unpooled.copiedBuffer("失败: " + status.toString()
			+ "\r\n", CharsetUtil.UTF_8));
	response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
	ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
