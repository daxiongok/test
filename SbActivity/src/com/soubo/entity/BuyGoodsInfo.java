package com.soubo.entity;

import java.io.Serializable;

/**
 * 购物车购买商品的实体类
 * @author Administrator
 *
 */
public class BuyGoodsInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String goods_id;
	private String goods_name;
	private double goods_price;
	private int goods_num;
	private String unit;
	private int goods_photo;
	@Override
	public String toString() {
		return "BuyGoodsInfo [goods_id=" + goods_id + ", goods_name="
				+ goods_name + ", goods_price=" + goods_price + ", goods_num="
				+ goods_num + ", unit=" + unit + ", goods_photo=" + goods_photo
				+ "]";
	}
	public BuyGoodsInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BuyGoodsInfo(String goods_id, String goods_name, double goods_price,
			int goods_num, String unit, int goods_photo) {
		super();
		this.goods_id = goods_id;
		this.goods_name = goods_name;
		this.goods_price = goods_price;
		this.goods_num = goods_num;
		this.unit = unit;
		this.goods_photo = goods_photo;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public double getGoods_price() {
		return goods_price;
	}
	public void setGoods_price(double goods_price) {
		this.goods_price = goods_price;
	}
	public int getGoods_num() {
		return goods_num;
	}
	public void setGoods_num(int goods_num) {
		this.goods_num = goods_num;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getGoods_photo() {
		return goods_photo;
	}
	public void setGoods_photo(int goods_photo) {
		this.goods_photo = goods_photo;
	}
}
