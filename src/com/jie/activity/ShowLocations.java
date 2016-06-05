package com.jie.activity;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jie.bean.Location;
import com.jie.fileshare.R;
import com.jie.net.SendXMLToWeb;
import com.jie.xml.XMLTools;

public class ShowLocations extends Activity {

	private List<Location> data = new LinkedList<Location>();
	private Myadapter adapter;
	private ListView listview;
	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {

			if (msg.what == 0x26) {

				String da = (String) msg.obj;
				System.out.println(da);
				List<Location> list = XMLTools.getLocations(da);
				data.clear();
				data.addAll(list);
				adapter.notifyDataSetChanged();
			}

		};

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.locations);
		adapter = new Myadapter();
		listview =(ListView) findViewById(R.id.lo_listview);
		listview.setAdapter(adapter);
		
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Location lo=data.get(position);
				Intent intent= new Intent(ShowLocations.this,BaiduLocation.class);
				
				intent.putExtra("x", lo.getLon());
				intent.putExtra("y", lo.getLat());
				
				startActivity(intent);
				
				
				
			}
			
			
			
			
		});
		new Thread(new GetLocations("555555", handler)).start();

	}

	class Myadapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View v = View.inflate(ShowLocations.this, R.layout.locations_item,
					null);
			TextView tv = (TextView) v.findViewById(R.id.lo_name);
			tv.setText(data.get(position).getName());

			return v;

		}

	}

	class GetLocations implements Runnable {
		private Handler handler;
		private String hostId;
		private static final String ip = "http://192.168.191.1/FileShare/user/GetPoint";

		public GetLocations(String hostId, Handler handler) {

			this.handler = handler;
			this.hostId = hostId;
		}

		@Override
		public void run() {
			Map<String, String> data = new Hashtable<String, String>();
			data.put("head", "GetLocations");
			data.put("hostId", hostId);
			String xml = XMLTools.SimpleMakeXML(data);

			String result = SendXMLToWeb.sendXMLToWeb(ip, xml);
			if (result != null) {
				Message mes = new Message();
				mes.obj = result;
				mes.what = 0x26;
				handler.sendMessage(mes);
			}
		}

	}

}
