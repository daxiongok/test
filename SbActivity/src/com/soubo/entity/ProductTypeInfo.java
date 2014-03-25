package com.soubo.entity;

public class ProductTypeInfo {
	private int product_type_id;//商品类型id
	private String product_type_name;//商品类型名称
	private int type_image;//类型图片
	private int type_icon;//类型图标
	
	public int getProduct_type_id() {
		return product_type_id;
	}
	public void setProduct_type_id(int product_type_id) {
		this.product_type_id = product_type_id;
	}
	public String getProduct_type_name() {
		return product_type_name;
	}
	public int getType_image() {
		return type_image;
	}
	public void setType_image(int type_image) {
		this.type_image = type_image;
	}
	public int getType_icon() {
		return type_icon;
	}
	public void setType_icon(int type_icon) {
		this.type_icon = type_icon;
	}
	
	//无参构造方法
	public ProductTypeInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	//有参构造方法
	public ProductTypeInfo(int product_type_id, String product_type_name,
			int type_image, int type_icon) {
		super();
		this.product_type_id = product_type_id;
		this.product_type_name = product_type_name;
		this.type_image = type_image;
		this.type_icon = type_icon;
	}
	
		//重写toString()方法
		@Override
		public String toString() {
			return "ProductTypeInfo [product_type_id=" + product_type_id
					+ ", product_type_name=" + product_type_name + "]";
		}
}
