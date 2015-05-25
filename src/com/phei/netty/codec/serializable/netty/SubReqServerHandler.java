/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.phei.netty.codec.serializable.netty;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import com.phei.netty.codec.pojo.SubscribeReq;
import com.phei.netty.codec.pojo.SubscribeResp;

/**
 * @author lilinfeng
 * @date 2014年2月14日
 * @version 1.0
 */
@Sharable
public class SubReqServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
	    throws Exception {
	SubscribeReq req = (SubscribeReq) msg;
	if ("Lilinfeng".equalsIgnoreCase(req.getUserName())) {
	    System.out.println("Service accept client subscrib req : ["
		    + req.toString() + "]");
	    ctx.writeAndFlush(resp(req.getSubReqID()));
	}
    }

    private SubscribeResp resp(int subReqID) {
	SubscribeResp resp = new SubscribeResp();
	resp.setSubReqID(subReqID);
	resp.setRespCode(0);
	resp.setDesc("Netty book order succeed, 3 days later, sent to the designated address");
	return resp;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
	cause.printStackTrace();
	ctx.close();// 发生异常，关闭链路
    }
}
