package com.jie.bean;

import android.graphics.drawable.Drawable;

public class User {
	// name的拼音
	private String pinyin;

	// name转换成拼音后后的首字母
	private String firstLetter;
	// 名字
	private String name;
	
	// 简介信息
	private String info;
	// 性别
	private String sex;
	// 登录号
	private String loginId;
	// 头像
	private Drawable headInfo;
	// 指定是否为字母
	private boolean isUser;

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public boolean isUser() {
		return isUser;
	}

	public void setUser(boolean isUser) {
		this.isUser = isUser;
	}

	public String getFirstLetter() {
		return firstLetter;
	}

	public void setFirstLetter(String firstLetter) {
		this.firstLetter = firstLetter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public Drawable getHeadInfo() {
		return headInfo;
	}

	public void setHeadInfo(Drawable headInfo) {
		this.headInfo = headInfo;
	}
}
