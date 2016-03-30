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
	 * ����ɹ����� 0x26
	 * ���û�гɹ����handler����0x46 ����ʧ����Ϣ
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
		 * �û�ע��
		 * 
		 * @param data
		 *            ���ݵļ�ֵ��
		 * @return �ɹ�Ϊtrue ʧ��Ϊfalse
		 */
		private String loginThread(Map<String, String> data) {
			String xml = XMLTools.SimpleMakeXML(data);
			if (xml == null)
				return null;
			// �����ӳɹ��򷵻�һ��xml����
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
				// �п���null
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
