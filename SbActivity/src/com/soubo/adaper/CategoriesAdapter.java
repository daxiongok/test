package com.soubo.adaper;

import java.util.ArrayList;
import java.util.List;

import com.soubo.app.DetailProductApp;
import com.soubo.app.MoreSetingApp;
import com.soubo.app.ProductDescription;
import com.soubo.app.R;
import com.soubo.app.SearchApp;
import com.soubo.entity.ProductTypeInfo;

import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/*
 * 自定义商品类型（二级跳转）的adapter
 */
public class CategoriesAdapter extends BaseAdapter{
	// 加载子布局
	private LayoutInflater inflater;
	// 引用上下文
	private Context context;
	// 显示商品类型的下拉列表
	private ListView listView;

	// 封装商品类型的集合
	List<ProductTypeInfo> product_type = new ArrayList<ProductTypeInfo>();

	// 商品类型总数
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return product_type.size();
	}

	// 构造函数
	public CategoriesAdapter(Context convertView, List<ProductTypeInfo> type,
			ListView listView) {
		this.context = convertView;
		this.product_type = type;
		this.listView = listView;
		inflater = (LayoutInflater) context
				.getSystemService(context.LAYOUT_INFLATER_SERVICE);
	}
	//得到listview的子项数据
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return product_type.get(arg0);
	}
	//得到listview的子项
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	//自定义重写视图
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//得到商品类型数据
		final ProductTypeInfo type = product_type.get(position);
		if (convertView == null) {
			//实例化视图
			convertView = inflater.inflate(
					R.layout.activity_search_general_item, null);
		}
		//实例化控件
		ImageView product_image = (ImageView) convertView
				.findViewById(R.id.product_image);
		TextView product_description = (TextView) convertView
				.findViewById(R.id.product_description);
		ImageView imageView = (ImageView) convertView
				.findViewById(R.id.image_icon);
		
		//给控件赋值
		imageView.setImageResource(type.getType_image());
		product_image.setImageResource(type.getType_icon());
		product_description.setText(type.getProduct_type_name());
		product_description.setTag(type.getProduct_type_id());
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				Intent intent=new Intent(context, DetailProductApp.class);
				intent.putExtra("type_id", type.getProduct_type_id());
				context.startActivity(intent);
			}
		});
		//返回视图
		return convertView;
	}
	
	
}
