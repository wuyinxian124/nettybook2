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
package com.phei.netty.protocol.http.xml;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;

import com.phei.netty.protocol.http.xml.pojo.Order;
import com.phei.netty.protocol.http.xml.pojo.OrderFactory;

/**
 * @author Lilinfeng
 * @date 2014年3月1日
 * @version 1.0
 */
public class TestOrder {

    private IBindingFactory factory = null;
    private StringWriter writer = null;
    private StringReader reader = null;
    private final static String CHARSET_NAME = "UTF-8";

    private String encode2Xml(Order order) throws JiBXException, IOException {
	factory = BindingDirectory.getFactory(Order.class);
	writer = new StringWriter();
	IMarshallingContext mctx = factory.createMarshallingContext();
	mctx.setIndent(2);
	mctx.marshalDocument(order, CHARSET_NAME, null, writer);
	String xmlStr = writer.toString();
	writer.close();
	System.out.println(xmlStr.toString());
	return xmlStr;
    }

    private Order decode2Order(String xmlBody) throws JiBXException {
	reader = new StringReader(xmlBody);
	IUnmarshallingContext uctx = factory.createUnmarshallingContext();
	Order order = (Order) uctx.unmarshalDocument(reader);
	return order;
    }

    public static void main(String[] args) throws JiBXException, IOException {
	TestOrder test = new TestOrder();
	Order order = OrderFactory.create(123);
	String body = test.encode2Xml(order);
	Order order2 = test.decode2Order(body);
	System.out.println(order2);

    }
}
