package com.soubo.entity;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;

import android.R.string;

public class UrlAddress {
	public static final int BLOG_PAGE_SIZE = 16;// ��Ʒ��ҳ����
	
	//��������ַ
	private static String localhost="192.168.1.108:7000";
	//ͨ����Ʒ����id�ĵ������͵�������Ʒ
	public static String type_url="http://"+localhost+"/shops_goodsSimpleWebService.asmx/getShops_goodsSimpleList";
	public static final String TEMP_IMAGES_LOCATION = "/sdcard/cnblogs/images/";// ��ʱͼƬ�ļ�
	//ע��·��
	public static String register_uel="http://"+localhost+"/userStatesWebService.asmx/register";
	//��¼·��
	public static String login_url="http://"+localhost+"/userStatesWebService.asmx/loginValidate";
	//��Ӧ��Ʒid������·��
	public static String goods_detail_url="http://"+localhost+"/shops_goodsDetailWebService.asmx/getShops_GoodsDetail";
	
	public static final String DB_FILE_NAME = "soubo_db";// ���ݿ��ļ���
	public static final String APP_PACKAGE_NAME = "com.soubo.app";// �������
	public static final String TABLE_PRODUCT_TYPE="product_type_info";//��Ʒ���ͱ�
	public static final String TABLE_PRODUCT="product_info";//��Ʒ��
	public static final String TABLE_USER="user_info";//�û���
	public static final String TABLE_BUY_GOODS="buy_goods";//������Ʒ��
}
