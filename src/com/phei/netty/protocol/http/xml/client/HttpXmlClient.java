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

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;

import java.net.InetSocketAddress;

import com.phei.netty.protocol.http.xml.codec.HttpXmlRequestEncoder;
import com.phei.netty.protocol.http.xml.codec.HttpXmlResponseDecoder;
import com.phei.netty.protocol.http.xml.pojo.Order;

/**
 * @author lilinfeng
 * @date 2014年2月14日
 * @version 1.0
 */
public class HttpXmlClient {

    public void connect(int port) throws Exception {
	// 配置客户端NIO线程组
	EventLoopGroup group = new NioEventLoopGroup();
	try {
	    Bootstrap b = new Bootstrap();
	    b.group(group).channel(NioSocketChannel.class)
		    .option(ChannelOption.TCP_NODELAY, true)
		    .handler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel ch)
				throws Exception {
			    ch.pipeline().addLast("http-decoder",
				    new HttpResponseDecoder());
			    ch.pipeline().addLast("http-aggregator",
				    new HttpObjectAggregator(65536));
			    // XML解码器
			    ch.pipeline().addLast(
				    "xml-decoder",
				    new HttpXmlResponseDecoder(Order.class,
					    true));
			    ch.pipeline().addLast("http-encoder",
				    new HttpRequestEncoder());
			    ch.pipeline().addLast("xml-encoder",
				    new HttpXmlRequestEncoder());
			    ch.pipeline().addLast("xmlClientHandler",
				    new HttpXmlClientHandle());
			}
		    });

	    // 发起异步连接操作
	    ChannelFuture f = b.connect(new InetSocketAddress(port)).sync();

	    // 当代客户端链路关闭
	    f.channel().closeFuture().sync();
	} finally {
	    // 优雅退出，释放NIO线程组
	    group.shutdownGracefully();
	}
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
	int port = 8080;
	if (args != null && args.length > 0) {
	    try {
		port = Integer.valueOf(args[0]);
	    } catch (NumberFormatException e) {
		// 采用默认值
	    }
	}
	new HttpXmlClient().connect(port);
    }
}
