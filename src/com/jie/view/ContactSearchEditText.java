package com.jie.view;

import com.jie.fileshare.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

/**
 * �Զ����EditText ��Ҫ����������һ���ַ����������������Ӧ���û���Ϣ ����ʹ������˵����һ����ͨ��EditText
 * --���Ǵ˿ռ�����Զ�����ȡ��ͼ��
 * 
 * @author lenovo
 *
 */
public class ContactSearchEditText extends EditText implements TextWatcher {

	private Drawable rightDrawable;

	public ContactSearchEditText(Context context) {
		this(context, null);
		init();
	}

	public ContactSearchEditText(Context context, AttributeSet attrs) {
		// ���ﹹ�췽��Ҳ����Ҫ����������ܶ����Բ�����XML���涨��
		this(context, attrs, android.R.attr.editTextStyle);
		init();
	}

	public ContactSearchEditText(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init();

	}

	// ��ʼ��
	private void init() {
		rightDrawable = getCompoundDrawables()[2];
		if (rightDrawable == null) {

			rightDrawable = getResources().getDrawable(
					R.drawable.cancel_edittext);
		}
		rightDrawable.setBounds(0, 0, rightDrawable.getIntrinsicWidth(), rightDrawable.getIntrinsicHeight());
		addTextChangedListener(this);
		
	}
	/**
	 * �����ȡ����ť����Ҫ���edit�е��ַ�
	 * ���ǲ���ҪȥΪrightDrawable���ü���   ��Ϊ��ͼƬ���������Ǵ���
	 */
	 @Override 
	    public boolean onTouchEvent(MotionEvent event) { 
	        if (getCompoundDrawables()[2] != null) { 
	            if (event.getAction() == MotionEvent.ACTION_UP) { 
	            	boolean touchable = event.getX() > (getWidth() 
	                        - getPaddingRight() - rightDrawable.getIntrinsicWidth()) 
	                        && (event.getX() < ((getWidth() - getPaddingRight())));
	                if (touchable) { 
	                    this.setText(""); 
	                } 
	            } 
	        } 
	 
	        return super.onTouchEvent(event); 
	    } 
	 
	private void setRightDrawable(boolean is) {
		Drawable _new = is ? rightDrawable : null;
		setCompoundDrawables(getCompoundDrawables()[0],
				getCompoundDrawables()[1], _new, getCompoundDrawables()[3]);

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	/**
	 * ��textΪ��ʱ����rightdrawable=null
	 * 
	 */
	@Override
	public void onTextChanged(CharSequence s, int start, int count, int after) {
		setRightDrawable(s.length() > 0);
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

	}

}
