package com.jie.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import com.jie.fileshare.R;
import com.jie.utils.SpUtil;

public class WelcomeActivity extends Activity {
	private ImageView iv_welcome;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcomelayout);
		findView(this);
		init(this);
	}
    private  void  findView(Context context){
    	iv_welcome=(ImageView) this.findViewById(R.id.iv_welcome);
    	
    }
    private  void   init(final Context context){
    	iv_welcome.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				SharedPreferences  sp=SpUtil.getSharePerference(context);
				boolean isHaveLogIn=SpUtil.isHaveLogIn(sp);
				if(!isHaveLogIn){
					//���뵽��¼����
					Intent intent= new Intent(WelcomeActivity.this,LoginActivity.class);
					startActivity(intent);
					finish();
				}else{
					//���뵽�����棬�����ڴ�֮ǰ��Ҫ���г�ʼ��
					//������Գ�ʼ�������ϵ�˵�
					
					
				}
			}
		}, 2000);
    	
    	
    }
    
}
