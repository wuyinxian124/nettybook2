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
package com.phei.netty.protocol.http.xml.codec;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * @author Lilinfeng
 * @date 2014年3月1日
 * @version 1.0
 */
public class HttpXmlRequestDecoder extends
	AbstractHttpXmlDecoder<FullHttpRequest> {

    public HttpXmlRequestDecoder(Class<?> clazz) {
	this(clazz, false);
    }

    public HttpXmlRequestDecoder(Class<?> clazz, boolean isPrint) {
	super(clazz, isPrint);
    }

    @Override
    protected void decode(ChannelHandlerContext arg0, FullHttpRequest arg1,
	    List<Object> arg2) throws Exception {
	if (!arg1.getDecoderResult().isSuccess()) {
	    sendError(arg0, BAD_REQUEST);
	    return;
	}
	HttpXmlRequest request = new HttpXmlRequest(arg1, decode0(arg0,
		arg1.content()));
	arg2.add(request);
    }

    private static void sendError(ChannelHandlerContext ctx,
	    HttpResponseStatus status) {
	FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
		status, Unpooled.copiedBuffer("Failure: " + status.toString()
			+ "\r\n", CharsetUtil.UTF_8));
	response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
	ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
