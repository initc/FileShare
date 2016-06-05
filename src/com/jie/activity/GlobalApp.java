package com.jie.activity;

import com.baidu.mapapi.SDKInitializer;

import android.app.Application;

public class GlobalApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		SDKInitializer.initialize(this);
	}
	
}
