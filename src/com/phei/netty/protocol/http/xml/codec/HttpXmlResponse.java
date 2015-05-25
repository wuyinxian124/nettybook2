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

import io.netty.handler.codec.http.FullHttpResponse;

/**
 * @author Administrator
 * @date 2014年3月1日
 * @version 1.0
 */
public class HttpXmlResponse {
    private FullHttpResponse httpResponse;
    private Object result;

    public HttpXmlResponse(FullHttpResponse httpResponse, Object result) {
	this.httpResponse = httpResponse;
	this.result = result;
    }

    /**
     * @return the httpResponse
     */
    public final FullHttpResponse getHttpResponse() {
	return httpResponse;
    }

    /**
     * @param httpResponse
     *            the httpResponse to set
     */
    public final void setHttpResponse(FullHttpResponse httpResponse) {
	this.httpResponse = httpResponse;
    }

    /**
     * @return the body
     */
    public final Object getResult() {
	return result;
    }

    /**
     * @param body
     *            the body to set
     */
    public final void setResult(Object result) {
	this.result = result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "HttpXmlResponse [httpResponse=" + httpResponse + ", result="
		+ result + "]";
    }

}
