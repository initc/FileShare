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
			//登录失败
			if (msg.what == 0x46) {
				Intent intent = new Intent(WelcomeActivity.this,
						LoginActivity.class);
				startActivity(intent);
				finish();
				
			} else if (msg.what == 0x26) {
				//成功
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
				// 还没有登录
				if (!isHaveLogIn) {
					// 进入到登录界面
					Intent intent = new Intent(WelcomeActivity.this,
							LoginActivity.class);
					startActivity(intent);
					finish();
				} else {
					// 进入到主界面，并且在此之前需要进行初始化
					// 列如可以初始化最近联系人等
					// 已经登录过了 所以直接进入主界面
					// 需要先验证用户名等
					
					String name = sp.getString("username", null);
					String password = sp.getString("password", null);
					if (name == null || password == null) {
						SpUtil.clearLogin(sp);
						// 直接进入登录区域
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
