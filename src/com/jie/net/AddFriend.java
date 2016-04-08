package com.jie.net;

import java.util.Hashtable;
import java.util.Map;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.jie.xml.XMLTools;

public class AddFriend {
	public static String Add_Path = "http://192.168.191.1/FileShare/user/contacts";
	private Handler handler;
	private Context context;
	private String hostId;

	public AddFriend(Handler handle, Context context) {
		this.handler = handle;
		this.context = context;
	}

	public void initFriend(String hostId) {
		this.hostId = hostId;
		Thread thread = new Thread(new ThreadAddFriend(handler));
		thread.setName("contact");
		thread.start();

	}

	class ThreadAddFriend implements Runnable {
		private Handler handler;

		public ThreadAddFriend(Handler handler) {

			this.handler = handler;
		}

		@Override
		public void run() {
			Map<String, String> data = new Hashtable<String, String>();
			data.put("head", "contacts");
			data.put("loginId", hostId);
			String xml = XMLTools.SimpleMakeXML(data);
			System.out.println(xml);
			String result = SendXMLToWeb.sendXMLToWeb(Add_Path, xml);
			
			Message mes = new Message();
			mes.obj = result;
			mes.what=0x26;
			handler.sendMessage(mes);
		}

	}

}
