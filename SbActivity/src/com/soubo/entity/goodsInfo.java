package com.soubo.entity;

public class goodsInfo {
	private String goods_id;
	private String goods_name;
	private String default_image;
	private String price;
	@Override
	public String toString() {
		return "goodsInfo [goods_id=" + goods_id + ", goods_name=" + goods_name
				+ ", default_image=" + default_image + ", price=" + price + "]";
	}
	public goodsInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public goodsInfo(String goods_id, String goods_name, String default_image,
			String price) {
		super();
		this.goods_id = goods_id;
		this.goods_name = goods_name;
		this.default_image = default_image;
		this.price = price;
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
	public String getDefault_image() {
		return default_image;
	}
	public void setDefault_image(String default_image) {
		this.default_image = default_image;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
}
