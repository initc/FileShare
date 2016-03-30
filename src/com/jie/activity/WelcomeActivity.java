package com.jie.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.jie.fileshare.R;
import com.jie.net.UserLogin;
import com.jie.utils.SpUtil;

public class WelcomeActivity extends Activity {
	private ImageView iv_welcome;
	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			//��¼ʧ��
			if (msg.what == 0x46) {
				Intent intent = new Intent(WelcomeActivity.this,
						LoginActivity.class);
				startActivity(intent);
				finish();
				
			} else if (msg.what == 0x26) {
				//�ɹ�
				Intent intent = new Intent(WelcomeActivity.this,
						MainInterface.class);
				startActivity(intent);
			}

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcomelayout);
		findView(this);
		init(this);
	}

	private void findView(Context context) {
		iv_welcome = (ImageView) this.findViewById(R.id.iv_welcome);

	}

	private void init(final Context context) {
		iv_welcome.postDelayed(new Runnable() {

			@Override
			public void run() {
				SharedPreferences sp = SpUtil.getSharePerference(context);
				boolean isHaveLogIn = SpUtil.isHaveLogIn(sp);
				// ��û�е�¼
				if (!isHaveLogIn) {
					// ���뵽��¼����
					Intent intent = new Intent(WelcomeActivity.this,
							LoginActivity.class);
					startActivity(intent);
					finish();
				} else {
					// ���뵽�����棬�����ڴ�֮ǰ��Ҫ���г�ʼ��
					// ������Գ�ʼ�������ϵ�˵�
					// �Ѿ���¼���� ����ֱ�ӽ���������
					// ��Ҫ����֤�û�����
					
					String name = sp.getString("username", null);
					String password = sp.getString("password", null);
					if (name == null || password == null) {
						SpUtil.clearLogin(sp);
						// ֱ�ӽ����¼����
						Intent login = new Intent(WelcomeActivity.this,
								LoginActivity.class);
						startActivity(login);
						finish();
						return ;
					}

					Map<String, String> xml = new HashMap<String, String>();
					xml.put("head", "register");
					xml.put("username", name);
					xml.put("password", password);
					UserLogin login = new UserLogin(getApplicationContext(),
							handler);
					login.login(xml);

				}
			}
		}, 1500);

	}

}
