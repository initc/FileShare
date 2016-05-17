package com.jie.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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

import com.jie.bean.User;
import com.jie.file.MainInterfaceBean;
import com.jie.fileshare.R;
import com.jie.fragment.ContactFragment;
import com.jie.fragment.ConversationFragment;

@SuppressLint("NewApi")
public class MainInterface extends FragmentActivity {
	// ����fragment����
	private Fragment conversation;
	private ContactFragment contact;
	private Fragment me;

	// ��������û��İ�ť
	private ImageView add;
	// ���涼�Ǵ˽���ĵĿؼ�
	// ��ʹ��һ��bean�����д洢
	private MainInterfaceBean mainUser = new MainInterfaceBean();

	private FragmentManager fManager;
	// ��Ϊfragment
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
		// ��ʼ�� mainuser �ֱ��ʼ�� ����imagebutton
		mainUser.setConversation((ImageButton) findViewById(R.id.user_conversation));
		mainUser.setContact((ImageButton) findViewById(R.id.user_contact));
		mainUser.setMe((ImageButton) findViewById(R.id.user_me));
		mainUser.setRlConversation((RelativeLayout) findViewById(R.id.rl_conversation));
		mainUser.setRlContact((RelativeLayout) findViewById(R.id.rl_contact));
		mainUser.setRlMe((RelativeLayout) findViewById(R.id.rl_me));
		add = (ImageView) findViewById(R.id.title_add);
	}

	/**
	 * ������ImageButton����clickʱ��
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

	// ��ȡ�õ���user����
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 0x88 && resultCode == 0x89) {
			// ��������
			contact.update();

		}

	}

	/**
	 * �����Ϣ�ĵ���¼�
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

			// ʹ��Ӳ�����
			add.setEnabled(false);
			FragmentTransaction tran = fManager.beginTransaction();
			if(conversation.isAdded()){
				tran.hide(mfragment);
				tran.show(conversation)
						.commit();
				((ConversationFragment)conversation).updateDB();
			}else{
				tran.hide(mfragment);
				tran.add(R.id.main_frame, conversation, "conversation").show(conversation)
				.commit();
				
			}
			

			mfragment = conversation;

			// �Բ� ֮ǰ����һЩС���� �Ű�����߼�д����ô���ӡ�����
			View c = v;
			if (v.getId() == R.id.rl_conversation)
				c = getImageButtonView(v.getId());
			setButton(c);
		}

	}

	/**
	 * ��ϵ�ĵ���¼�
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
				Log.v("is get clcik ", "���ݺ������»�ȡ�� " + count++);
			}

			if (mfragment == null) {
				mfragment = contact;
			}
			// ʹ��Ӳ�����
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
			// �Բ� ֮ǰ����һЩС���� �Ű�����߼�д����ô���ӡ�����
			View c = v;
			if (v.getId() == R.id.rl_contact)
				c = getImageButtonView(v.getId());
			setButton(c);
		}

	}

	/**
	 * �ҵõ���¼�
	 * 
	 * @author lenovo
	 *
	 */
	private class MeClick implements OnClickListener {

		@Override
		public void onClick(View v) {

			// ʹ��Ӳ�����
			add.setEnabled(false);
			// �Բ� ֮ǰ����һЩС���� �Ű�����߼�д����ô���ӡ�����
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
