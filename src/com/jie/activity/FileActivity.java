package com.jie.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.jie.file.FileManager;
import com.jie.file.FileNode;
import com.jie.fileshare.R;
	/**
	 * 这是管理文件传输的activity    
	 * 你可以选择一个文件来进行与服务器的传输
	 * 
	 * @since 2016/2/22
	 * @version 1.0
	 * @author lenovo
	 * 
	 */
public class FileActivity extends Activity {
	private SimpleAdapter adapter;
	private List<Map<String, String>> data;
	private ListView listview;
	private FileManager fileManager;
	private View back_file;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.filemanagerview);
		fileManager = new FileManager();
		listview = (ListView) findViewById(R.id.list);
		back_file = findViewById(R.id.back_file);
		Back_File backButton = new Back_File();
		back_file.setOnClickListener(backButton);
		init();
		listview.setAdapter(adapter);
		// 添加listview的按键的监听事件
		ListViewItemLister lister = new ListViewItemLister();
		listview.setOnItemClickListener(lister);
	}

	class Back_File implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (fileManager.size() < 2) {
				return;

			}
			fileManager.pop();
			FileNode node = fileManager.peekFileNode();

			// 可以回退到上一级
			System.out.println(fileManager.size());
			data.clear();
			data.addAll(node.getData());
			adapter.notifyDataSetChanged();

		}

	}

	class ListViewItemLister implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			FileNode node = fileManager.peekFileNode();
			if (position >= node.size())
				return;
			List<Map<String, String>> da = node.getData();
			Map<String, String> map = da.get(position);
			String path = map.get("filepath");
			System.out.println(path);
			FileNode newNode = fileManager.InvertFiles(path);
			if (newNode == null) {
				// 说明这个文件时文件夹或者是一个不可读的文件
			    //传输文件	
				fileManager.uploadFile(path);
			    Toast.makeText(FileActivity.this, "upload  file  "+map.get("filename"), 0).show();
				return;
			}
			data.clear();
			data.addAll(newNode.getData());
			fileManager.pushFileNode(newNode);
			adapter.notifyDataSetChanged();
		}

	}

	private void init() {
		data = new ArrayList<Map<String, String>>();
		Map<String, String> l_1 = new HashMap<String, String>();
		File file = Environment.getExternalStorageDirectory();
		l_1.put("filename", "SD卡");
		l_1.put("filepath", file.getPath());
		data.add(l_1);
		// 吧第一个节点存储起来
		// 引用问题 我们必须保持data这个变量的纯洁性
		List<Map<String, String>> da = new ArrayList<Map<String, String>>();
		da.addAll(data);
		FileNode node = new FileNode();
		node.setData(da);
		node.setFirst(true);
		node.setParentPath("\\");
		fileManager.pushFileNode(node);
		adapter = new SimpleAdapter(this, data, R.layout.fileadapter,
				new String[] { "filename" }, new int[] { R.id.filename });

	}

}
