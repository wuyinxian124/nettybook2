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

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.io.StreamCorruptedException;

import org.jboss.marshalling.ByteInput;
import org.jboss.marshalling.Unmarshaller;

/**
 * @author Lilinfeng
 * @date 2014年3月14日
 * @version 1.0
 */
public class MarshallingDecoder {

    private final Unmarshaller unmarshaller;

    /**
     * Creates a new decoder whose maximum object size is {@code 1048576} bytes.
     * If the size of the received object is greater than {@code 1048576} bytes,
     * a {@link StreamCorruptedException} will be raised.
     * 
     * @throws IOException
     * 
     */
    public MarshallingDecoder() throws IOException {
	unmarshaller = MarshallingCodecFactory.buildUnMarshalling();
    }

    protected Object decode(ByteBuf in) throws Exception {
	int objectSize = in.readInt();
	ByteBuf buf = in.slice(in.readerIndex(), objectSize);
	ByteInput input = new ChannelBufferByteInput(buf);
	try {
	    unmarshaller.start(input);
	    Object obj = unmarshaller.readObject();
	    unmarshaller.finish();
	    in.readerIndex(in.readerIndex() + objectSize);
	    return obj;
	} finally {
	    unmarshaller.close();
	}
    }
}
