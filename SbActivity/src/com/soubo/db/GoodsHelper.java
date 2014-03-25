package com.soubo.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.soubo.app.DetailProductApp.PageIndex;
import com.soubo.entity.ProductTypeInfo;
import com.soubo.entity.UrlAddress;
import com.soubo.entity.goodsInfo;
import com.soubo.util.JsonUtil;
import com.soubo.util.NetWorkHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GoodsHelper {
	private DBHelper.DatabaseHelper dbHelper;
	private SQLiteDatabase db;
	public final static byte[] _writeLock = new byte[0];
	private Context context;

	public GoodsHelper(Context context) {
		dbHelper = new DBHelper.DatabaseHelper(context);
		db = dbHelper.getWritableDatabase();
	}

	public void Close() {
		dbHelper.close();
	}

	/**
	 * 判断是否已经存在
	 * 
	 * @param goods_id
	 * @return
	 */
	private boolean Exist(int goods_id) {
		String where = "goods_id=?";
		String[] args = { String.valueOf(goods_id) };
		Cursor cursor = db.query(UrlAddress.TABLE_PRODUCT, null, where, args,
				null, null, null);
		boolean isExist = cursor != null && cursor.moveToNext();
		cursor.close();
		return isExist;
	}

	/**
	 * 根据页码返回Goods对象集合
	 * 
	 * @return pageIndex:页码，从1开始
	 */
	public static ArrayList<goodsInfo> GetBlogList(int pageIndex, String type_id) {
		int pageSize = UrlAddress.BLOG_PAGE_SIZE;
		String url = UrlAddress.type_url + "?pageIndex=" + pageIndex
				+ "&cate_id=" + type_id;// 数据地址
		String dataString = NetWorkHelper.getDataDoGet(url);
		System.out.println("sdaf==>"+dataString);
		String str1 = dataString.substring(dataString.indexOf("["),
				dataString.lastIndexOf("]") + 1);
		System.out.println("gooods==>"+str1);
		ArrayList<goodsInfo> list = JsonUtil.jsonGoods(str1);
		return list;
	}

	/**
	 * 分页
	 */
	public List<goodsInfo> GetGoodsByPage(int pageIndex, int pageSize) {
		String limit = String.valueOf((pageIndex - 1) * pageSize) + ","
				+ String.valueOf(pageSize);
		List<goodsInfo> list = GetGoodsByWhere(limit, null, null);

		return list;
	}
	//通过产品id排序查询商品信息
	private List<goodsInfo> GetGoodsByWhere(String limit, String where,
			String[] args) {

		List<goodsInfo> listGoods = new ArrayList<goodsInfo>();
		String orderBy = "goods_id desc";
		Cursor cursor = db.query(UrlAddress.TABLE_PRODUCT, null, where, args,
				null, null, orderBy, limit);
		while (cursor != null && cursor.moveToNext()) {
			goodsInfo entity = new goodsInfo();
			entity.setGoods_id(cursor.getString(cursor
					.getColumnIndex("goods_id")));
			entity.setGoods_name(cursor.getString(cursor
					.getColumnIndex("goods_name")));
			entity.setDefault_image(cursor.getString(cursor
					.getColumnIndex("default_image")));
			entity.setPrice(cursor.getString(cursor.getColumnIndex("price")));
			listGoods.add(entity);
		}
		cursor.close();

		return listGoods;
	}

	/**
	 * 插入
	 * 
	 * @param list
	 */
	public void SynchronyData2DB(List<goodsInfo> goodsList) {
		List<ContentValues> list = new ArrayList<ContentValues>();
		for (int i = 0, len = goodsList.size(); i < len; i++) {
			ContentValues contentValues = new ContentValues();
			contentValues.put("goods_id", goodsList.get(i).getGoods_id());
			contentValues.put("goods_name", goodsList.get(i).getGoods_name());
			contentValues.put("default_image", goodsList.get(i)
					.getDefault_image());
			contentValues.put("price", goodsList.get(i).getPrice());
			list.add(contentValues);
		}
		synchronized (_writeLock) {
			db.beginTransaction();
			try {
				for (int i = 0, len = list.size(); i < len; i++) {
					int goods_id = list.get(i).getAsInteger("goods_id");
					boolean isExist = Exist(goods_id);
					if(!isExist){
					db.insert(UrlAddress.TABLE_PRODUCT, null, list.get(i));
					}
				}
				db.setTransactionSuccessful();
			} finally {
				db.endTransaction();
			}
		}
	}
}
