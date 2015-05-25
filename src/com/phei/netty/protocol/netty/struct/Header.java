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
package com.phei.netty.protocol.netty.struct;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lilinfeng
 * @date 2014年3月14日
 * @version 1.0
 */
public final class Header {

    private int crcCode = 0xabef0101;

    private int length;// 消息长度

    private long sessionID;// 会话ID

    private byte type;// 消息类型

    private byte priority;// 消息优先级

    private Map<String, Object> attachment = new HashMap<String, Object>(); // 附件

    /**
     * @return the crcCode
     */
    public final int getCrcCode() {
	return crcCode;
    }

    /**
     * @param crcCode
     *            the crcCode to set
     */
    public final void setCrcCode(int crcCode) {
	this.crcCode = crcCode;
    }

    /**
     * @return the length
     */
    public final int getLength() {
	return length;
    }

    /**
     * @param length
     *            the length to set
     */
    public final void setLength(int length) {
	this.length = length;
    }

    /**
     * @return the sessionID
     */
    public final long getSessionID() {
	return sessionID;
    }

    /**
     * @param sessionID
     *            the sessionID to set
     */
    public final void setSessionID(long sessionID) {
	this.sessionID = sessionID;
    }

    /**
     * @return the type
     */
    public final byte getType() {
	return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public final void setType(byte type) {
	this.type = type;
    }

    /**
     * @return the priority
     */
    public final byte getPriority() {
	return priority;
    }

    /**
     * @param priority
     *            the priority to set
     */
    public final void setPriority(byte priority) {
	this.priority = priority;
    }

    /**
     * @return the attachment
     */
    public final Map<String, Object> getAttachment() {
	return attachment;
    }

    /**
     * @param attachment
     *            the attachment to set
     */
    public final void setAttachment(Map<String, Object> attachment) {
	this.attachment = attachment;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "Header [crcCode=" + crcCode + ", length=" + length
		+ ", sessionID=" + sessionID + ", type=" + type + ", priority="
		+ priority + ", attachment=" + attachment + "]";
    }

}
