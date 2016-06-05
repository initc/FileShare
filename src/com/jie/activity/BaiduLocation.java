package com.jie.activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;


public class BaiduLocation extends Activity {

	@SuppressWarnings("unused")
    private MapView map;
	private BaiduMap baiduMap;
	private LocationClient  locationClient;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        
        super.onCreate(savedInstanceState);
       /* setContentView(R.layout.loactionmap);
        map=(MapView) findViewById(R.id.mapview);
        baiduMap=map.getMap();*/
        /*baiduMap.setMyLocationEnabled(true);
        locationClient= new LocationClient(this);
        locationClient.registerLocationListener(new MyLocationListenner());
        
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // ��gps
        option.setCoorType("bd09ll"); // ������������
        option.setScanSpan(1000);
        locationClient.setLocOption(option);
        locationClient.start();*/
        
        Intent intent = getIntent();
        if (intent.hasExtra("x") && intent.hasExtra("y")) {
            // ����intent����ʱ���������ĵ�Ϊָ����
           
            double x=Double.parseDouble(intent.getStringExtra("x"));
            double y=Double.parseDouble(intent.getStringExtra("y"));
            LatLng p = new LatLng(y,x);
            map = new MapView(this,
                    new BaiduMapOptions().mapStatus(new MapStatus.Builder()
                            .target(p).build()));
            
            setContentView(map);
        } 
        
        
        
    }
    boolean isFirstLoc=true;
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view ���ٺ��ڴ����½��յ�λ��
            if (location == null || map == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // �˴����ÿ����߻�ȡ���ķ�����Ϣ��˳ʱ��0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            baiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        // activity ��ͣʱͬʱ��ͣ��ͼ�ؼ�
        map.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // activity �ָ�ʱͬʱ�ָ���ͼ�ؼ�
        map.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // activity ����ʱͬʱ���ٵ�ͼ�ؼ�
        map.onDestroy();
    }

	
	
	
}
