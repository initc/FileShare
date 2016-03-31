package com.jie.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import android.util.Log;
import android.util.Xml;

public class XMLTools {

	public static Map<String, String> parseXML(String xml,String root) {
		Map<String, String> re = new HashMap<String, String>();
		XmlPullParser parse = Xml.newPullParser();
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(
					xml.getBytes("utf-8"));
			parse.setInput(in, "utf-8");
			int type = parse.getEventType();
			while (type != XmlPullParser.END_DOCUMENT) {

				switch (type) {

				case XmlPullParser.START_TAG:
					String tag = parse.getName();
					if(tag.equals(root)){
						re.put("head", tag);
						break;
					}
					re.put(tag, parse.nextText());
					
					//re.put(tag, parse.nextText());

					break;

				}
				type = parse.next();
			}
			return re;

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
/**
 * 把一组键值对转换成xml格式的数据
 * 
 * @param data  一个xml格式的数据
 * @return
 */
	public static String SimpleMakeXML(Map<String, String> data) {
		if (data == null || data.size() <= 1)
			return null;
		String head = data.get("head");
		if (head == null)
			return null;
		data.remove("head");
		XmlSerializer xml = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			xml.setOutput(writer);
			xml.startDocument("utf-8", true);
			// 最外的root节点
			xml.startTag("", head);

			for (Entry<String, String> item : data.entrySet()) {
				
				
				xml.startTag("", item.getKey());
				// 写入内容
				xml.text(item.getValue());
				xml.endTag("", item.getKey());
			}
			xml.endTag("", head);
			xml.endDocument();
			return new String(writer.toString().getBytes(),"utf-8");

		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
