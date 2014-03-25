package com.soubo.entity;

public class goodsDetailInfo {
	private String goods_id;
	private String goods_name;
	private double price;	//实际价格
	private double mk_price;//显示价格
	private	String unit;	//物品计量单位
	private int subtotalsale;//成功出售数量
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
	public goodsDetailInfo(String goods_id, String goods_name, double price,
			double mk_price, String unit, int subtotalsale, String spec_name_1,
			String spec_name_2) {
		super();
		this.goods_id = goods_id;
		this.goods_name = goods_name;
		this.price = price;
		this.mk_price = mk_price;
		this.unit = unit;
		this.subtotalsale = subtotalsale;
		this.spec_name_1 = spec_name_1;
		this.spec_name_2 = spec_name_2;
	}
	public goodsDetailInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getMk_price() {
		return mk_price;
	}
	public void setMk_price(double mk_price) {
		this.mk_price = mk_price;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getSubtotalsale() {
		return subtotalsale;
	}
	public void setSubtotalsale(int subtotalsale) {
		this.subtotalsale = subtotalsale;
	}
	public String getSpec_name_1() {
		return spec_name_1;
	}
	public void setSpec_name_1(String spec_name_1) {
		this.spec_name_1 = spec_name_1;
	}
	public String getSpec_name_2() {
		return spec_name_2;
	}
	public void setSpec_name_2(String spec_name_2) {
		this.spec_name_2 = spec_name_2;
	}
	private String spec_name_1;//商品规格1
	private String spec_name_2;//商品规格2
	@Override
	public String toString() {
		return "goodsDetailInfo [goods_id=" + goods_id + ", goods_name="
				+ goods_name + ", price=" + price + ", mk_price=" + mk_price
				+ ", unit=" + unit + ", subtotalsale=" + subtotalsale
				+ ", spec_name_1=" + spec_name_1 + ", spec_name_2="
				+ spec_name_2 + "]";
	}
}
