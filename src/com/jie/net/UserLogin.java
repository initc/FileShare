package com.jie.net;

import java.util.Map;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.jie.xml.XMLTools;

public class UserLogin {
	public static String Register_Path = "http://192.168.191.1/FileShare/user/login";
	//public static String Register_Path = "http://127.0.0.1/FileShare/user/login";
	private Handler handler;
	private Context context;

	public UserLogin(Context context, Handler handler) {

		this.context = context;
		this.handler = handler;
	}
	/**
	 * 锟斤拷锟斤拷晒锟斤拷锟斤拷锟� 0x26
	 * 锟斤拷锟矫伙拷谐晒锟斤拷锟斤拷handler锟斤拷锟斤拷0x46 锟斤拷锟斤拷失锟斤拷锟斤拷息
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
		 * 锟矫伙拷注锟斤拷
		 * 
		 * @param data
		 *            锟斤拷锟捷的硷拷值锟斤拷
		 * @return 锟缴癸拷为true 失锟斤拷为false
		 */
		private String loginThread(Map<String, String> data) {
			String xml = XMLTools.SimpleMakeXML(data);
			System.out.println(xml);
			if (xml == null)
				return null;
			// 锟斤拷锟斤拷锟接成癸拷锟津返伙拷一锟斤拷xml锟斤拷锟斤拷
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
				// 锟叫匡拷锟斤拷null
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
