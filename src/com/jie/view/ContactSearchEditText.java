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
 * 自定义的EditText 主要功能是输入一个字符串来进行搜索相对应的用户信息 对于使用者来说就是一个普通的EditText
 * --但是此空间可以自动控制取消图标
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
		// 这里构造方法也很重要，不加这个很多属性不能再XML里面定义
		this(context, attrs, android.R.attr.editTextStyle);
		init();
	}

	public ContactSearchEditText(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init();

	}

	// 初始化
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
	 * 当点击取消按钮后需要清除edit中的字符
	 * 我们不需要去为rightDrawable设置监听   因为此图片并不是总是存在
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
	 * 当text为空时设置rightdrawable=null
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
