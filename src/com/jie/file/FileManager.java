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
 * 。文件夹的管理者
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
	 * push一个文件节点
	 * 
	 * @param fileNode
	 * @return
	 */
	public FileNode pushFileNode(FileNode fileNode) {

		return fileStack.push(fileNode);

	}

	/**
	 * peek一个文件节点
	 * 
	 * @return
	 */
	public FileNode peekFileNode() {

		return fileStack.peek();
	}

	/**
	 * 判断文件节点是否为空
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return fileStack.isEmpty();

	}

	/**
	 * 删除栈头的节点
	 * 
	 * @return
	 */
	public FileNode pop() {

		return fileStack.pop();
	}

}
