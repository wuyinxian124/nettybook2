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

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 * @author lilinfeng
 * @date 2014年2月14日
 * @version 1.0
 */
public class ChineseProverbClient {

    public void run(int port) throws Exception {
	EventLoopGroup group = new NioEventLoopGroup();
	try {
	    Bootstrap b = new Bootstrap();
	    b.group(group).channel(NioDatagramChannel.class)
		    .option(ChannelOption.SO_BROADCAST, true)
		    .handler(new ChineseProverbClientHandler());
	    Channel ch = b.bind(0).sync().channel();
	    // 向网段内的所有机器广播UDP消息
	    ch.writeAndFlush(
		    new DatagramPacket(Unpooled.copiedBuffer("谚语字典查询?",
			    CharsetUtil.UTF_8), new InetSocketAddress(
			    "255.255.255.255", port))).sync();
	    if (!ch.closeFuture().await(15000)) {
		System.out.println("查询超时!");
	    }
	} finally {
	    group.shutdownGracefully();
	}
    }

    public static void main(String[] args) throws Exception {
	int port = 8080;
	if (args.length > 0) {
	    try {
		port = Integer.parseInt(args[0]);
	    } catch (NumberFormatException e) {
		e.printStackTrace();
	    }
	}
	new ChineseProverbClient().run(port);
    }
}
