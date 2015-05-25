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
package com.phei.netty.protocol.udp;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.ThreadLocalRandom;

/**
 * @author lilinfeng
 * @date 2014年2月14日
 * @version 1.0
 */
public class ChineseProverbServerHandler extends
	SimpleChannelInboundHandler<DatagramPacket> {

    // 谚语列表
    private static final String[] DICTIONARY = { "只要功夫深，铁棒磨成针。",
	    "旧时王谢堂前燕，飞入寻常百姓家。", "洛阳亲友如相问，一片冰心在玉壶。", "一寸光阴一寸金，寸金难买寸光阴。",
	    "老骥伏枥，志在千里。烈士暮年，壮心不已!" };

    private String nextQuote() {
	int quoteId = ThreadLocalRandom.current().nextInt(DICTIONARY.length);
	return DICTIONARY[quoteId];
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, DatagramPacket packet)
	    throws Exception {
	String req = packet.content().toString(CharsetUtil.UTF_8);
	System.out.println(req);
	if ("谚语字典查询?".equals(req)) {
	    ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(
		    "谚语查询结果: " + nextQuote(), CharsetUtil.UTF_8), packet
		    .sender()));
	}
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
	    throws Exception {
	ctx.close();
	cause.printStackTrace();
    }
}
