package com.jie.file;

import java.util.List;
import java.util.Map;
/**
 * 一个在文件管理中的文件夹层级的数据结构
 * @author lenovo
 *
 */
public class FileNode {

	boolean isFirst;
	List<Map<String,String>>  data;
	private String  parentPath;
	public String getParentPath() {
		return parentPath;
	}
	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}
	public  int size(){
		return data.size();
		
	}
	public boolean isFirst() {
		return isFirst;
	}
	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}
	public List<Map<String, String>> getData() {
		return data;
	}
	public void setData(List<Map<String, String>> list) {
		this.data = list;
	}
	
	
	
}
