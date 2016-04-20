package com.jie.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import android.os.Handler;
import android.os.Message;
import android.util.Xml;

import com.jie.bean.Conversation;
import com.jie.bean.User;

public class XMLTools {

	public static User getUser(String xml, Handler handler) {

		Map<String, String> data = parseXML(xml, "user");
		String result = data.get("result");
		if (result.equals("error")) {
			// 没有这个用户
			handler.sendEmptyMessage(0x42);
			return null;
		} else if (result.equals("fault")) {

			// 已经是你的好友
			handler.sendEmptyMessage(0x43);
			return null;

		} else {
			User user = new User();
			user.setLoginId(data.get("friendId"));
			user.setName(data.get("name"));
			Message message = new Message();
			message.what=0x45;
			message.obj=user;
			handler.sendMessage(message);
			return user;
		}

	}
	
	public static List<User> getContact(String xml) {
		List<User> users = new LinkedList<User>();
		XmlPullParser parse = Xml.newPullParser();

		ByteArrayInputStream in = null;
		try {
			//----------更改了   utf-8  到gbk
			in = new ByteArrayInputStream(xml.getBytes());
			parse.setInput(in, "utf-8");
			int type = parse.getEventType();
			User user = null;
			while (type != XmlPullParser.END_DOCUMENT) {
				switch (type) {
				case XmlPullParser.START_TAG:
					if (parse.getName().equals("user")) {

						user = new User();

					} else if (parse.getName().equals("friendId")) {

						user.setLoginId(parse.nextText());
					} else if (parse.getName().equals("name")) {

						user.setName(parse.nextText());
					}

					break;

				case XmlPullParser.END_TAG:
					if (parse.getName().equals("user")) {
						users.add(user);
					}
					break;
				default:
					break;

				}
				type = parse.next();
			}

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
		return users;
	}
	/**
	 * 
	 * 
	 * @param xml
	 * @return
	 */
	public static List<Conversation> getMessage(String xml) {
		List<Conversation> users = new LinkedList<Conversation>();
		XmlPullParser parse = Xml.newPullParser();

		ByteArrayInputStream in = null;
		try {
			//----------更改了   utf-8  到gbk
			in = new ByteArrayInputStream(xml.getBytes());
			parse.setInput(in, "utf-8");
			int type = parse.getEventType();
			Conversation user = null;
			while (type != XmlPullParser.END_DOCUMENT) {
				switch (type) {
				case XmlPullParser.START_TAG:
					if (parse.getName().equals("message")) {

						user = new Conversation();

					} else if (parse.getName().equals("fromId")) {

						user.setFriendLoginId(parse.nextText());
					} else if (parse.getName().equals("fromName")) {

						user.setName(parse.nextText());
					}else if (parse.getName().equals("toId")) {

						user.setHostloginId(parse.nextText());
					}else if (parse.getName().equals("content")) {

						user.setMessage(parse.nextText());
					}

					break;

				case XmlPullParser.END_TAG:
					if (parse.getName().equals("message")) {
						user.setTime(new Date().toString());
						user.setMySend(false);
						user.setStringData(true);
						users.add(user);
						
					}
					break;
				default:
					break;

				}
				type = parse.next();
			}

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
		return users;
	}
	public static Map<String, String> parseXML(String xml, String root) {
		Map<String, String> re = new HashMap<String, String>();
		XmlPullParser parse = Xml.newPullParser();
		try {
			//----更改过  utf-8  到GBK
			ByteArrayInputStream in = new ByteArrayInputStream(
					xml.getBytes("utf-8"));
			parse.setInput(in, "gbk");
			int type = parse.getEventType();
			while (type != XmlPullParser.END_DOCUMENT) {

				switch (type) {

				case XmlPullParser.START_TAG:
					String tag = parse.getName();
					if (tag.equals(root)) {
						re.put("head", tag);
						break;
					}
					re.put(tag, parse.nextText());

					// re.put(tag, parse.nextText());

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
	 * @param data
	 *            一个xml格式的数据
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
			xml.startDocument("GBK", true);
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
			return new String(writer.toString().getBytes(), "utf-8");

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
