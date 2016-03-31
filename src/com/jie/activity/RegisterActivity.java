package com.jie.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jie.fileshare.R;
import com.jie.net.UserRegister;

public class RegisterActivity extends Activity {
	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {

			// 确实收到系统的返回结果的调用
			if (msg.what == 0x20) {
				if (msg.obj == null) {
					Toast.makeText(RegisterActivity.this, "重新注册",
							Toast.LENGTH_SHORT).show();
					return;
				}
				@SuppressWarnings("unchecked")
				Map<String, String> re = (Map<String, String>) msg.obj;
				String result = re.get("result");
				if (result == null || result.equals("false")) {
					Toast.makeText(RegisterActivity.this, "重新注册",
							Toast.LENGTH_SHORT).show();
					return;
				}
				Toast.makeText(RegisterActivity.this, "注册成功",
						Toast.LENGTH_SHORT).show();
				// 注册成功了 diglog显示分享号
				Builder dialog = new AlertDialog.Builder(
						RegisterActivity.this);
				dialog.setMessage("分享号为"+re.get("loginId"));
				dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						setResult(0x26, null);
						finish();
					}
				});
				dialog.create().show();

			}
			// 注册失败时
			if (msg.what == 0x44) {

				Toast.makeText(RegisterActivity.this, "注册失败",
						Toast.LENGTH_SHORT).show();
			}
			if (msg.what == 0x41) {
				setResult(0x46);
				finish();
			}
		};

	};
	private RelativeLayout back;// 返回
	private EditText name;
	private EditText password;
	private EditText surePassword;
	private Button register;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		back = (RelativeLayout) findViewById(R.id.register_back);
		name = (EditText) findViewById(R.id.register_name);
		password = (EditText) findViewById(R.id.register_password);
		surePassword = (EditText) findViewById(R.id.register_sure_password);
		register = (Button) findViewById(R.id.register_sure);
		addAction();
	}

	private void addAction() {
		// 返回登录界面
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 客户端中的发送错误是在1-5 服务端中是6-10
				setResult(0x41, null);
				finish();
			}
		});
		// 注册按钮时间
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String nameS = name.getText().toString();
				if (nameS.equals("")) {
					Toast.makeText(RegisterActivity.this, "用户名不可为空",
							Toast.LENGTH_SHORT).show();
					return;
				}

				String passOne = password.getText().toString();
				String passTwo = surePassword.getText().toString();
				if (!passOne.equals(passTwo)) {
					Toast.makeText(RegisterActivity.this, "密码不一致",
							Toast.LENGTH_SHORT).show();
					return;
				}
				Map<String, String> xml = new HashMap<String, String>();
				xml.put("head", "register");
				xml.put("username", nameS);
				xml.put("password", passOne);
				UserRegister reg = new UserRegister(getApplicationContext(),
						handler);
				reg.register(xml);
			}
		});
	}

}
