package com.soubo.entity;

public class BuyBodyInfo {
	public int order_id;
	public String pay_time;
	private int goods_id;
	public String goods_name;

	

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public String getPay_time() {
		return pay_time;
	}

	public void setPay_time(String pay_time) {
		this.pay_time = pay_time;
	}

	public int getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(int goods_id) {
		this.goods_id = goods_id;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public int getSpec_id() {
		return spec_id;
	}

	public void setSpec_id(int spec_id) {
		this.spec_id = spec_id;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getGoods_image() {
		return goods_image;
	}

	public void setGoods_image(String goods_image) {
		this.goods_image = goods_image;
	}

	private int spec_id;
	public int price;
	private int quantity;
	private String goods_image;


	public BuyBodyInfo() {
		super();
	}

	public BuyBodyInfo(int orider_id, String pay_time, int goods_id,
			String goods_name, int spce_id, int quantity, String goods_image) {
		super();
		this.order_id = order_id;
		this.pay_time = pay_time;
		this.goods_id = goods_id;
		this.goods_name = goods_name;
		this.spec_id = spce_id;
		this.quantity = quantity;
		this.goods_image = goods_image;

	}
}
