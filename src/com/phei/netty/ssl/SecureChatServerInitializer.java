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
package com.phei.netty.ssl;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * Creates a newly configured {@link ChannelPipeline} for a new channel.
 */
public class SecureChatServerInitializer extends
	ChannelInitializer<SocketChannel> {

    private String tlsMode;

    public SecureChatServerInitializer(String TLS_MODE) {
	tlsMode = TLS_MODE;
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
	ChannelPipeline pipeline = ch.pipeline();

	// Add SSL handler first to encrypt and decrypt everything.
	// In this example, we use a bogus certificate in the server side
	// and accept any invalid certificates in the client side.
	// You will need something more complicated to identify both
	// and server in the real world.
	//
	// Read SecureChatSslContextFactory
	// if you need client certificate authentication.

	SSLEngine engine = null;
	if (SSLMODE.CA.toString().equals(tlsMode)) {
	    engine = SecureChatSslContextFactory
		    .getServerContext(
			    tlsMode,
			    System.getProperty("user.dir")
				    + "/src/com/phei/netty/ssl/conf/client/sChat.jks",
			    null).createSSLEngine();
	} else if (SSLMODE.CSA.toString().equals(tlsMode)) {
	    engine = SecureChatSslContextFactory
		    .getServerContext(
			    tlsMode,
			    System.getProperty("user.dir")
				    + "/src/com/phei/netty/ssl/conf/twoway/sChat.jks",
			    System.getProperty("user.dir")
				    + "/src/com/phei/netty/ssl/conf/twoway/sChat.jks")
		    .createSSLEngine();

	    // engine = SecureChatSslContextFactory
	    // .getServerContext(
	    // tlsMode,
	    // System.getProperty("user.dir")
	    // + "/src/com/phei/netty/ssl/conf/client/sChat.jks",
	    // System.getProperty("user.dir")
	    // + "/src/com/phei/netty/ssl/conf/client/sChat.jks")
	    // .createSSLEngine();
	} else {
	    System.err.println("ERROR : " + tlsMode);
	    System.exit(-1);
	}
	engine.setUseClientMode(false);

	// Client auth
	if (SSLMODE.CSA.toString().equals(tlsMode))
	    engine.setNeedClientAuth(true);
	pipeline.addLast("ssl", new SslHandler(engine));

	// On top of the SSL handler, add the text line codec.
	pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192,
		Delimiters.lineDelimiter()));
	pipeline.addLast("decoder", new StringDecoder());
	pipeline.addLast("encoder", new StringEncoder());

	// and then business logic.
	pipeline.addLast("handler", new SecureChatServerHandler());
    }
}
