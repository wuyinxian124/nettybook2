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
package com.phei.netty.codec.serializable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author Administrator
 * @date 2014年2月23日
 * @version 1.0
 */
public class TestUserInfo {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
	UserInfo info = new UserInfo();
	info.buildUserID(100).buildUserName("Welcome to Netty");
	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	ObjectOutputStream os = new ObjectOutputStream(bos);
	os.writeObject(info);
	os.flush();
	os.close();
	byte[] b = bos.toByteArray();
	System.out.println("The jdk serializable length is : " + b.length);
	bos.close();
	System.out.println("-------------------------------------");
	System.out.println("The byte array serializable length is : "
		+ info.codeC().length);

    }

}
