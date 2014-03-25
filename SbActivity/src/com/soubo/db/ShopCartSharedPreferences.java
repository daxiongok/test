package com.soubo.db;

import android.content.Context;
import android.content.SharedPreferences;
/**
 * 购物车需要的商品数据存储
 * @author Administrator
 *
 */
public class ShopCartSharedPreferences {
	// 定义存取数据的接口
	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor editor;
	private Context context;

	public ShopCartSharedPreferences(Context context) {
		this.context = context;
		// 通过getSharedPreferences()方法得到SharedPreferences接口引用实例化
		sharedPreferences = context.getSharedPreferences("shop_cart",
				context.MODE_PRIVATE);
		// 调用SharedPreferences的接口方法
		editor = sharedPreferences.edit();
	}

	// 商品名称
	public void setGoodsName(String goods_name) {
		// 数据存储到SharedPreferences对象中
		if (goods_name != null) {
			editor.putString("goods_name", goods_name);
			// 提交数据
			editor.commit();
		}
	}

	// 从内部存储数据中获取值
	public String getGoodsName() {
		return sharedPreferences.getString("goods_name", "");
	}

	// 商品单价
	public void setPrice(Float price) {
		editor.putFloat("goods_price", price);
		editor.commit();
	}

	public Float getPrice() {
		return sharedPreferences.getFloat("goods_price", 0);
	}

	// 商品数量
	public void setGoodsNum(int goods_num) {
		editor.putInt("goods_num", goods_num);
		editor.commit();
	}

	public int getGoodsNum() {
		return sharedPreferences.getInt("goods_num", 0);
	}

	// 商品图片
	public void setGoodsPhoto(int goods_photo) {
		editor.putInt("goods_photo", goods_photo);
		editor.commit();
	}

	public int getGoodsPhoto() {
		return sharedPreferences.getInt("goods_photo",0);
	}

	// 清楚所有数据
	public void clearAll() {
		// 清楚数据
		editor.clear();
	}
}
