package com.jie.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SpUtil {

	private static final String NAME = "userInfo";
	private static SpUtil instance;
	static {
		instance = new SpUtil();
	}

	public static SpUtil getInstance() {
		if (instance == null) {
			instance = new SpUtil();
		}
		return instance;
	}

	public static SharedPreferences getSharePerference(Context context) {
		return context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
	}

	/**
	 * 查看是否已经登录了
	 * 
	 * @param sp
	 * @return
	 */
	public static boolean isHaveLogIn(SharedPreferences sp) {
		return sp.getBoolean("isHaveLogIn", false);
	}

	public static void setLogIn(SharedPreferences sp) {
		Editor editor = sp.edit();
		editor.putBoolean("isHaveLogIn", true);
		editor.commit();

	}

	public static void setStringSharedPerference(SharedPreferences sp,
			String key, String value) {
		Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();

	}
	public static void removeStringSharePerference(SharedPreferences sp,
			String key){
		Editor editor=sp.edit();
		editor.remove(key);
		editor.commit();
	
	}
	public static void clearLogin(SharedPreferences sp){
		Editor editor=sp.edit();
		editor.remove("username");
		editor.remove("password");
		editor.commit();
		
	}
	
	public static void setBooleanSharedPerference(SharedPreferences sp,
			String key, boolean value) {
		Editor editor = sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

}
