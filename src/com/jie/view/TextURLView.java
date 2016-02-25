package com.jie.view;



import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jie.fileshare.R;

public class TextURLView extends LinearLayout {
    private  Context context;
    private  TextView textUri;
	public TextURLView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		init(context);
	}

	public TextURLView(Context context) {
		super(context);
		this.context=context;
		init(context);
	}
	private  void   init(Context context){
		
		LayoutInflater.from(context).inflate(R.layout.common_url_textview, this);
		textUri=(TextView) findViewById(R.id.tv_url_view);
		
	}
	public void setUrlOnclickListener(OnClickListener listener){
		textUri.setOnClickListener(listener);
	}
	public void setText(int text){
		
		textUri.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		textUri.setText(text);
	}
}
