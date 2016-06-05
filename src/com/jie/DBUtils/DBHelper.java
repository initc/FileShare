package com.jie.DBUtils;

import com.jie.bean.Conversation;
import com.jie.bean.FileMode;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private static String NAME = "fileshare";
	private static int VERSION = 1;

	

	
	
	public DBHelper(Context context) {
		super(context, NAME, null, VERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(Conversation.TABLE_SQL);
		db.execSQL(FileMode.SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
