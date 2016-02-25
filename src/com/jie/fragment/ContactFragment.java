package com.jie.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jie.adapter.ContactListAdapter;
import com.jie.bean.User;
import com.jie.fileshare.R;
import com.jie.view.SiderView;

public class ContactFragment extends Fragment {
	private SiderView sider;
	private ListView contactListView;
	private TextView textLoad;
	private View root;
	private List<User> resource;
	private BaseAdapter listAdapter;

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
	}

	// ��ʼ��view
	private void initView() {

		textLoad.setVisibility(View.INVISIBLE);
		sider.setTextLoad(textLoad);
		resource = getUserData();
	}

	private void doEvent() {
		listAdapter = new ContactListAdapter(resource, getContext());
		contactListView.setAdapter(listAdapter);

	}

	public List<User> getUserData() {
		List<User> li = new ArrayList<User>();

		User u1 = new User();
		u1.setName("С��");
		li.add(u1);

		User u2 = new User();
		u2.setName("�ɽ�");
		li.add(u2);

		User u3 = new User();
		u3.setName("�ɽ�");
		li.add(u3);

		User u4 = new User();
		u4.setName("���");
		li.add(u4);

		User u5 = new User();
		u5.setName("�̽�");
		li.add(u5);

		User u6 = new User();
		u6.setName("����");
		li.add(u6);

		User u7 = new User();
		u7.setName("c��");
		li.add(u7);

		User u8 = new User();
		u8.setName("ssss");
		li.add(u8);

		User u9 = new User();
		u9.setName("8855");
		li.add(u9);

		return li;

	}

}
