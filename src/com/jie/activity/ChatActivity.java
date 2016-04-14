package com.jie.activity;

import java.util.LinkedList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jie.DBUtils.DBHelper;
import com.jie.bean.ChatHolder;
import com.jie.bean.Conversation;
import com.jie.fileshare.R;
import com.jie.utils.SpUtil;

public class ChatActivity extends Activity {

	private ListView chatListview;
	// 用来记录所有的消息
	List<Conversation> chatData;
	ChatAdapter chatAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);
		chatListview = (ListView) findViewById(R.id.chatListview);
		chatData = new LinkedList<Conversation>();
		chatAdapter = new ChatAdapter();
		chatListview.setAdapter(chatAdapter);
		Intent intent= getIntent();
		initMessageData(intent.getStringExtra("FriendId"));
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
		String[] selectArgs = new String[] { loginId ,friendId};
		String orderBy = Conversation.COLUMN_NAME_TIME;
		Cursor cursor = sqlTable.query(true, Conversation.TABLE_NAME, columns,
				Conversation.COLUMN_NAME_HOSTLOGINID + " =? and "
						+ Conversation.COLUMN_NAME_MESSAGE + " is not null and "+Conversation.COLUMN_NAME_FRIENDLOGINID+"=?",
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
		public View getView(int position, View convertView, ViewGroup parent) {
			Conversation item = chatData.get(position);
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

}
