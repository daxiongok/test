package com.soubo.entity;

public class ProductTypeInfo {
	private int product_type_id;//��Ʒ����id
	private String product_type_name;//��Ʒ��������
	private int type_image;//����ͼƬ
	private int type_icon;//����ͼ��
	
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
	
	//�޲ι��췽��
	public ProductTypeInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	//�вι��췽��
	public ProductTypeInfo(int product_type_id, String product_type_name,
			int type_image, int type_icon) {
		super();
		this.product_type_id = product_type_id;
		this.product_type_name = product_type_name;
		this.type_image = type_image;
		this.type_icon = type_icon;
	}
	
		//��дtoString()����
		@Override
		public String toString() {
			return "ProductTypeInfo [product_type_id=" + product_type_id
					+ ", product_type_name=" + product_type_name + "]";
		}
}
