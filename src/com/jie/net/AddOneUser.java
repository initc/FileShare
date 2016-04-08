package com.jie.net;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.Handler;

import com.jie.bean.User;
import com.jie.xml.XMLTools;

public class AddOneUser {

	public static String Add_Path = "http://192.168.191.1/FileShare/user/user_add";
	private Handler handler;
	private Context context;

	public AddOneUser(Handler handler, Context context) {

		this.handler = handler;
		this.context = context;

	}

	public void add(String hostId, String friendId) {
		TheadAddUser t = new TheadAddUser(hostId, friendId);
		Thread thread = new Thread(t);
		thread.start();

	}

	class TheadAddUser implements Runnable {

		String hostId;
		String friendId;

		public TheadAddUser(String hostId, String friendId) {

			this.hostId = hostId;
			this.friendId = friendId;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Map<String, String> data = new HashMap<String, String>();
			data.put("hostId", hostId);
			data.put("friendId", friendId);
			data.put("head", "addoneuser");
			String xml = XMLTools.SimpleMakeXML(data);
			String result = SendXMLToWeb.sendXMLToWeb(Add_Path, xml);
			XMLTools.getUser(result, handler);
			
			System.out.println(result);
			
		}

	}
}
