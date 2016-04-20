package com.jie.fragment;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class ConnectionService extends Service {

	private CallBackMessage  callBack;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return new MyBinder();
	}

	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}


	class MyBinder  extends Binder{
		
		
		
	}
	
	interface   CallBackMessage {
		
		
	}
	
}
