package com.soubo.app;

import java.util.Arrays;
import java.util.List;


import com.soubo.adaper.CategoriesAdapter;
import com.soubo.entity.ProductTypeInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

/*
 * 产品类型二级分类
 */
public class ProductDescription extends Activity{
	// 显示商品类型的下拉列表框
	private ListView product_description_display;
	// 封装对应所有的商品类型到集合中
	private List<ProductTypeInfo> type;
	// 设置数码馆商品类型数据
	ProductTypeInfo[] museum_type = {
			new ProductTypeInfo(1283, "手机通讯", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(1284, "手机配件", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(1285, "摄影摄像", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(1286, "数码配件", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(1287, "时尚影音", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(1951, "电脑整机", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(1952, "电脑配件", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(1953, "办公打印", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(1954, "组装机", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(1955, "网络产品", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(1956, "外设产品", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(3114, "平板配件", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(1327, "大家电", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(1328, "生活电器", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(1329, "厨房电器", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(1330, "个人护理", R.drawable.my_search1,
					R.drawable.my_search1),

	};

	// 生活日用
	ProductTypeInfo[] daily_life_type = {
			new ProductTypeInfo(1320, "食品超市", R.drawable.my_search2,
					R.drawable.my_search1),
			new ProductTypeInfo(1319, "母婴玩具乐器店", R.drawable.my_search2,
					R.drawable.my_search1),
			new ProductTypeInfo(1323, "家居家纺/园艺馆", R.drawable.my_search2,
					R.drawable.my_search1),
			new ProductTypeInfo(1324, "钟表、珠宝店", R.drawable.my_search2,
					R.drawable.my_search1),
			new ProductTypeInfo(1325, "户外运动、文体用品", R.drawable.my_search2,
					R.drawable.my_search1),
			new ProductTypeInfo(1326, "汽车饰品城", R.drawable.my_search2,
					R.drawable.my_search1),
			new ProductTypeInfo(1651, "家装五金", R.drawable.my_search2,
					R.drawable.my_search1),
			new ProductTypeInfo(1654, "灯具", R.drawable.my_search2,
					R.drawable.my_search1),
			new ProductTypeInfo(1658, "家装建材", R.drawable.my_search2,
					R.drawable.my_search1), };
	// 生活服务
	ProductTypeInfo[] living_services_type = {
			new ProductTypeInfo(2725, "婚庆摄影", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(2829, "汽车服务", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(2830, "家政服务", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(2831, "装饰服务", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(2832, "商务服务", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(2833, "医疗保健", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(2834, "休闲娱乐", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(2835, "教育培训", R.drawable.my_search1,
					R.drawable.my_search1), };

	// 在线订餐
	ProductTypeInfo[] online_ordering_type = {
			new ProductTypeInfo(2718, "散餐预定", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(2719, "宴席预定", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(2720, "美食外卖", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(2721, "找优惠券", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(2722, "找代金券", R.drawable.my_search1,
					R.drawable.my_search1), };

	// 休闲娱乐
	ProductTypeInfo[] leisure_type = {
			new ProductTypeInfo(1322, "美容美发馆", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(1757, "纤体瑜伽", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(1758, "体育娱乐", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(1321, "音像图书、宠物馆", R.drawable.my_search1,
					R.drawable.my_search1), };

	// 跳蚤市场
	ProductTypeInfo[] flea_type = {
			new ProductTypeInfo(2770, "售二手车", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(2771, "售二手店", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(2772, "房屋出租", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(2773, "新楼盘", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(2774, "车辆出租", R.drawable.my_search1,
					R.drawable.my_search1)
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//去标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.search_general_main);
		init();
		System.out.println("sdfsdfa");

	}

	// 初始化界面
	public void init() {
		Intent intent = getIntent();
		String typeName = intent.getStringExtra("museum");
		if (typeName.equals("museum")) {// 数码馆
			// 将数组转换为集合
			type = Arrays.asList(museum_type);
			product_description_display = (ListView) findViewById(R.id.product_description_display);
			CategoriesAdapter adapter = new CategoriesAdapter(this, type,
					product_description_display);
			// 给listView绑定数据
			product_description_display.setAdapter(adapter);
		} else if (typeName.equals("daily_life")) {// 生活日用
			// 将数组转换为集合
			type = Arrays.asList(daily_life_type);
			product_description_display = (ListView) findViewById(R.id.product_description_display);
			CategoriesAdapter adapter = new CategoriesAdapter(this, type,
					product_description_display);
			// 给listView绑定数据
			product_description_display.setAdapter(adapter);
		} else if (typeName.equals("living_services")) {// 生活服务
			// 将数组转换为集合
			type = Arrays.asList(living_services_type);
			product_description_display = (ListView) findViewById(R.id.product_description_display);
			CategoriesAdapter adapter = new CategoriesAdapter(this, type,
					product_description_display);
			// 给listView绑定数据
			product_description_display.setAdapter(adapter);
		} else if (typeName.equals("online_ordering")) {// 在线订餐
			// 将数组转换为集合
			type = Arrays.asList(online_ordering_type);
			product_description_display = (ListView) findViewById(R.id.product_description_display);
			CategoriesAdapter adapter = new CategoriesAdapter(this, type,
					product_description_display);
			// 给listView绑定数据
			product_description_display.setAdapter(adapter);
		} else if (typeName.equals("leisure")) {// 休闲娱乐
			// 将数组转换为集合
			type = Arrays.asList(leisure_type);
			product_description_display = (ListView) findViewById(R.id.product_description_display);
			CategoriesAdapter adapter = new CategoriesAdapter(this, type,
					product_description_display);
			// 给listView绑定数据
			product_description_display.setAdapter(adapter);
		} else if (typeName.equals("flea")) {// 跳蚤市场
			// 将数组转换为集合
			type = Arrays.asList(flea_type);
			product_description_display = (ListView) findViewById(R.id.product_description_display);
			CategoriesAdapter adapter = new CategoriesAdapter(this, type,
					product_description_display);
			// 给listView绑定数据
			product_description_display.setAdapter(adapter);
		}
		// 设置ListView的item点击事件
		product_description_display
				.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						TextView type_name_text = (TextView) arg1
								.findViewById(R.id.product_description);
						//得到商品类型的id
						String type_name = type_name_text.getTag().toString();
						//页面跳转并传值
						Intent intent=new Intent();
						intent.putExtra("type_id", type_name);
						intent.setClass(ProductDescription.this, DetailProductApp.class);
						startActivity(intent);
					}
				});
	}
}
