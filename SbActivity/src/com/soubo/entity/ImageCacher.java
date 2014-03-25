package com.soubo.entity;


import android.content.Context;

public class ImageCacher {
	// ����Context����
	private Context context;

	// �޲εĹ��췽��
	public ImageCacher() {

	}

	// �вεĹ��췽��
	public ImageCacher(Context context) {
		this.context = context;
	}
	
	//ͼƬ����
	public enum EnumImageType{
		goods_photo,
		member_photo,
		goods_id
	}
	
	/**
	 * �õ�ͼƬ��ַ�ļ���
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
