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
package com.phei.netty.codec.marshalling;

import io.netty.handler.codec.marshalling.DefaultMarshallerProvider;
import io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

/**
 * @author Lilinfeng
 * @date 2014年2月25日
 * @version 1.0
 */
public final class MarshallingCodeCFactory {

    /**
     * 创建Jboss Marshalling解码器MarshallingDecoder
     * 
     * @return
     */
    public static MarshallingDecoder buildMarshallingDecoder() {
	final MarshallerFactory marshallerFactory = Marshalling
		.getProvidedMarshallerFactory("serial");
	final MarshallingConfiguration configuration = new MarshallingConfiguration();
	configuration.setVersion(5);
	UnmarshallerProvider provider = new DefaultUnmarshallerProvider(
		marshallerFactory, configuration);
	MarshallingDecoder decoder = new MarshallingDecoder(provider, 1024);
	return decoder;
    }

    /**
     * 创建Jboss Marshalling编码器MarshallingEncoder
     * 
     * @return
     */
    public static MarshallingEncoder buildMarshallingEncoder() {
	final MarshallerFactory marshallerFactory = Marshalling
		.getProvidedMarshallerFactory("serial");
	final MarshallingConfiguration configuration = new MarshallingConfiguration();
	configuration.setVersion(5);
	MarshallerProvider provider = new DefaultMarshallerProvider(
		marshallerFactory, configuration);
	MarshallingEncoder encoder = new MarshallingEncoder(provider);
	return encoder;
    }
}
