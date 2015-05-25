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
package com.phei.netty.codec.pojo;

import java.io.Serializable;

/**
 * @author Lilinfeng
 * @date 2014年2月23日
 * @version 1.0
 */
public class SubscribeResp implements Serializable {

    /**
     * 默认序列ID
     */
    private static final long serialVersionUID = 1L;

    private int subReqID;

    private int respCode;

    private String desc;

    /**
     * @return the subReqID
     */
    public final int getSubReqID() {
	return subReqID;
    }

    /**
     * @param subReqID
     *            the subReqID to set
     */
    public final void setSubReqID(int subReqID) {
	this.subReqID = subReqID;
    }

    /**
     * @return the respCode
     */
    public final int getRespCode() {
	return respCode;
    }

    /**
     * @param respCode
     *            the respCode to set
     */
    public final void setRespCode(int respCode) {
	this.respCode = respCode;
    }

    /**
     * @return the desc
     */
    public final String getDesc() {
	return desc;
    }

    /**
     * @param desc
     *            the desc to set
     */
    public final void setDesc(String desc) {
	this.desc = desc;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "SubscribeResp [subReqID=" + subReqID + ", respCode=" + respCode
		+ ", desc=" + desc + "]";
    }

}
