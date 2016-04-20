package com.jie.DBUtils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jie.bean.Conversation;

public class DBUser {
	/**
	 * ���һ���հ��û� ����û���û�н��й���Ϣ����
	 * 
	 * @param context
	 * @param values
	 */
	public static void insertUserToConversation(Context context,
			ContentValues values) {

		String[] columns = new String[] { Conversation.COLUMN_NAME_HOSTLOGINID,
				Conversation.COLUMN_NAME_FRIENDLOGINID,
				Conversation.COLUMN_NAME_FRIENDNAME };
		DBHelper db = new DBHelper(context);
		SQLiteDatabase sqlTable = db.getReadableDatabase();
		sqlTable.insert(Conversation.TABLE_NAME, null, values);

	}

	/**
	 * ���һ���û��������Ϣ�����ݿ���ȥ
	 * 
	 * @param context
	 * @param values  �û�  �Է�  �Է�����  ��Ϣ   ʱ��   ����
	 */
	public static void insertMessageToConversation(Context context,
			ContentValues values) {

		String[] columns = new String[] { Conversation.COLUMN_NAME_HOSTLOGINID,
				Conversation.COLUMN_NAME_FRIENDLOGINID,
				Conversation.COLUMN_NAME_FRIENDNAME,
				Conversation.COLUMN_NAME_MESSAGE,
				Conversation.COLUMN_NAME_TIME, Conversation.COLUMN_NAME_TYPE };
		DBHelper db = new DBHelper(context);
		SQLiteDatabase sqlTable = db.getReadableDatabase();
		sqlTable.insert(Conversation.TABLE_NAME, null, values);

	}

	@SuppressLint("NewApi")
	public static boolean queryIsHaveConversion(Context context, String host,
			String friend) {

		DBHelper db = new DBHelper(context);
		SQLiteDatabase sqlTable = db.getReadableDatabase();
		Cursor cursor = sqlTable.query(true, Conversation.TABLE_NAME, null,
				Conversation.COLUMN_NAME_HOSTLOGINID + "=? and "
						+ Conversation.COLUMN_NAME_FRIENDLOGINID + "=?",
				new String[] { host, friend }, null, null, null, null, null);
		int size = cursor.getCount();
		cursor.close();
		return size > 0 ? true : false;

	}

}
