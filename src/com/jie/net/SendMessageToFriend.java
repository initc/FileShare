package com.jie.net;

import java.util.Hashtable;
import java.util.Map;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.jie.net.AddFriend.ThreadAddFriend;
import com.jie.xml.XMLTools;

public class SendMessageToFriend {

	public static String Send_Path = "http://192.168.191.1/FileShare/user/sendMessage";
	private Handler handler;
	private Context context;
	
	private String hostId;
	private String friendId;
	private String message;
	public SendMessageToFriend(Handler handle, Context context) {
		this.handler = handle;
		this.context = context;
	}

	/**
	 * 发送一个消息给对方
	 * 
	 * 
	 * @param hostId
	 *            发送人
	 * @param friendId
	 *            接收人
	 * @param text
	 *            信息
	 */
	public void sendToFriend(String hostId, String friendId, String message) {
		this.hostId = hostId;
		this.friendId=friendId;
		this.message=message;
		Thread thread = new Thread(new ThreadSendMessage(handler));
		thread.setName("sendMessage");
		thread.start();

	}

	class ThreadSendMessage implements Runnable {
		private Handler handler;

		public ThreadSendMessage(Handler handler) {

			this.handler = handler;
		}
		 

		@Override
		public void run() {
			Map<String, String> data = new Hashtable<String, String>();
			data.put("head", "sendMessage");
			data.put("hostId", hostId);
			data.put("friendId", friendId);
			data.put("message", message);
			String xml = XMLTools.SimpleMakeXML(data);
			System.out.println(xml);
			String result = SendXMLToWeb.sendXMLToWeb(Send_Path, xml);
			if(result==null){
				handler.sendEmptyMessage(0x21);
				return ;
			}
			Map<String, String> re = XMLTools.parseXML(result, "message");
			String jieguo = re.get("result");	
			
			System.out.println(jieguo);
			
			if(jieguo.equals("success")){
				handler.sendEmptyMessage(0x26);
				return ;
			}
			handler.sendEmptyMessage(0x21);
			
		}

	}

}
