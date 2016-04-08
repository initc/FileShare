package com.jie.net;

import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.jie.xml.XMLTools;

public class UserRegister {
	private Handler handler;
	private Context context;

	public UserRegister(Context context, Handler handler) {

		this.context = context;
		this.handler = handler;
	}

	public static String Register_Path = "http://192.168.191.1/FileShare/user/register";
	//public static String Register_Path = "http://127.0.0.1/FileShare/user/register";

	public void register(Map<String, String> data) {
		Thread thread = new Thread(new TheadRegister(data, handler));
		thread.setName("register");
		thread.start();

	}

	class TheadRegister implements Runnable {
		private Map<String, String> data;
		private Handler handler;

		public TheadRegister(Map<String, String> data, Handler handler) {
			this.data = data;
			this.handler = handler;
		}

		/**
		 * 用户注册
		 * 
		 * @param data
		 *            数据的键值对
		 * @return 成功为true 失败为false
		 */
		private String registerThread(Map<String, String> data) {
			String xml = XMLTools.SimpleMakeXML(data);
			// System.out.println(xml);
			if (xml == null)
				return null;
			// 如果添加成功则返回一个xml数据
			String result = SendXMLToWeb.sendXMLToWeb(Register_Path, xml);
			if (result == null)
				return null;
			Log.d("xml---------", result);
			
			return result;
		}

		@Override
		public void run() {
			String re = registerThread(data);
			if (re != null) {
				Intent xmlResult = new Intent();
				Message message = new Message();
				Map<String, String> xmlMap = XMLTools.parseXML(re, "register");
				// 有可能null
				message.obj = xmlMap;
				message.what = 0x20;
				handler.sendMessage(message);
			} else {
				handler.sendEmptyMessage(0x44);
			}
		}

	}

}
