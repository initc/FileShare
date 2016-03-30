package com.jie.net;

import java.util.Map;

import com.jie.net.UserRegister.TheadRegister;
import com.jie.xml.XMLTools;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class UserLogin {
	public static String Register_Path = "http://192.168.191.1/FileShare/user/login";
	private Handler handler;
	private Context context;

	public UserLogin(Context context, Handler handler) {

		this.context = context;
		this.handler = handler;
	}
	/**
	 * 如果成功发送 0x26
	 * 如果没有成功会对handler发送0x46 接收失败信息
	 * @param data
	 */
	public void login(Map<String, String> data) {
		Thread thread = new Thread(new TheadLogin(data, handler));
		thread.setName("login");
		thread.start();

	}

	class TheadLogin implements Runnable {
		private Map<String, String> data;
		private Handler handler;

		public TheadLogin(Map<String, String> data, Handler handler) {
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
		private String loginThread(Map<String, String> data) {
			String xml = XMLTools.SimpleMakeXML(data);
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
			String re = loginThread(data);
			if (re != null) {

				Message message = new Message();
				Map<String, String> xmlMap = XMLTools.parseXML(re, "login");
				// 有可能null
				if(xmlMap==null){
					message.what = 0x46;
				}else{
					message.what = 0x26;
				}
				message.obj = xmlMap;
				
				
				handler.sendMessage(message);
			} else {
				handler.sendEmptyMessage(0x46);
			}
		}

	}

}
