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
	// ������ʾ�����ĸ���ĸ��
	TextView textLoad;
	// ��Ļ��ʾ��
	public static String[] z = { "��", "#", "A", "B", "C", "D", "E", "F", "G",
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
			paint.setTypeface(Typeface.DEFAULT_BOLD);// Ĭ������
			paint.setAntiAlias(true);// ���
			float x = width / 2 - paint.measureText(z[i]) / 2;
			float y = sizeHeight * i + sizeHeight - sizeHeight / 5;
			// y��ָ����ַ��Ļ�׼�ߵ�λ�� ����һ���ַ������Ǹ��»��ߵ�λ��
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
		
		// �����ϻ��߻��µ�ʱ��һ��bug
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
			//���ü����¼�    ��֪ͨlistview����λ��
			if(onSiderClickListener!=null)
				onSiderClickListener.onSelect(z[index]);
			break;
		}
	
		// �¼��Ѿ�������ɲ������´����� ������listview
		return true;
	}

	public interface OnSiderClickListener {
		public void  onSelect(String  ch);
	}
	
}
