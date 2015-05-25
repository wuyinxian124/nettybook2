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
package com.phei.netty.protocol.http.xml.codec;

import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author Lilinfeng
 * @date 2014年3月1日
 * @version 1.0
 */
public class HttpXmlRequest {

    private FullHttpRequest request;
    private Object body;

    public HttpXmlRequest(FullHttpRequest request, Object body) {
	this.request = request;
	this.body = body;
    }

    /**
     * @return the request
     */
    public final FullHttpRequest getRequest() {
	return request;
    }

    /**
     * @param request
     *            the request to set
     */
    public final void setRequest(FullHttpRequest request) {
	this.request = request;
    }

    /**
     * @return the object
     */
    public final Object getBody() {
	return body;
    }

    /**
     * @param object
     *            the object to set
     */
    public final void setBody(Object body) {
	this.body = body;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "HttpXmlRequest [request=" + request + ", body =" + body + "]";
    }
}
