package com.jie.bean;

public class ChatData {
    //�Ƿ����ַ�����
	private boolean  isStringData;
	//�ַ���������
	private String data ;
	//�ļ�������
	private String fileName;
	//���ݵ�ʱ��
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
