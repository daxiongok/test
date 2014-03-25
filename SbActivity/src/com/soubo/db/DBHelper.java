package com.soubo.db;

import java.util.List;
import com.soubo.entity.UrlAddress;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper {

	private SQLiteDatabase db;
	private DatabaseHelper dbHelper;
	public final static byte[] _writeLock = new byte[0];

	// �����ݿ�
	public void OpenDB(Context context) {
		dbHelper = new DatabaseHelper(context);
		db = dbHelper.getWritableDatabase();
	}

	// �ر����ݿ�
	public void Close() {
		dbHelper.close();
		if (db != null) {
			db.close();
		}
	}

	/**
	 * ����
	 * 
	 * @param list
	 * @param table
	 *  ����
	 */
	public void Insert(List<ContentValues> list, String tableName) {
		synchronized (_writeLock) {
			db.beginTransaction();
			try {
				db.delete(tableName, null, null);
				for (int i = 0, len = list.size(); i < len; i++)
					db.insert(tableName, null, list.get(i));
				db.setTransactionSuccessful();
			} finally {
				db.endTransaction();
			}
		}
	}

	public DBHelper(Context context) {
		this.dbHelper = new DatabaseHelper(context);
		this.dbHelper.getWritableDatabase();
	}

	/**
	 * ���ڳ�ʼ�����ݿ�
	 * 
	 * @author admin
	 */
	public static class DatabaseHelper extends SQLiteOpenHelper {
		// �������ݿ�����
		private static final String DB_NAME = UrlAddress.DB_FILE_NAME;
		// �������ݿ�汾
		private static final int DB_VERSION = 1;

		public DatabaseHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onOpen(SQLiteDatabase db) {
			super.onOpen(db);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			CreateProductDB(db);
			Log.i("DBHelper", "������Ʒ��ɹ�");
			CreateUserDB(db);
			Log.i("DBHelper", "�����û���ɹ�");
			CreateBuyGoods(db);
			Log.i("DBHelper", "����������Ʒ��ɹ�");
		}


		/**
		 * ����product_info��
		 * 
		 * @param db
		 */
		private void CreateProductDB(SQLiteDatabase db) {
			StringBuilder sb = new StringBuilder();
			sb.append("CREATE TABLE [product_info] (");
			sb.append("[goods_id] INTEGER(13) NOT NULL DEFAULT (0),");
			sb.append("[goods_name] NVARCHAR(50) NOT NULL DEFAULT (''),");
			sb.append("[default_image] NVARCHAR(900) NOT NULL DEFAULT (''),");
			sb.append("[price] double NOT NULL DEFAULT (''))");
			db.execSQL(sb.toString());
		}
		
		/**
		 * �����û���user_info����
		 */
		private void CreateUserDB(SQLiteDatabase db){
			StringBuilder sb=new StringBuilder();
			sb.append("create table [user_info] (");
			sb.append("[user_id] int not null default(0),");
			sb.append("[user_name] nvarchar(50) not null default(''),");
			sb.append("[password] nvarchar(50) not null default(''),");
			sb.append("[email] nvarchar(50) not null default(''),");
			sb.append("[status] int not null default(0))");
			db.execSQL(sb.toString());
		}
		/**
		 * ����������Ʒ��
		 */
		private void CreateBuyGoods(SQLiteDatabase db){
			StringBuffer sb=new StringBuffer();
			sb.append("create table [buy_goods] (");
			sb.append("[goods_id] int not null default(0),");
			sb.append("[goods_name] nvarchar(50) not null default(''),");
			sb.append("[goods_num] int not null default(0),");
			sb.append("[goods_price] double not null default(''),");
			sb.append("[goods_photo] nvarchar(500) not null default(0),");
			sb.append("[unit] nvarchar(50) not null default(''))");
			db.execSQL(sb.toString());
		} 
		
		/**
		 * ���°汾ʱ���±�
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			DropTable(db);
			onCreate(db);
			Log.e("User", "onUpgrade");
		}

		/**
		 * ɾ����
		 * 
		 * @param db
		 */
		private void DropTable(SQLiteDatabase db) {
			StringBuilder sb = new StringBuilder();
			sb.append("DROP TABLE IF EXISTS " + UrlAddress.TABLE_PRODUCT_TYPE + ";");
			sb.append("DROP TABLE IF EXISTS " + UrlAddress.TABLE_PRODUCT+ ";");//��Ʒ��Ϣ
			sb.append("drop table if exists "+UrlAddress.TABLE_USER+ ";");//�û�
			sb.append("drop table if exists "+UrlAddress.TABLE_BUY_GOODS+";");//������Ʒ
			db.execSQL(sb.toString());
		}

		/**
		 * ������ݱ�������������ݣ�
		 * 
		 * @param db
		 */
		public static void ClearData(Context context) {
			DatabaseHelper dbHelper = new DBHelper.DatabaseHelper(context);
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			StringBuilder sb = new StringBuilder();
			sb.append("DELETE FROM product_info;");// �����Ʒ��
			sb.append("DELETE FROM product_type_info;");// �����Ʒ���ͱ�
			sb.append("DELETE FROM user_info;");// ����û���
			sb.append("delete from buy_goods;");//��չ�����Ʒ��Ϣ
			db.execSQL(sb.toString());
		}

	}
}
