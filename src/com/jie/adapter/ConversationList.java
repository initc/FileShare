package com.jie.adapter;

import java.util.LinkedList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jie.DBUtils.DBHelper;
import com.jie.bean.Conversation;
import com.jie.fileshare.R;
import com.jie.utils.SpUtil;

public class ConversationList extends ListView {

	public ConversationList(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initAdapter();
	}

	public ConversationList(Context context, AttributeSet attrs) {
		super(context, attrs);
		initAdapter();
	}

	public ConversationList(Context context) {
		super(context);
		initAdapter();
	}

	// list��baseAddapter ���������ݵĴ���
	private BaseAdapter listAdapter = null;
	private List<Conversation> conversation = new LinkedList<Conversation>();

	/**
	 * ��Ӧ��һ�ξ����һ��user ���һ��user ��list��
	 * 
	 * @param con
	 */
	public void addConversation(Conversation con) {

		conversation.add(con);
		listAdapter.notifyDataSetChanged();

	}

	// ����list�е�size ��������dialog����ʾ
	public int getSize() {
		return conversation.size();
	}

	/**
	 * ��list�е����е���Ϣ����ӵ�list��ȥ ���Ҹ���list�б�
	 * 
	 * @param cons
	 *            ��ʾ�û������bean����
	 */
	public void addConversation(List<Conversation> cons) {
		conversation.addAll(cons);
		listAdapter.notifyDataSetChanged();
	}

	/**
	 * �����ݿ��л�ȡ���� ������ݿ��� Conversation 
	 * 
	 * ��Ҫ��ȡ ��id ��ϵ��id ����ϵ�˵�name
	 * 
	 * ����������Ҫ��name��ʾ��listview�е�item��
	 */
	@SuppressLint("NewApi")
	public void addDBData() {
		//��Ϊ��������ᱻ���úܶ��   ������Ҫ���  listview�е�ԭ������
		conversation.clear();
		DBHelper db = new DBHelper(getContext());
		SQLiteDatabase sqlTable = db.getReadableDatabase();
		String[] columns = new String[] { Conversation.COLUMN_NAME_HOSTLOGINID,
				Conversation.COLUMN_NAME_FRIENDLOGINID,
				Conversation.COLUMN_NAME_FRIENDNAME };
		SharedPreferences sh = SpUtil.getSharePerference(getContext());
		String loginId = sh.getString("loginId", "");
		String [] selectArgs=new String []{loginId};
		Cursor cursor = sqlTable.query(true, Conversation.TABLE_NAME, columns,
				Conversation.COLUMN_NAME_HOSTLOGINID+" =?", selectArgs, null, null, null, null, null);

		while (cursor.moveToNext()) {
			Conversation con = new Conversation();
			con.setHostloginId(cursor.getString(0));
			con.setFriendLoginId(cursor.getString(1));
			con.setName(cursor.getString(2));
			conversation.add(con);

		}
		listAdapter.notifyDataSetChanged();
		cursor.close();
	}

	private void initAdapter() {
		listAdapter = new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = null;
				ConversationHolder holder = null;
				if (convertView == null) {
					view = View.inflate(getContext(),
							R.layout.conversation_item, null);
					holder = new ConversationHolder();
					holder.name = (TextView) view
							.findViewById(R.id.conversation_user_name);

					view.setTag(holder);

				} else {
					view = convertView;
					holder = (ConversationHolder) convertView.getTag();
				}

				holder.name.setText(conversation.get(position).getName());

				return view;
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return conversation.get(position);
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return conversation.size();
			}
		};
		this.setAdapter(listAdapter);
		this.addDBData();
		listAdapter.notifyDataSetChanged();
		
	}

}
