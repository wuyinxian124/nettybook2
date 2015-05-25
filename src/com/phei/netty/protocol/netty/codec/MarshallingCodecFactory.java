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
package com.phei.netty.protocol.netty.codec;

import java.io.IOException;

import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;
import org.jboss.marshalling.Unmarshaller;

/**
 * @author Administrator
 * @date 2014年3月15日
 * @version 1.0
 */
public final class MarshallingCodecFactory {

    /**
     * 创建Jboss Marshaller
     * 
     * @return
     * @throws IOException
     */
    protected static Marshaller buildMarshalling() throws IOException {
	final MarshallerFactory marshallerFactory = Marshalling
		.getProvidedMarshallerFactory("serial");
	final MarshallingConfiguration configuration = new MarshallingConfiguration();
	configuration.setVersion(5);
	Marshaller marshaller = marshallerFactory
		.createMarshaller(configuration);
	return marshaller;
    }

    /**
     * 创建Jboss Unmarshaller
     * 
     * @return
     * @throws IOException
     */
    protected static Unmarshaller buildUnMarshalling() throws IOException {
	final MarshallerFactory marshallerFactory = Marshalling
		.getProvidedMarshallerFactory("serial");
	final MarshallingConfiguration configuration = new MarshallingConfiguration();
	configuration.setVersion(5);
	final Unmarshaller unmarshaller = marshallerFactory
		.createUnmarshaller(configuration);
	return unmarshaller;
    }
}
