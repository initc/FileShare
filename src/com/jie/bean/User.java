package com.jie.bean;

import android.graphics.drawable.Drawable;

public class User {
	// name��ƴ��
	private String pinyin;

	// nameת����ƴ����������ĸ
	private String firstLetter;
	// ����
	private String name;
	
	// �����Ϣ
	private String info;
	// �Ա�
	private String sex;
	// ��¼��
	private String loginId;
	// ͷ��
	private Drawable headInfo;
	// ָ���Ƿ�Ϊ��ĸ
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
