package com.jie.bean;

public class ChatData {
    //是否是字符内容
	private boolean  isStringData;
	//字符内容数据
	private String data ;
	//文件的名字
	private String fileName;
	//数据的时间
	private long time;
	public boolean isStringData() {
		return isStringData;
	}
	public void setStringData(boolean isStringData) {
		this.isStringData = isStringData;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
	
}
