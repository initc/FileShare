package com.jie.bean;

public class FileMode {
	public static String SQL="create table file ( fromid varchar(20),toid varchar(20),filename varchar(50),uuidname varchar(50),statu int )";
	private String fromid;
	private String toid;
	private String filename;
	private String uuidname;
	public String getFromId() {
		return fromid;
	}
	public void setFromId(String fromId) {
		this.fromid = fromId;
	}
	public String getToid() {
		return toid;
	}
	public void setToid(String toid) {
		this.toid = toid;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getUuidname() {
		return uuidname;
	}
	public void setUuidname(String uuidname) {
		this.uuidname = uuidname;
	}

}
