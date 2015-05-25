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
package com.phei.netty.protocol.websocket.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author lilinfeng
 * @date 2014年2月14日
 * @version 1.0
 */
public class WebSocketServer {
    public void run(int port) throws Exception {
	EventLoopGroup bossGroup = new NioEventLoopGroup();
	EventLoopGroup workerGroup = new NioEventLoopGroup();
	try {
	    ServerBootstrap b = new ServerBootstrap();
	    b.group(bossGroup, workerGroup)
		    .channel(NioServerSocketChannel.class)
		    .childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch)
				throws Exception {
			    ChannelPipeline pipeline = ch.pipeline();
			    pipeline.addLast("http-codec",
				    new HttpServerCodec());
			    pipeline.addLast("aggregator",
				    new HttpObjectAggregator(65536));
			    ch.pipeline().addLast("http-chunked",
				    new ChunkedWriteHandler());
			    pipeline.addLast("handler",
				    new WebSocketServerHandler());
			}
		    });

	    Channel ch = b.bind(port).sync().channel();
	    System.out.println("Web socket server started at port " + port
		    + '.');
	    System.out
		    .println("Open your browser and navigate to http://localhost:"
			    + port + '/');

	    ch.closeFuture().sync();
	} finally {
	    bossGroup.shutdownGracefully();
	    workerGroup.shutdownGracefully();
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
	new WebSocketServer().run(port);
    }
}
