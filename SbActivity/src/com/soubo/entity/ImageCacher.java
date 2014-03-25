package com.soubo.entity;


import android.content.Context;

public class ImageCacher {
	// 定义Context对象
	private Context context;

	// 无参的构造方法
	public ImageCacher() {

	}

	// 有参的构造方法
	public ImageCacher(Context context) {
		this.context = context;
	}
	
	//图片类型
	public enum EnumImageType{
		goods_photo,
		member_photo,
		goods_id
	}
	
	/**
	 * 得到图片地址文件夹
	 * 
	 * @param imageType
	 * @return
	 */
	public static String GetImageFolder(EnumImageType imageType) {
		String folder = UrlAddress.TEMP_IMAGES_LOCATION;
		switch (imageType) {
		default:
		case goods_photo:
			folder += "goods_photo/";
			break;
		case member_photo:
			folder += "member_photo/";
			break;
		}
		return folder;
	}
}
