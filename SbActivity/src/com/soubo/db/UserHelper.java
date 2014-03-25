package com.soubo.db;

import java.util.ArrayList;
import java.util.List;

import com.soubo.entity.UrlAddress;
import com.soubo.entity.UserInfo;

import android.app.DownloadManager.Query;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserHelper {
	private static DBHelper.DatabaseHelper dbHelper;
	public static SQLiteDatabase db;
	public final static byte[] _writeLock = new byte[0];
	private Context context;

	public UserHelper(Context context) {
		dbHelper = new DBHelper.DatabaseHelper(context);
		db = dbHelper.getWritableDatabase();
		this.context = context;
	}

	// 关闭数据连接
	public void close() {
		dbHelper.close();
	}

	// 查询所有用户信息
	public static ArrayList<UserInfo> queryAllUser() {
		if(!db.isOpen()){
			db.isOpen();
		}
		ArrayList<UserInfo> allUser = new ArrayList<UserInfo>();
		//查询表中的所有信息
		Cursor cursor = db.query(UrlAddress.TABLE_USER, null, null, null, null,
				null, null);
		while (cursor.moveToNext()) {
			//取数据库的值存入userInfo集合中
			UserInfo user = new UserInfo();
			user.setUser_id(cursor.getString(cursor.getColumnIndex("user_id")));
			user.setUser_name(cursor.getString(cursor
					.getColumnIndex("user_name")));
			user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
			user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
			user.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
			allUser.add(user);
		}
		//关闭游标
		cursor.close();
		//db.close();
		return allUser;
	}

	// 插入用户信息
	public static boolean adduser(UserInfo user) {
		if(!db.isOpen()){
			db.isOpen();
		}
		//设置添加的值
		ContentValues contentValues = new ContentValues();
		contentValues.put("user_id", user.getUser_id());
		contentValues.put("user_name", user.getUser_name());
		contentValues.put("password", user.getPassword());
		contentValues.put("email", user.getEmail());
		contentValues.put("status", user.getStatus());
		boolean flag = false;
		synchronized (_writeLock) {
			db.beginTransaction();
			try {
				//向表中插入数据
				flag = db.insert(UrlAddress.TABLE_USER, null, contentValues) > 0;
				db.setTransactionSuccessful();
			} finally {
				db.endTransaction();
			}
		}
		return flag;
	}

	// 修改用户信息
	public static boolean updateUser(int status, String user_id) {
		if(!db.isOpen()){
			db.isOpen();
		}
		//设置要修改的值
		ContentValues values = new ContentValues();
		values.put("status", status);
		//修改表中的信息
		boolean flag=db.update(UrlAddress.TABLE_USER, values, "user_id=?",
						new String[] { user_id })>0;
						return flag;
	}
}
