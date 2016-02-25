package com.jie.adapter;

import java.util.Collections;
import java.util.List;
import java.util.zip.Inflater;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jie.bean.User;
import com.jie.charater.utils.CharacterParser;
import com.jie.charater.utils.PinyinComparator;
import com.jie.fileshare.R;

public class ContactListAdapter extends BaseAdapter {

	private List<User> user;
	private Context context;
	private static int ZM_TYPE = 0;
	private static int USER_TYPE = 1;

	public ContactListAdapter() {
	}

	public ContactListAdapter(List<User> list, Context context) {

		user = list;
		changeView(user);
		this.context = context;
	}

	public void updateAdaper(List<User> _list) {
		user = _list;
		changeView(_list);
		notifyDataSetChanged();
	}

	// 实例化汉字转拼音类
	public void changeView(List<User> list) {

		convertData(user);
		sortList(user);

	}

	// 由于我认为数据应该和界面分离 所以关于数据的逻辑应该和适配器相关

	@SuppressLint("DefaultLocale")
	private void convertData(List<User> u) {
		CharacterParser characterParser = CharacterParser.getInstance();
		for (int i = 0; i < u.size(); i++) {
			User user = u.get(i);
			String pinyin = characterParser.getSelling(user.getName())
					.toUpperCase();
			user.setPinyin(pinyin);
			pinyin=pinyin.substring(0,1);
			Log.d("**************", pinyin);
			if (pinyin.matches("[A-Z]")) {
				user.setFirstLetter(pinyin);
			} else {

				user.setFirstLetter("#");
			}
			Log.d("**getFirstLetter***", user.getFirstLetter());
		}

	}

	/**
	 * 
	 * 排序list 并且从原始的list生成一个带字母表的list
	 * 
	 * @param list
	 */
	private void sortList(List<User> list) {
		PinyinComparator pinyinComparator = new PinyinComparator();
		Collections.sort(list, pinyinComparator);
		if (list.size() > 0) {

			User[] users = new User[] {};
			users = list.toArray(users);
			list.clear();
			User user = users[0];
			User u = new User();
			u.setFirstLetter(user.getFirstLetter());
			u.setUser(false);
			list.add(u);
			for (int i = 0; i < users.length; i++) {
				user = users[i];
				Log.d("------------------------", user.getFirstLetter());
				if (!u.getFirstLetter().equals(user.getFirstLetter())) {
					u = new User();
					u.setFirstLetter(user.getFirstLetter());
					u.setUser(false);
					list.add(u);

				}
				user.setUser(true);
				list.add(user);
			}

		}

	}

	// 说明item是否可被点击

	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub

		return user.get(position).isUser();

	}

	// 返回指定位置的类型
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		if (isEnabled(position)) {
			return USER_TYPE;
		}
		return ZM_TYPE;
	}

	// 类型的数量
	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub

		return 2;
	}

	@Override
	public int getCount() {
		return user.size();
	}

	@Override
	public Object getItem(int position) {
		return user.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		UserHolder userH;
		ZMHolder zH;
		User u = user.get(position);
		if (view == null) {
			if (getItemViewType(position) == USER_TYPE) {
				userH = new UserHolder();
				view = LayoutInflater.from(context).inflate(R.layout.user_item,
						null);
				userH.headInfo = (ImageView) view
						.findViewById(R.id.user_headInfo);
				userH.name = (TextView) view.findViewById(R.id.user_name);
				view.setTag(userH);
			} else {
				zH = new ZMHolder();
				view = LayoutInflater.from(context).inflate(R.layout.zm_space,
						null);
				zH.zm = (TextView) view.findViewById(R.id.zm);
				view.setTag(zH);
			}

		}
		if (getItemViewType(position) == USER_TYPE) {
			userH = (UserHolder) view.getTag();
			if (u.getHeadInfo() != null) {
				userH.headInfo.setBackground(u.getHeadInfo());
			}
			if (u.getName() != null) {
				userH.name.setText(u.getName());
			}

		} else {

			zH = (ZMHolder) view.getTag();
			if (u.getFirstLetter() != null) {
				zH.zm.setText(u.getFirstLetter());
			}

		}

		return view;
	}

	// 指定位置是否是user还是字符
	public boolean isUserForPosition(int position) {

		return user.get(position).isUser();
	}

}
