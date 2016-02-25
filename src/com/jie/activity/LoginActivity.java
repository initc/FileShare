package com.jie.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.jie.fileshare.R;
import com.jie.view.TextURLView;

public class LoginActivity extends Activity {
	private View rl_user;
	private Button bt_login;
	private Button bt_register;
	private TextURLView urlText;

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
	}

	private void setAnim(View view) {
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.login_anim);
		anim.setFillAfter(true);
		rl_user.startAnimation(anim);

	}

	private void init() {
		setAnim(rl_user);
		initTvUrl();
		bt_login.setOnClickListener(loginOnClickListener);
		bt_register.setOnClickListener(registerOnClickListener);
	}
	private void initTvUrl(){
		urlText.setText(R.string.forget_password);
	}
	private OnClickListener loginOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			Intent intent = new Intent(LoginActivity.this,MainInterface.class);
			startActivity(intent);
		}
	};

	private OnClickListener registerOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Toast.makeText(LoginActivity.this, "register", 1).show();

		}
	};

}
