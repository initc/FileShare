package com.jie.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.jie.file.MainInterfaceBean;
import com.jie.fileshare.R;
import com.jie.fragment.ContactFragment;
import com.jie.view.ContactSearchEditText;

@SuppressLint("NewApi")
public class MainInterface extends FragmentActivity {
	// 三个fragment界面
	private Fragment conversation;
	private Fragment contact;
	private Fragment me;
	// 下面都是此界面的的控件
	// 我使用一个bean来进行存储
	private MainInterfaceBean mainUser = new MainInterfaceBean();

	private FragmentManager fManager;
	//此为fragment
	private View currentView;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.main_interface);
		fManager = getSupportFragmentManager();
		findView();
		setEventClick();
	}

	private void findView() {
		// 初始化 mainuser 分别初始化 三个imagebutton
		mainUser.setConversation((ImageButton) findViewById(R.id.user_conversation));
		mainUser.setContact((ImageButton) findViewById(R.id.user_contact));
		mainUser.setMe((ImageButton) findViewById(R.id.user_me));
		mainUser.setRlConversation((RelativeLayout) findViewById(R.id.rl_conversation));
		mainUser.setRlContact((RelativeLayout) findViewById(R.id.rl_contact));
		mainUser.setRlMe((RelativeLayout) findViewById(R.id.rl_me));
		
	}

	/**
	 * 给三个ImageButton设置click时间
	 */
	private void setEventClick() {
		ConversationClick conversation = new ConversationClick();
		ContactClick contact = new ContactClick();
		MeClick me = new MeClick();
		mainUser.getRlConversation().setOnClickListener(conversation);
		mainUser.getConversation().setOnClickListener(conversation);
		mainUser.getRlContact().setOnClickListener(contact);
		mainUser.getContact().setOnClickListener(contact);
		mainUser.getRlMe().setOnClickListener(me);
		mainUser.getMe().setOnClickListener(me);
		
	}

	/**
	 * 最近消息的点击事件
	 * 
	 * @author lenovo
	 *
	 */
	private class ConversationClick implements OnClickListener {

		@Override
		public void onClick(View v) {

			// 卧槽 之前犯了一些小错误 才把这个逻辑写的这么复杂。。。
			View c = v;
			if (v.getId() == R.id.rl_conversation)
				c = getImageButtonView(v.getId());
			setButton(c);
		}

	}

	/**
	 * 联系的点击事件
	 * 
	 * @author lenovo
	 *
	 */
	private class ContactClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (contact == null) {
				contact = new ContactFragment();
			}
			
			FragmentTransaction tran = fManager.beginTransaction();
			tran.replace(R.id.main_frame, contact, "contact");
			tran.commit();
			// 卧槽 之前犯了一些小错误 才把这个逻辑写的这么复杂。。。
			View c = v;
			if (v.getId() == R.id.rl_contact)
				c = getImageButtonView(v.getId());
			setButton(c);
		}

	}

	/**
	 * 我得点击事件
	 * 
	 * @author lenovo
	 *
	 */
	private class MeClick implements OnClickListener {

		@Override
		public void onClick(View v) {

			// 卧槽 之前犯了一些小错误 才把这个逻辑写的这么复杂。。。
			View c = v;
			if (v.getId() == R.id.rl_me)
				c = getImageButtonView(v.getId());
			setButton(c);
		}

	}

	private View getImageButtonView(int id) {
		switch (id) {
		case R.id.rl_conversation:
			return mainUser.getConversation();
		case R.id.rl_contact:
			return mainUser.getContact();
		case R.id.rl_me:
			return mainUser.getMe();
		default:
			return null;
		}

	}

	private void setButton(View v) {
		if (currentView != null && currentView.getId() != v.getId()) {

			currentView.setEnabled(true);
		}
		currentView = v;
		currentView.setEnabled(false);
	}

}
