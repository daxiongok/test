package com.soubo.db;

import java.util.ArrayList;
import java.util.List;

import com.soubo.entity.BuyGoodsInfo;
import com.soubo.entity.UrlAddress;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BuyGoodsHelper {
	private Context context;
	private DBHelper.DatabaseHelper dbHelper;
	private SQLiteDatabase db;
	public final static byte[] _writeLock = new byte[0];

	public BuyGoodsHelper(Context context) {
		dbHelper = new DBHelper.DatabaseHelper(context);
		db = dbHelper.getWritableDatabase();
		this.context = context;
	}
	//关闭数据库
	public void close(){
		if(db!=null){
			db.close();
		}
	}

	/**
	 * 查询已添加到购物车的信息
	 * 
	 * @return
	 */
	public List<BuyGoodsInfo> queryBuyGoods() {
		if(!db.isOpen()){
			db.isOpen();
		}
		ArrayList<BuyGoodsInfo> buy_goods = new ArrayList<BuyGoodsInfo>();
		Cursor cursor = db.query(UrlAddress.TABLE_BUY_GOODS, null, null, null,
				null, null, null);
		while (cursor.moveToNext()) {
			BuyGoodsInfo goods = new BuyGoodsInfo();
			goods.setGoods_id(cursor.getString(cursor
					.getColumnIndex("goods_id")));
			goods.setGoods_name(cursor.getString(cursor
					.getColumnIndex("goods_name")));
			goods.setGoods_num(cursor.getInt(cursor.getColumnIndex("goods_num")));
			goods.setGoods_price(cursor.getDouble(cursor
					.getColumnIndex("goods_price")));
			goods.setGoods_photo(cursor.getInt(cursor
					.getColumnIndex("goods_photo")));
			goods.setUnit(cursor.getString(cursor.getColumnIndex("unit")));
			buy_goods.add(goods);
		}
		// 关闭游标
		cursor.close();
		return buy_goods;
	}

	/*
	 * 添加商品到购物车
	 */
	public boolean addBuyGoods(BuyGoodsInfo info) {
		// 设置添加的值
		ContentValues contentValues = new ContentValues();
		contentValues.put("goods_id", info.getGoods_id());
		contentValues.put("goods_name", info.getGoods_name());
		contentValues.put("goods_num", info.getGoods_num());
		contentValues.put("goods_price", info.getGoods_price());
		contentValues.put("goods_photo", info.getGoods_photo());
		contentValues.put("unit", info.getUnit());
		// 向表中插入数据
		return db.insert(UrlAddress.TABLE_BUY_GOODS, null, contentValues) > 0;
	}
	/**
	 * 从购物车中删除商品
	 */
	
	public boolean delBuyGoods(String goods_id){
		//设置参数值
		return db.delete(UrlAddress.TABLE_BUY_GOODS, "goods_id=?", new String[]{goods_id})>0;
	}
}
