package com.jie.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.jie.fileshare.R;

public class SiderView extends View {
	// 用来提示按到哪个字母了
	TextView textLoad;
	// 字幕提示表
	public static String[] z = { "☆", "#", "A", "B", "C", "D", "E", "F", "G",
			"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
			"U", "V", "W", "X", "Y", "Z" };
	float width;
	float height;
	float sizeHeight;
	float sizeBug;
    private OnSiderClickListener  onSiderClickListener;
	
	public SiderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public SiderView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setTextLoad(TextView tl) {
		this.textLoad = tl;
	}
	public OnSiderClickListener getOnSiderClickListener() {
		return onSiderClickListener;
	}

	public void setOnSiderClickListener(OnSiderClickListener onSiderClickListener) {
		this.onSiderClickListener = onSiderClickListener;
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub

		super.draw(canvas);
		sizeBug = (float) (sizeHeight / 5 * 2.0);
		width = getWidth();
		height = getHeight();
		sizeHeight = height / z.length;
		Paint paint = new Paint();
		for (int i = 0; i < z.length; i++) {
			paint.setColor(getResources().getColor(R.color.sidertext));
			paint.setTextSize(30);
			paint.setTypeface(Typeface.DEFAULT_BOLD);// 默认字体
			paint.setAntiAlias(true);// 锯齿
			float x = width / 2 - paint.measureText(z[i]) / 2;
			float y = sizeHeight * i + sizeHeight - sizeHeight / 5;
			// y是指这个字符的基准线的位置 就是一个字符下面那个下划线的位置
			canvas.drawText(z[i], x, y, paint);
			paint.reset();
		}

	}

	@SuppressLint("NewApi")
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {

		int index = (int) (event.getY() / getHeight() * z.length);
		if (index > z.length - 1 || index < 0)
			return true;
		
		// 当划上或者滑下的时的一个bug
		if (event.getY() - 5 <= 0 || event.getY() + sizeBug > getHeight()) {
			setBackgroundColor(getResources().getColor(R.color.none));
			if (textLoad != null) {

				textLoad.setVisibility(View.INVISIBLE);
			}
			return true;

		}
		switch (event.getAction()) {

		case MotionEvent.ACTION_UP:
			setBackgroundColor(getResources().getColor(R.color.none));
			if (textLoad != null) {

				textLoad.setVisibility(View.INVISIBLE);
			}
			break;
		default:
			setBackground(getResources()
					.getDrawable(R.drawable.siderbackground));
			;
			if (textLoad != null) {

				textLoad.setText(z[index]);
				textLoad.setVisibility(View.VISIBLE);
			}
			//设置监听事件    来通知listview安排位置
			if(onSiderClickListener!=null)
				onSiderClickListener.onSelect(z[index]);
			break;
		}
	
		// 事件已经处理完成不能向下传播了 下面是listview
		return true;
	}

	public interface OnSiderClickListener {
		public void  onSelect(String  ch);
	}
	
}
