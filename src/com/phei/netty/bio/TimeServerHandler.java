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
package com.phei.netty.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Administrator
 * @date 2014年2月14日
 * @version 1.0
 */
public class TimeServerHandler implements Runnable {

    private Socket socket;

    public TimeServerHandler(Socket socket) {
	this.socket = socket;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
	BufferedReader in = null;
	PrintWriter out = null;
	try {
	    in = new BufferedReader(new InputStreamReader(
		    this.socket.getInputStream()));
	    out = new PrintWriter(this.socket.getOutputStream(), true);
	    String currentTime = null;
	    String body = null;
	    while (true) {
		body = in.readLine();
		if (body == null)
		    break;
		System.out.println("The time server receive order : " + body);
		currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new java.util.Date(
			System.currentTimeMillis()).toString() : "BAD ORDER";
		out.println(currentTime);
	    }

	} catch (Exception e) {
	    if (in != null) {
		try {
		    in.close();
		} catch (IOException e1) {
		    e1.printStackTrace();
		}
	    }
	    if (out != null) {
		out.close();
		out = null;
	    }
	    if (this.socket != null) {
		try {
		    this.socket.close();
		} catch (IOException e1) {
		    e1.printStackTrace();
		}
		this.socket = null;
	    }
	}
    }
}
