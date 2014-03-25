package com.soubo.entity;

public class CollectionInfo {
	private int goods_id;//商品id
	private String goods_name;//商品名称
	private String default_image;//商品图片
	private double price;//价格
	
	public CollectionInfo(){
		super();
		
	}
   public CollectionInfo(int goods_id,String goods_name,String default_image,double price){
	   super();
	   this.goods_id=goods_id;
	   this.goods_name=goods_name;
	   this.default_image=default_image;
	   this.price=price;
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
public String getDefault_image() {
	return default_image;
}
public void setDefault_image(String default_image) {
	this.default_image = default_image;
}
public double getPrice() {
	return price;
}
public void setPrice(double price) {
	this.price = price;
}
}
