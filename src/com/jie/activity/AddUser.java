package com.jie.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jie.fileshare.R;
import com.jie.net.AddOneUser;
import com.jie.utils.SpUtil;

public class AddUser extends Activity {

	private EditText input;
	private Button bt;
	Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			if(msg.what==0x42){
				Toast.makeText(AddUser.this, "没有此用户", Toast.LENGTH_SHORT).show();
				
			}else if(msg.what==0x43){
				
				Toast.makeText(AddUser.this, "你和他已经是好友", Toast.LENGTH_SHORT).show();
			}else if(msg.what==0x45){
				
				Toast.makeText(AddUser.this, "添加成功", Toast.LENGTH_SHORT).show();
				setResult(0x89);
				finish();
			}
			
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adduser);

		input = (EditText) findViewById(R.id.adduser);
		bt = (Button) findViewById(R.id.adduserBT);
		bt.setOnClickListener(new MyListener());
	}

	class MyListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			String id = input.getText().toString();
			if (null == id || id.equals("")) {

				Toast.makeText(AddUser.this, "内容不可为空", Toast.LENGTH_SHORT)
						.show();

				return;
			}

			SharedPreferences sh=SpUtil.getSharePerference(AddUser.this);
			String hostId=sh.getString("loginId", "");
			AddOneUser oneUser = new AddOneUser(handler, AddUser.this);
			
			oneUser.add(hostId, id);
			
			
		}

	}

}
