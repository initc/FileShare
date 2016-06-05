package com.jie.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jie.activity.ShowLocations;
import com.jie.fileshare.R;

public class MeFragment extends Fragment {

	private LinearLayout meHead;
	private LinearLayout file;
	private LinearLayout fileload;
	private LinearLayout  location;
	private LinearLayout  set;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View surface = inflater.inflate(R.layout.melayout, null);
		meHead=(LinearLayout) surface.findViewById(R.id.ll_head);
		file=(LinearLayout) surface.findViewById(R.id.ll_recentlyFile);
		fileload=(LinearLayout) surface.findViewById(R.id.ll_fileload);
		location=(LinearLayout) surface.findViewById(R.id.ll_location);
		set=(LinearLayout) surface.findViewById(R.id.ll_set);
		setClick();
		
		return surface;

	}
	private void setClick(){
		
		location.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent= new Intent(MeFragment.this.getContext(),ShowLocations.class);
				startActivity(intent);
			}
		});
		
		
	}
	

}
