package com.soubo.entity;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;

import android.R.string;

public class UrlAddress {
	public static final int BLOG_PAGE_SIZE = 16;// 商品分页条数
	
	//服务器地址
	private static String localhost="192.168.1.108:7000";
	//通过商品类型id的到该类型的所有商品
	public static String type_url="http://"+localhost+"/shops_goodsSimpleWebService.asmx/getShops_goodsSimpleList";
	public static final String TEMP_IMAGES_LOCATION = "/sdcard/cnblogs/images/";// 临时图片文件
	//注册路径
	public static String register_uel="http://"+localhost+"/userStatesWebService.asmx/register";
	//登录路径
	public static String login_url="http://"+localhost+"/userStatesWebService.asmx/loginValidate";
	//对应商品id的详情路径
	public static String goods_detail_url="http://"+localhost+"/shops_goodsDetailWebService.asmx/getShops_GoodsDetail";
	
	public static final String DB_FILE_NAME = "soubo_db";// 数据库文件名
	public static final String APP_PACKAGE_NAME = "com.soubo.app";// 程序包名
	public static final String TABLE_PRODUCT_TYPE="product_type_info";//商品类型表
	public static final String TABLE_PRODUCT="product_info";//商品表
	public static final String TABLE_USER="user_info";//用户表
	public static final String TABLE_BUY_GOODS="buy_goods";//购买商品表
}
