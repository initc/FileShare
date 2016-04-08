package com.jie.fragment;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.TextView;

import com.jie.adapter.ConversationList;
import com.jie.fileshare.R;

public class ConversationFragment extends Fragment {

	Timer timer = new Timer();
	ConversationList list;
	TextView dialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View conversion = inflater.inflate(R.layout.conversation, null);
		list = (ConversationList) conversion.findViewById(R.id.conversionList);
		//去数据库中经行初始化数据
		list.addDBData();
		
		dialog = (TextView) conversion.findViewById(R.id.noConversation);
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (list.getSize() == 0) {
					dialog.setVisibility(View.VISIBLE);
				} else {
					dialog.setVisibility(View.GONE);
				}

			}
		}, 0, 1000);

		return conversion;
	}

}
