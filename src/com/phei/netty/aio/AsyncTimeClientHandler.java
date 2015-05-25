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
package com.phei.netty.aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * @author Administrator
 * @date 2014年2月16日
 * @version 1.0
 */
public class AsyncTimeClientHandler implements
	CompletionHandler<Void, AsyncTimeClientHandler>, Runnable {

    private AsynchronousSocketChannel client;
    private String host;
    private int port;
    private CountDownLatch latch;

    public AsyncTimeClientHandler(String host, int port) {
	this.host = host;
	this.port = port;
	try {
	    client = AsynchronousSocketChannel.open();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void run() {

	latch = new CountDownLatch(1);
	client.connect(new InetSocketAddress(host, port), this, this);
	try {
	    latch.await();
	} catch (InterruptedException e1) {
	    e1.printStackTrace();
	}
	try {
	    client.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void completed(Void result, AsyncTimeClientHandler attachment) {
	byte[] req = "QUERY TIME ORDER".getBytes();
	ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
	writeBuffer.put(req);
	writeBuffer.flip();
	client.write(writeBuffer, writeBuffer,
		new CompletionHandler<Integer, ByteBuffer>() {
		    @Override
		    public void completed(Integer result, ByteBuffer buffer) {
			if (buffer.hasRemaining()) {
			    client.write(buffer, buffer, this);
			} else {
			    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
			    client.read(
				    readBuffer,
				    readBuffer,
				    new CompletionHandler<Integer, ByteBuffer>() {
					@Override
					public void completed(Integer result,
						ByteBuffer buffer) {
					    buffer.flip();
					    byte[] bytes = new byte[buffer
						    .remaining()];
					    buffer.get(bytes);
					    String body;
					    try {
						body = new String(bytes,
							"UTF-8");
						System.out.println("Now is : "
							+ body);
						latch.countDown();
					    } catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					    }
					}

					@Override
					public void failed(Throwable exc,
						ByteBuffer attachment) {
					    try {
						client.close();
						latch.countDown();
					    } catch (IOException e) {
						// ingnore on close
					    }
					}
				    });
			}
		    }

		    @Override
		    public void failed(Throwable exc, ByteBuffer attachment) {
			try {
			    client.close();
			    latch.countDown();
			} catch (IOException e) {
			    // ingnore on close
			}
		    }
		});
    }

    @Override
    public void failed(Throwable exc, AsyncTimeClientHandler attachment) {
	exc.printStackTrace();
	try {
	    client.close();
	    latch.countDown();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}
