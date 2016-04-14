package com.jie.bean;

public class Conversation {

	public static String TABLE_NAME = "Conversation";
	public static String COLUMN_NAME_HOSTLOGINID = "HostloginId";
	public static String COLUMN_NAME_FRIENDLOGINID = "FriendLoginId";
	public static String COLUMN_NAME_MESSAGE = "Message";
	public static String COLUMN_NAME_TIME = "Time";
	public static String COLUMN_NAME_FRIENDNAME = "FriendName";
	public static String COLUMN_NAME_TYPE = "TYPE";
	public static String TABLE_SQL = "create table " + TABLE_NAME + " ("
			+ COLUMN_NAME_HOSTLOGINID + " varchar(20) ,"
			+ COLUMN_NAME_FRIENDLOGINID + " varchar(20) ,"
			+ COLUMN_NAME_MESSAGE + " varchar(500) ," + COLUMN_NAME_TIME
			+ " varchar(20),"+  COLUMN_NAME_FRIENDNAME+"  varchar(50) ,"+COLUMN_NAME_TYPE  +" integer)"  ;
	//聊天的数据
	private String message;
	//聊天的时间
	private String time;   
	 //是否是字符内容
	private boolean isMySend;
	public boolean isMySend() {
		return isMySend;
	}

	public void setMySend(boolean isMySend) {
		this.isMySend = isMySend;
	}

	private boolean  isStringData;
	public boolean isStringData() {
		return isStringData;
	}

	public void setStringData(boolean isStringData) {
		this.isStringData = isStringData;
	}

	private String hostloginId;
	private String friendLoginId;
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getHostloginId() {
		return hostloginId;
	}

	public void setHostloginId(String hostloginId) {
		this.hostloginId = hostloginId;
	}

	public String getFriendLoginId() {
		return friendLoginId;
	}

	public void setFriendLoginId(String friendLoginId) {
		this.friendLoginId = friendLoginId;
	}

}
