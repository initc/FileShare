package com.jie.activity;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.jie.bean.FileMode;
import com.jie.fileshare.R;
import com.jie.net.SendXMLToWeb;
import com.jie.utils.SpUtil;
import com.jie.xml.XMLTools;

public class FileService extends Service {

	private Handler handler = new Handler() {

		@SuppressLint("NewApi")
		public void handleMessage(Message msg) {

			if (msg.what == 0x26) {

				System.out.println("get data");
				String xml = (String) msg.obj;
				List<FileMode> files = XMLTools.getFiles(xml);
				// 创建notification
				Intent intent = new Intent(FileService.this,
						ListDownFiles.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				PendingIntent pending = PendingIntent.getActivity(
						getApplicationContext(), 1, intent,
						PendingIntent.FLAG_UPDATE_CURRENT);
				
				NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				Builder builder = new Notification.Builder(
						getApplicationContext());
				builder.setSmallIcon(R.drawable.notifi);
				builder.setTicker("你有新的文件!!!");
				builder.setWhen(System.currentTimeMillis());
				builder.setContentText("一个来自朋友的文件!点击查看!!!");
				builder.setContentTitle("新文件");
				builder.setContentIntent(pending);
				builder.setAutoCancel(true);
				manager.notify(1, builder.build());
				
			}

		};

	};
	private LocationClient locationClient;
	private FileBinder fileBinder;
	private String hostId;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return fileBinder;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		fileBinder = new FileBinder();
		locationClient = new LocationClient(this);
		MyLocationListenner lis = new MyLocationListenner();
		locationClient.registerLocationListener(lis);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(5000);
		locationClient.setLocOption(option);
		locationClient.start();
		SharedPreferences sh = SpUtil.getSharePerference(this);
		hostId = sh.getString("loginId", "");
		// 百度地图的定位收集数据

	}

	class P {
		String x;
		String y;

		public String getX() {
			return x;
		}

		public void setX(String x) {
			this.x = x;
		}

		public String getY() {
			return y;
		}

		public void setY(String y) {
			this.y = y;
		}
	}

	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null) {
				return;
			}

			P pp = new P();
			pp.setX(location.getLatitude() + "");
			pp.setY(location.getLongitude() + "");

			new Thread(new Heart(pp, hostId, handler)).start();
			/*
			 * baiduMap.setMyLocationData(locData); if (isFirstLoc) { isFirstLoc
			 * = false; LatLng ll = new LatLng(location.getLatitude(),
			 * location.getLongitude()); MapStatus.Builder builder = new
			 * MapStatus.Builder(); builder.target(ll).zoom(18.0f);
			 * baiduMap.animateMapStatus
			 * (MapStatusUpdateFactory.newMapStatus(builder.build())); }
			 */

		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	public class FileBinder extends Binder {

		public FileService getService() {

			return FileService.this;
		}

	}

	class Heart implements Runnable {
		private P point;
		private Handler handler;
		private String hostId;
		private static final String ip = "http://192.168.191.1/FileShare/DealHeart";

		public Heart(P point, String hostId, Handler handler) {

			this.point = point;
			this.handler = handler;
			this.hostId = hostId;
		}

		@Override
		public void run() {
			Map<String, String> data = new Hashtable<String, String>();
			data.put("head", "heart");
			data.put("hostId", hostId);
			data.put("latitude", point.getX() + "");
			data.put("longitude", point.getY() + "");
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
