package com.jie.bean;

import android.graphics.drawable.Drawable;

public class User {
	// name��ƴ��
	private String pinyin;

	// nameת����ƴ����������ĸ
	private String firstLetter;
	// ����
	private String name;
	// �Լ�����������
	private String id;
	// �����Ϣ
	private String info;
	// �Ա�
	private String sex;
	// ��¼��
	private String loginNumber;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getLoginNumber() {
		return loginNumber;
	}

	public void setLoginNumber(String loginNumber) {
		this.loginNumber = loginNumber;
	}

	public Drawable getHeadInfo() {
		return headInfo;
	}

	public void setHeadInfo(Drawable headInfo) {
		this.headInfo = headInfo;
	}
}
