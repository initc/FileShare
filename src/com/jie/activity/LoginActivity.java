package com.jie.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jie.fileshare.R;
import com.jie.net.UserLogin;
import com.jie.utils.SpUtil;
import com.jie.view.TextURLView;

public class LoginActivity extends Activity {
	private View rl_user;
	private Button bt_login;
	private Button bt_register;
	private TextURLView urlText;

	private EditText username;
	private EditText password;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// 登录 number
			if (msg.what == 0x26) {
				// 目前就不需要再进行判断了
				if (msg.obj == null) {
					Toast.makeText(LoginActivity.this, "登录失败",
							Toast.LENGTH_SHORT).show();
					return;
				}
				// 对返回的数据进行判断
				// 主要是对节点result的判断 如果是true就意味的登录成功 否则失败
				@SuppressWarnings("unchecked")
				Map<String, String> data = (Map<String, String>) msg.obj;
				String result = data.get("result");
				if (result == null || result.equals("")
						|| result.equals("false")) {
					Toast.makeText(getApplicationContext(), "登录失败",
							Toast.LENGTH_SHORT).show();
					return;
				}
				SharedPreferences sh=SpUtil.getSharePerference(LoginActivity.this);
				SpUtil.setStringSharedPerference(sh, "loginId", username.getText().toString());
				
				Intent intent = new Intent(LoginActivity.this,
						MainInterface.class);
				startActivity(intent);
			}
			if (msg.what == 0x46) {
				Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT)
						.show();
				return;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		findView();
		init();
	}

	private void findView() {
		rl_user = this.findViewById(R.id.rl_user);
		bt_login = (Button) this.findViewById(R.id.bt_login);
		bt_register = (Button) this.findViewById(R.id.register);
		urlText = (TextURLView) this.findViewById(R.id.tv_forget_password);

		username = (EditText) findViewById(R.id.account);
		password = (EditText) findViewById(R.id.password);
	}

	private void setAnim(View view) {
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.login_anim);
		anim.setFillAfter(true);
		rl_user.startAnimation(anim);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == 0x46) {

			return;
		}
		if (resultCode == 0x26) {

			return;
		}
	}

	private void init() {
		setAnim(rl_user);
		initTvUrl();
		bt_login.setOnClickListener(loginOnClickListener);
		bt_register.setOnClickListener(registerOnClickListener);
	}

	private void initTvUrl() {
		urlText.setText(R.string.forget_password);
	}

	private OnClickListener loginOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			String name = username.getText().toString().trim();
			String pass = password.getText().toString();
			if (name.equals("") || pass.equals("")) {
				Toast.makeText(LoginActivity.this, "用户名或者密码不可为空",
						Toast.LENGTH_SHORT).show();
				return;
			}
			Map<String, String> xml = new HashMap<String, String>();
			xml.put("head", "login");
			xml.put("username", name);
			xml.put("password", pass);
			UserLogin login = new UserLogin(getApplicationContext(), handler);
			login.login(xml);

		}
	};

	private OnClickListener registerOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(LoginActivity.this,
					RegisterActivity.class);
			startActivityForResult(intent, 0x10);
		}
	};

}
