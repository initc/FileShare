package com.jie.bean;

public class Conversation {

	public static String TABLE_NAME = "Conversation";
	public static String COLUMN_NAME_HOSTLOGINID = "HostloginId";
	public static String COLUMN_NAME_FRIENDLOGINID = "FriendLoginId";
	public static String COLUMN_NAME_MESSAGE = "Message";
	public static String COLUMN_NAME_TIME = "Time";
	public static String COLUMN_NAME_FRIENDNAME = "FriendName";
	public static String TABLE_SQL = "create table " + TABLE_NAME + " ("
			+ COLUMN_NAME_HOSTLOGINID + " varchar(20) ,"
			+ COLUMN_NAME_FRIENDLOGINID + " varchar(20) ,"
			+ COLUMN_NAME_MESSAGE + " varchar(500) ," + COLUMN_NAME_TIME
			+ " varchar(20),"+  COLUMN_NAME_FRIENDNAME+"  varchar(50) )" ;
	private String message;
	private String time;   
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
