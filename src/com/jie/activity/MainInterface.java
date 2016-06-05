package com.jie.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jie.activity.FileService.FileBinder;
import com.jie.file.MainInterfaceBean;
import com.jie.fileshare.R;
import com.jie.fragment.ContactFragment;
import com.jie.fragment.ConversationFragment;
import com.jie.fragment.MeFragment;

@SuppressLint("NewApi")
public class MainInterface extends FragmentActivity {
	// 三个fragment界面
	private Fragment conversation;
	private ContactFragment contact;
	private MeFragment me;

	// 用来添加用户的按钮
	private ImageView add;
	// 下面都是此界面的的控件
	// 我使用一个bean来进行存储
	private MainInterfaceBean mainUser = new MainInterfaceBean();

	private FragmentManager fManager;
	// 此为fragment
	private View currentView;

	private ServiceConnection con;
	private FileBinder fileBinder;
	// 返回定时
	private Timer timer = new Timer();
	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {

			if (msg.what == 0x11) {

				isBack = false;
				timer.cancel();
			}

		};
	};

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.main_interface);
		fManager = getSupportFragmentManager();
		findView();
		setEventClick();

		con = new ServiceConnection() {

			@Override
			public void onServiceDisconnected(ComponentName name) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				// TODO Auto-generated method stub
				fileBinder = (FileBinder) service;

			}
		};
		Intent ser = new Intent(this, FileService.class);
		startService(ser);
		
	}

	private void findView() {
		// 初始化 mainuser 分别初始化 三个imagebutton
		mainUser.setConversation((ImageButton) findViewById(R.id.user_conversation));
		mainUser.setContact((ImageButton) findViewById(R.id.user_contact));
		mainUser.setMe((ImageButton) findViewById(R.id.user_me));
		mainUser.setRlConversation((RelativeLayout) findViewById(R.id.rl_conversation));
		mainUser.setRlContact((RelativeLayout) findViewById(R.id.rl_contact));
		mainUser.setRlMe((RelativeLayout) findViewById(R.id.rl_me));
		add = (ImageView) findViewById(R.id.title_add);
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
		mainUser.getContact().performClick();

		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(MainInterface.this, AddUser.class);
				startActivityForResult(intent, 0x88);

			}
		});

	}

	// 获取得到的user数据
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 0x88 && resultCode == 0x89) {
			// 跟新数据
			contact.update();

		}

	}

	private boolean isBack = false;

	public void onBackPressed() {
		if (isBack) {
			isBack = false;
			timer.cancel();
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			startActivity(intent);
		} else {
			isBack = true;
			timer = new Timer();
			Toast.makeText(this, "再按一次推出", 0).show();

			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					handler.sendEmptyMessage(0x11);
				}
			}, 5000);

		}

	};

	/**
	 * 最近消息的点击事件
	 * 
	 * @author lenovo
	 *
	 */
	Fragment mfragment;

	private class ConversationClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (conversation == null) {
				conversation = new ConversationFragment();
			}

			// 使添加不可用
			add.setEnabled(false);
			FragmentTransaction tran = fManager.beginTransaction();
			if (conversation.isAdded()) {
				tran.hide(mfragment);
				tran.show(conversation).commit();
				((ConversationFragment) conversation).updateDB();
			} else {
				tran.hide(mfragment);
				tran.add(R.id.main_frame, conversation, "conversation")
						.show(conversation).commit();

			}

			mfragment = conversation;

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
	int count = 0;

	private class ContactClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (contact == null) {
				contact = new ContactFragment();
				Log.v("is get clcik ", "数据好像重新获取了 " + count++);
			}

			if (mfragment == null) {
				mfragment = contact;
			}
			// 使添加不可用
			add.setEnabled(true);
			FragmentTransaction tran = fManager.beginTransaction();
			if (contact.isAdded()) {

				tran.hide(mfragment);

				tran.show(contact).commit();

			} else {
				if (mfragment != contact)
					tran.hide(mfragment);
				tran.add(R.id.main_frame, contact, "contact").show(contact)
						.commit();

			}
			mfragment = contact;
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

			// 使添加不可用
			add.setEnabled(false);
			
			if (me == null) {
				me = new MeFragment();
			}
			
			FragmentTransaction tran = fManager.beginTransaction();
			if (me.isAdded()) {

				tran.hide(mfragment);

				tran.show(me).commit();

			} else {
				tran.hide(mfragment);
				tran.add(R.id.main_frame, me, "me").show(me)
						.commit();

			}
			mfragment = me;
			
			
			
			
			
			
			
			
			
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
