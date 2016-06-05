package com.jie.file;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import android.content.Context;
import android.os.Handler;

import com.jie.utils.FileUtils;

/**
 * 
 * ���ļ��еĹ�����
 * 
 * @author lenovo
 *
 */
public class FileManager {

	private Stack<FileNode> fileStack;

	public FileManager() {
		fileStack = new Stack<FileNode>();
	}

	public int size() {

		return fileStack.size();
	}
    public   void uploadFile(final String path,final Handler handler ,final Context context,final String toid){
    	Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				FileUtils.uploadFile(path,handler,context,toid);
			}
		});
    	thread.start();
    	
    }
	public FileNode InvertFiles(File file) {
		FileNode node = new FileNode();

		if (file.isDirectory() && file.canRead()) {
			List<Map<String, String>> data = new ArrayList<Map<String, String>>();

			for (File f : file.listFiles()) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("filename", f.getName());
				map.put("filepath", f.getPath());
			//	System.out.println(f.getPath());
				data.add(map);
			}
			node.setData(data);
			node.setParentPath(file.getPath());
			node.setFirst(false);
			return node;
		}
		return null;

	}

	public FileNode InvertFiles(String filePath) {
		File file = new File(filePath);
		return InvertFiles(file);
	}

	/**
	 * pushһ���ļ��ڵ�
	 * 
	 * @param fileNode
	 * @return
	 */
	public FileNode pushFileNode(FileNode fileNode) {

		return fileStack.push(fileNode);

	}

	/**
	 * peekһ���ļ��ڵ�
	 * 
	 * @return
	 */
	public FileNode peekFileNode() {

		return fileStack.peek();
	}

	/**
	 * �ж��ļ��ڵ��Ƿ�Ϊ��
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return fileStack.isEmpty();

	}

	/**
	 * ɾ��ջͷ�Ľڵ�
	 * 
	 * @return
	 */
	public FileNode pop() {

		return fileStack.pop();
	}

}
