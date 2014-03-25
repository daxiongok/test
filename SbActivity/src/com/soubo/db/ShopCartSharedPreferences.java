package com.soubo.db;

import android.content.Context;
import android.content.SharedPreferences;
/**
 * ���ﳵ��Ҫ����Ʒ���ݴ洢
 * @author Administrator
 *
 */
public class ShopCartSharedPreferences {
	// �����ȡ���ݵĽӿ�
	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor editor;
	private Context context;

	public ShopCartSharedPreferences(Context context) {
		this.context = context;
		// ͨ��getSharedPreferences()�����õ�SharedPreferences�ӿ�����ʵ����
		sharedPreferences = context.getSharedPreferences("shop_cart",
				context.MODE_PRIVATE);
		// ����SharedPreferences�Ľӿڷ���
		editor = sharedPreferences.edit();
	}

	// ��Ʒ����
	public void setGoodsName(String goods_name) {
		// ���ݴ洢��SharedPreferences������
		if (goods_name != null) {
			editor.putString("goods_name", goods_name);
			// �ύ����
			editor.commit();
		}
	}

	// ���ڲ��洢�����л�ȡֵ
	public String getGoodsName() {
		return sharedPreferences.getString("goods_name", "");
	}

	// ��Ʒ����
	public void setPrice(Float price) {
		editor.putFloat("goods_price", price);
		editor.commit();
	}

	public Float getPrice() {
		return sharedPreferences.getFloat("goods_price", 0);
	}

	// ��Ʒ����
	public void setGoodsNum(int goods_num) {
		editor.putInt("goods_num", goods_num);
		editor.commit();
	}

	public int getGoodsNum() {
		return sharedPreferences.getInt("goods_num", 0);
	}

	// ��ƷͼƬ
	public void setGoodsPhoto(int goods_photo) {
		editor.putInt("goods_photo", goods_photo);
		editor.commit();
	}

	public int getGoodsPhoto() {
		return sharedPreferences.getInt("goods_photo",0);
	}

	// �����������
	public void clearAll() {
		// �������
		editor.clear();
	}
}
