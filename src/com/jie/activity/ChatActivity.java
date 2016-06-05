package com.jie.activity;

import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jie.DBUtils.DBHelper;
import com.jie.DBUtils.DBUser;
import com.jie.bean.ChatHolder;
import com.jie.bean.Conversation;
import com.jie.fileshare.R;
import com.jie.net.SendMessageToFriend;
import com.jie.net.SendXMLToWeb;
import com.jie.utils.SpUtil;
import com.jie.xml.XMLTools;

public class ChatActivity extends Activity {
	private Timer timer;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			if (msg.what == 0x21) {

				Toast.makeText(ChatActivity.this, "联网后再试", 0).show();
			} else if (msg.what == 0x26) {
				// 说明发送成功了
				// 需要把发送的信息存储到数据库中和显示出来
				ContentValues values = new ContentValues();
				values.put(Conversation.COLUMN_NAME_HOSTLOGINID, hostId);
				values.put(Conversation.COLUMN_NAME_FRIENDLOGINID, friendId);
				values.put(Conversation.COLUMN_NAME_FRIENDNAME, friendName);
				values.put(Conversation.COLUMN_NAME_MESSAGE, message);
				Date time = new Date();
				values.put(Conversation.COLUMN_NAME_TIME, time.toString());
				values.put(Conversation.COLUMN_NAME_TYPE, 0);
				DBUser.insertMessageToConversation(ChatActivity.this, values);
				Conversation con = new Conversation();

				con.setFriendLoginId(friendId);
				con.setHostloginId(hostId);
				con.setName(friendName);
				con.setMessage(message);
				con.setTime(time.toString());
				con.setMySend(true);
				con.setStringData(true);

				chatData.add(con);
				chatAdapter.notifyDataSetChanged();
				chatListview.setSelection(chatListview.getCount());
			} else if (msg.what == 0x23) {
				@SuppressWarnings("unchecked")
				List<Conversation> messages = (List<Conversation>) msg.obj;

				for (int i = 0; i < messages.size(); i++) {
					Conversation conv = messages.get(i);
					ContentValues values = new ContentValues();
					values.put(Conversation.COLUMN_NAME_HOSTLOGINID,
							conv.getHostloginId());
					values.put(Conversation.COLUMN_NAME_FRIENDLOGINID,
							conv.getFriendLoginId());
					values.put(Conversation.COLUMN_NAME_FRIENDNAME,
							conv.getName());
					values.put(Conversation.COLUMN_NAME_MESSAGE,
							conv.getMessage());

					values.put(Conversation.COLUMN_NAME_TIME, conv.getTime());
					values.put(Conversation.COLUMN_NAME_TYPE, 1);
					DBUser.insertMessageToConversation(ChatActivity.this,
							values);

				}
				chatData.addAll(messages);
				chatAdapter.notifyDataSetChanged();
				chatListview.setSelection(chatListview.getCount());
			}

		};

	};

	private ListView chatListview;
	// 用来记录所有的消息
	List<Conversation> chatData;
	ChatAdapter chatAdapter;
	// 输入框
	EditText sendText;
	// 发送按钮
	ImageView send;
	// 记录send按钮是否是在发送文件还是普通的消息
	private boolean isFile=true;

	private String friendId;
	private String friendName;
	private String hostId;

	private String message;

	
	// private int type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);
		chatListview = (ListView) findViewById(R.id.chatListview);
		sendText = (EditText) findViewById(R.id.edittext);
		send = (ImageView) findViewById(R.id.send);
		chatData = new LinkedList<Conversation>();
		chatAdapter = new ChatAdapter();
		chatListview.setAdapter(chatAdapter);
		Intent intent = getIntent();

		// 获取一些信息
		SharedPreferences sh = SpUtil.getSharePerference(ChatActivity.this);
		hostId = sh.getString("loginId", "");
		friendId = intent.getStringExtra("FriendId");
		friendName = intent.getStringExtra("FriendName");
		((TextView)findViewById(R.id.chat_name)).setText(friendName);
		initMessageData(friendId);
		sendText.addTextChangedListener(new SendEdit());
		send.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {

				String text = sendText.getText().toString();
				
				if (!isFile) {
					if (text == null || text.equals(""))
						return;
					
					if (hostId == null) {

						SharedPreferences sh = SpUtil
								.getSharePerference(ChatActivity.this);

						hostId = sh.getString("loginId", "");
					}
					// 记录消息
					message = text;

					SendMessageToFriend sendTools = new SendMessageToFriend(
							handler, ChatActivity.this);
					sendTools.sendToFriend(hostId, friendId, text);
					sendText.setText("");
				}else{
					
					Intent intent= new Intent(ChatActivity.this,FileActivity.class);
					intent.putExtra("toid", friendId);
					startActivityForResult(intent, 52);
					
				}

			}
		});

		timer = new Timer();
		timer.schedule(new ConnectionTask(handler, hostId, friendId), 0, 2500);

	}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	if(requestCode==52&&resultCode==53){
    		//fale to load is to do nothing 
    		
    	}else if(requestCode==52&&resultCode==54){
    		//success , Be going to show the message in listview 
    		
    		
    		
    	}
    	
    	
    }
	@SuppressLint("NewApi")
	private void initMessageData(String friendId) {
		chatData.clear();

		DBHelper db = new DBHelper(this);
		SQLiteDatabase sqlTable = db.getReadableDatabase();
		String[] columns = new String[] { Conversation.COLUMN_NAME_HOSTLOGINID,
				Conversation.COLUMN_NAME_FRIENDLOGINID,
				Conversation.COLUMN_NAME_FRIENDNAME,
				Conversation.COLUMN_NAME_TIME,
				Conversation.COLUMN_NAME_MESSAGE, Conversation.COLUMN_NAME_TYPE };
		SharedPreferences sh = SpUtil.getSharePerference(this);
		String loginId = sh.getString("loginId", "");
		String[] selectArgs = new String[] { loginId, friendId };
		String orderBy = Conversation.COLUMN_NAME_TIME;
		Cursor cursor = sqlTable.query(true, Conversation.TABLE_NAME, columns,
				Conversation.COLUMN_NAME_HOSTLOGINID + " =? and "
						+ Conversation.COLUMN_NAME_MESSAGE
						+ " is not null and "
						+ Conversation.COLUMN_NAME_FRIENDLOGINID + "=?",
				selectArgs, null, null, orderBy, null, null);

		while (cursor.moveToNext()) {
			Conversation con = new Conversation();
			con.setHostloginId(cursor.getString(0));
			con.setFriendLoginId(cursor.getString(1));
			con.setName(cursor.getString(2));
			con.setTime(cursor.getString(3));
			con.setMessage(cursor.getString(4));
			int type = cursor.getInt(5);
			// 字符并且是我发的
			if (type == 0) {
				con.setStringData(true);
				con.setMySend(true);
			} else if (type == 1) {
				// 字符不是我发的
				con.setStringData(true);
				con.setMySend(false);

			} else if (type == 2) {
				// 文件是我发的
				con.setStringData(false);
				con.setMySend(true);
			} else {
				// 文件不是我发的
				con.setStringData(false);
				con.setMySend(false);
			}
			chatData.add(con);

		}
		chatAdapter.notifyDataSetChanged();
		chatListview.setSelection(chatListview.getCount());
		cursor.close();

	}

	class ChatAdapter extends BaseAdapter {

		@Override
		public boolean isEnabled(int position) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public int getViewTypeCount() {
			// TODO Auto-generated method stub
			return 2;
		}

		@Override
		public boolean isEmpty() {
			// TODO Auto-generated method stub
			return chatData.size() == 0 ? true : false;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return chatData.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return chatData.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public int getItemViewType(int position) {
			// TODO Auto-generated method stub
			Conversation item = chatData.get(position);
			if (item.isMySend() && item.isStringData()) {
				return 0;
			} else {
				return 1;
			}

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Conversation item = chatData.get(position);

			System.out.println("----isMySend------" + item.isMySend());
			if (convertView == null) {
				if (item.isMySend() && item.isStringData()) {
					convertView = View.inflate(ChatActivity.this,
							R.layout.message_right, null);
					ChatHolder holder = new ChatHolder();
					holder.text = (TextView) convertView
							.findViewById(R.id.message_right);
					convertView.setTag(holder);
				} else if (!item.isMySend() && item.isStringData()) {

					convertView = View.inflate(ChatActivity.this,
							R.layout.message_left, null);
					ChatHolder holder = new ChatHolder();
					holder.text = (TextView) convertView
							.findViewById(R.id.message_left);
					convertView.setTag(holder);
				}

			}
			ChatHolder holder = (ChatHolder) convertView.getTag();
			if (item.isStringData()) {
				holder.text.setText(item.getMessage());

			}

			return convertView;
		}

	}

	public void back(View v) {

		if (timer != null) {
			timer.cancel();
		}
		finish();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (timer != null) {
				timer.cancel();
			}

		}

		return super.onKeyDown(keyCode, event);

	}

	class SendEdit implements TextWatcher {

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			int size = s.length();
			if (size > 0) {
				send.setImageResource(R.drawable.send);
				isFile = false;
			} else {
				send.setImageResource(R.drawable.addfile);
				isFile = true;

			}
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub

		}

	}

	class ConnectionTask extends TimerTask {

		private Handler handler;
		private String hostId;
		private String friendId;

		public ConnectionTask(Handler handler, String hostId, String friendId) {
			this.handler = handler;
			this.hostId = hostId;
			this.friendId = friendId;
		}

		private String Get_Path = "http://192.168.191.1/FileShare/user/getMessage";
		private boolean isDia = false;

		@Override
		public void run() {

			Map<String, String> data = new Hashtable<String, String>();
			data.put("head", "GetMessage");
			data.put("hostId", hostId);
			data.put("friendId", friendId);
			String xml = XMLTools.SimpleMakeXML(data);

			String result = SendXMLToWeb.sendXMLToWeb(Get_Path, xml);

			if (result == null && isDia) {

				isDia = false;
				handler.sendEmptyMessage(0x21);
				return;
			}
			if (result != null) {
				isDia = false;
				// 获取到了内容
				if (result.length() < 15) {
					// 表示现在是没有有用的数据的
					return;
				}

				List<Conversation> re = XMLTools.getMessage(result);

				// 发送获得到的消息
				System.out
						.println("this is in the  chatActivity-----" + result);
				Message mes = new Message();
				mes.what = 0x23;
				mes.obj = re;
				handler.sendMessage(mes);

			}
		}

	}

}
