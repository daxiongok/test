package com.soubo.app;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.soubo.adaper.GalleryAdapter;
import com.soubo.db.BuyGoodsHelper;
import com.soubo.entity.BuyGoodsInfo;
import com.soubo.entity.UrlAddress;
import com.soubo.entity.goodsDetailInfo;
import com.soubo.util.JsonUtil;
import com.soubo.util.NetWorkHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;

/*
 * 餐饮详情
 */
public class DiningDetailApp extends Activity implements OnItemClickListener {
	// 商品图片显示的画廊
	private Gallery gallery;
	// 商品图片数量、价格、单位、原价、名称、库存、买数量
	private TextView goods_image_num, goods_price_text, unit_text,
			list_price_text, goods_name_text, unit, kucun_text;
	// 存商品图片的变量
	private List<String> goods_image;
	// 全局变量
	private VariableInfo info;
	// 商品id
	private String goods_id;
	private EditText goods_num;
	// 商品价格
	private double price = 0;
	// 购买商品数据库帮助类
	private BuyGoodsHelper buyGoodsHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dining_detail_layout);
		init();
	}

	// 界面初始化
	private void init() {
		// 实例化数据库帮助类
		buyGoodsHelper = new BuyGoodsHelper(getApplicationContext());
		info = (VariableInfo) getApplication();
		goods_price_text = (TextView) findViewById(R.id.goods_price);
		unit_text = (TextView) findViewById(R.id.unit_display);
		list_price_text = (TextView) findViewById(R.id.list_price);
		goods_name_text = (TextView) findViewById(R.id.goods_name);
		unit = (TextView) findViewById(R.id.uint);
		kucun_text = (TextView) findViewById(R.id.ku_cun);
		goods_num = (EditText) findViewById(R.id.goods_num);
		// 取从上一个页面传过来的值
		Intent intent = getIntent();
		goods_id = intent.getStringExtra("goods_id");
		// 商品详细信息
		new goodsDetailThread().start();
		// 给购买的商品数量的控件设置点击事件
		goods_num.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				goods_num.setFocusable(true);
				goods_num.setFocusableInTouchMode(true);
				goods_num.requestFocus();
			}
		});
		goods_image = new ArrayList<String>();
		// 实例化话各个控件
		goods_image_num = (TextView) findViewById(R.id.image_num);
		gallery = (Gallery) findViewById(R.id.goods_image_gallery);
		gallery.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		goods_image_num.setText((info.getCurrent_index() + 1) + "/"
				+ goods_image.size());
		List<Drawable> iamge_list = (List<Drawable>) arg1.getTag();
		ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
		for (Drawable drawable : iamge_list) {
			BitmapDrawable bd = (BitmapDrawable) drawable;
			bitmaps.add(bd.getBitmap());
		}
		info.setBitmap(bitmaps);
		Intent intent = new Intent(this, GoodsImageApp.class);
		intent.putExtra("position", arg2);
		startActivity(intent);
	}
	
	// 产品详情页面显示连接网络的线程
		private class goodsDetailThread extends Thread {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				// 从服务器取值
				String goods_detail_web = NetWorkHelper
						.getDataDoGet(UrlAddress.goods_detail_url + "?goods_id="
								+ goods_id);
				System.out.println("detail==>"+goods_detail_web);
				// 截取商品基本信息
				String goods_detail = goods_detail_web.substring(
						goods_detail_web.indexOf("{"),
						goods_detail_web.indexOf("}") + 1);
				// 截取商品种类信息
				String goods_spec = goods_detail_web.substring(
						goods_detail_web.lastIndexOf("{"),
						goods_detail_web.lastIndexOf("}"));
				System.out.println("json==>" + goods_spec);
				// 截取商品图片并解析数据
				int max = goods_detail_web.lastIndexOf("[");
				int min = goods_detail_web.substring(0, max).lastIndexOf("[");
				String goods_photo = goods_detail_web.substring(min, max);
				goods_image = JsonUtil.jsonCartImage(goods_photo);
				// 解析数据
				Gson gson = new Gson();
				System.out.println("fdfs==?"+goods_detail);
				goodsDetailInfo goods_detail_info = gson.fromJson(goods_detail,
						goodsDetailInfo.class);
				if (goods_detail != null) {// 商品基本信息
					Message msg = mHandler.obtainMessage();
					msg.what = 1;
					msg.obj = goods_detail_info;
					mHandler.sendMessage(msg);
					if (goods_photo != null || !"".equals(goods_photo)) {//商品图片基本信息
						Message msg1 = mHandler.obtainMessage();
						msg1.what = 2;
						msg1.obj = goods_image;
						mHandler.sendMessage(msg1);
					}else{
						return;
					}
				} else {// 其他情况（如网络有问题）
					Message msg = mHandler.obtainMessage();
					msg.what = -1;
					mHandler.sendMessage(msg);
				}
				super.run();
			}
		}

		// 与产品详情显示线程相关的handler
		Handler mHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				// 获取服务器信息失败
				case -1:
					Toast.makeText(getApplicationContext(), "网络连接错误，请检查网络！",
							Toast.LENGTH_LONG).show();
					break;
				// 获取服务器信息成功
				case 1:
					// 取线程传递过来的值
					goodsDetailInfo goods_detail = (goodsDetailInfo) msg.obj;
					price = goods_detail.getPrice();
					// 给ui界面的控件赋值
					goods_price_text.setText("￥" + goods_detail.getPrice() + "");
					list_price_text.setText("人均消费：￥" + goods_detail.getMk_price()
							+ "元");
					System.out.println("unt==>" + goods_detail.getUnit());
					if ("".equals(goods_detail.getUnit())
							|| goods_detail.getUnit() == null) {
						unit_text.setText("元/个");
						kucun_text.setText("(库存9999个)");
						unit.setText("个");
					} else {
						unit_text.setText("元/" + goods_detail.getUnit() + "");
						unit.setText(goods_detail.getUnit() + "");
						kucun_text
								.setText("(库存9999" + goods_detail.getUnit() + ")");
					}
					goods_name_text.setText(goods_detail.getGoods_name() + "");
					break;
				case 2://图片信息显示
					List<String> image = (List<String>) msg.obj;
					gallery.setAdapter(new GalleryAdapter(getApplicationContext(),
							image));// 设置图片适配器
					goods_image_num.setText("1/"+image.size());
					break;
				}
			};
		};

		// 商品详细信息的按钮等控件的点击事件
		public void onAction(View v) {
			switch (v.getId()) {
			case R.id.add_goods:// 添加商品到购物车
				System.out.println("goods==>" + info.getLogin_status());
				if (info.getLogin_status() == 0) {
					this.alterExit("友情提示：", "亲，你还没有登录哦!\t若继续请按确定键！");
					return;
				} else {
					BuyGoodsInfo buy_goods = new BuyGoodsInfo();
					buy_goods.setGoods_id(goods_id);
					buy_goods.setGoods_name(goods_name_text.getText() + "");
					buy_goods.setGoods_num(Integer.parseInt(goods_num.getText()
							+ ""));
					buy_goods.setGoods_price(price);
					buy_goods.setUnit(unit_text.getText() + "");
					buy_goods.setGoods_photo(R.drawable.ind22_04);
					boolean flag = buyGoodsHelper.addBuyGoods(buy_goods);
					if (flag == true) {
						Toast.makeText(getApplicationContext(), "添加成功！",
								Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(getApplicationContext(), "添加失败！",
								Toast.LENGTH_LONG).show();
					}
				}
				break;
			case R.id.shou_cang:// 收藏商品
				break;
			case R.id.look_map:// 查看餐饮地图
				Intent diningMapIntent=new Intent(this, DiningMapApp.class);
				startActivity(diningMapIntent);
				break;
			case R.id.buy_consulting:// 购买咨询
				break;
			}
		}

		/**
		 * 构造出一个提示对话框
		 * 
		 * @param title
		 * @param message
		 */
		public void alterExit(String title, String message) {
			// 实例化一个AlterDialog.Builder的对象
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			// 设置对话框的标题
			builder.setTitle(title);
			// 设置对话框的显示信息
			builder.setMessage(message);
			// 设置对话框的按钮
			builder.setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// 返回
							Intent intent = new Intent(getApplicationContext(),
									LoginApp.class);
							startActivity(intent);
							return;
						}
					}).setNegativeButton(R.string.cancel,
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// 返回
							return;
						}
					});
			// 创建对话框
			builder.create();
			// 显示对话框
			builder.show();
		}
}
