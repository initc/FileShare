package com.jie.bean;

import android.graphics.drawable.Drawable;

public class Person {
	
	private  String  name;
	private  String password;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public Drawable getHeadInfo() {
		return headInfo;
	}
	public void setHeadInfo(Drawable headInfo) {
		this.headInfo = headInfo;
	}
	//Ψһʶ��id
	private String id;
	//�����Ϣ
	private String info;
	private String sex;
	// ͷ��
	private Drawable headInfo;
	
	
	
}
