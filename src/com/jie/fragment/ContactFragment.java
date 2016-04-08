package com.jie.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jie.DBUtils.DBUser;
import com.jie.adapter.ContactListAdapter;
import com.jie.bean.Conversation;
import com.jie.bean.User;
import com.jie.fileshare.R;
import com.jie.net.AddFriend;
import com.jie.utils.SpUtil;
import com.jie.view.ContactSearchEditText;
import com.jie.view.SiderView;
import com.jie.view.SiderView.OnSiderClickListener;
import com.jie.xml.XMLTools;

public class ContactFragment extends Fragment implements TextWatcher,
		OnSiderClickListener {

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			if (msg.what == 0x26) {
				String xml = (String) msg.obj;
				List<User> users = XMLTools.getContact(xml);
				listAdapter.updateAdaper(users);
			}

		};

	};
	private SiderView sider;
	private ListView contactListView;
	private TextView textLoad;
	private View root;
	private List<User> resource;
	private ContactListAdapter listAdapter;
	ContactSearchEditText searchEdit;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		root = inflater.inflate(R.layout.contact_fragment, null);
		findView();
		initView();// ��ʼ������ ��ȡ������
		// ��ʼ�������� ��������װ����������
		doEvent();
		return root;

	}

	// ��ȡview
	private void findView() {

		sider = (SiderView) root.findViewById(R.id.contact_sider);
		contactListView = (ListView) root.findViewById(R.id.contact_listview);
		textLoad = (TextView) root.findViewById(R.id.contact_load);
		searchEdit = (ContactSearchEditText) root.findViewById(R.id.searchEdit);
	}

	// ��ʼ��view
	private void initView() {

		textLoad.setVisibility(View.INVISIBLE);
		sider.setTextLoad(textLoad);
		resource = getUserData();
	}

	private void doEvent() {
		searchEdit.addTextChangedListener(this);
		listAdapter = new ContactListAdapter(resource, getContext());

		sider.setOnSiderClickListener(this);
		contactListView.setAdapter(listAdapter);

		MyItemClickListner itemListner = new MyItemClickListner();
		contactListView.setOnItemClickListener(itemListner);
		// ������ȡ��Ϣ
		update();

	}

	class MyItemClickListner implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// ��������itemʱ ����Ҫ�����item����Ϣ��д�����ݿ���
			User user = (User) parent.getItemAtPosition(position);
			SharedPreferences sh = SpUtil.getSharePerference(getContext());
			String loginId = sh.getString("loginId", "");
			String friendId=user.getLoginId();
			String friendName=user.getName();
			if(!DBUser.queryIsHaveConversion(getActivity(), loginId, friendId)){
			ContentValues values= new ContentValues();
			values.put(Conversation.COLUMN_NAME_HOSTLOGINID, loginId);
			values.put(Conversation.COLUMN_NAME_FRIENDLOGINID, friendId);
			values.put(Conversation.COLUMN_NAME_FRIENDNAME, friendName);
			//д������
			DBUser.insertUserToConversation(getActivity(), values);
			}
			
			
		}

	}

	// ���»�ȡ�û�������
	public void update() {
		SharedPreferences sh = SpUtil.getSharePerference(getContext());
		String loginId = sh.getString("loginId", "");

		AddFriend con = new AddFriend(handler, getContext());
		con.initFriend(loginId);

	}

	public List<User> getUserData() {
		List<User> li = new ArrayList<User>();

		/*
		 * User u1 = new User(); u1.setName("С��"); li.add(u1);
		 * 
		 * User u2 = new User(); u2.setName("�ɽ�"); li.add(u2);
		 * 
		 * User u3 = new User(); u3.setName("�ɽ�"); li.add(u3);
		 * 
		 * User u4 = new User(); u4.setName("���"); li.add(u4);
		 * 
		 * User u5 = new User(); u5.setName("�̽�"); li.add(u5);
		 * 
		 * User u6 = new User(); u6.setName("����"); li.add(u6);
		 * 
		 * User u7 = new User(); u7.setName("c��"); li.add(u7);
		 * 
		 * User u8 = new User(); u8.setName("ssss"); li.add(u8);
		 * 
		 * User u9 = new User(); u9.setName("8855"); li.add(u9);
		 */

		return li;

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	// ��edittext�����ݱ仯ʱ�����Ķ�
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (s.length() == 0) {

			listAdapter.updateNoNew();
			;
			return;
		}
		listAdapter.fileterString(s);

	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSelect(String ch) {
		// TODO Auto-generated method stub
		int i = listAdapter.getPosition(ch);
		contactListView.setSelection(i);
	}

}
