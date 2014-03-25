package com.soubo.adaper;


import java.util.List;

import com.soubo.app.R;
import com.soubo.app.VariableInfo;
import com.soubo.entity.BuyGoodsInfo;
import com.soubo.entity.goodsDetailInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 购物车的商品信息adapter
 * @author Administrator
 *
 */
public class BuyGoodsAdapter extends BaseAdapter {
	public BuyGoodsAdapter(Context context,List<BuyGoodsInfo> goods_list,ListView listView){
		this.context=context;
		this.goods_list=goods_list;
		this.listView=listView;
	}
	private Context context;
	private List<BuyGoodsInfo> goods_list;
	private ListView listView;
	//ShopCartSharedPreferences sharedPreferences=new ShopCartSharedPreferences(context);
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return goods_list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return goods_list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if(view==null){
			view=LayoutInflater.from(context).inflate(R.layout.my_shoping_cart_doods_display, null);
		}
		TextView goods_name=(TextView) view.findViewById(R.id.goods_name);
		TextView goods_price=(TextView) view.findViewById(R.id.goods_price);
		TextView goods_num=(TextView) view.findViewById(R.id.goods_num);
		TextView subtotal=(TextView) view.findViewById(R.id.subtotal);
		ImageView goods_iamge=(ImageView) view.findViewById(R.id.goods_image);
		
		goods_name.setText(goods_list.get(arg0).getGoods_name());
		goods_num.setText(goods_list.get(arg0).getGoods_num()+goods_list.get(arg0).getUnit()+"");
		goods_price.setText(goods_list.get(arg0).getGoods_price()+"元");
		subtotal.setText(goods_list.get(arg0).getGoods_num()*goods_list.get(arg0).getGoods_price()+"元");
		goods_iamge.setImageResource(goods_list.get(arg0).getGoods_photo());
		goods_name.setTag(goods_list.get(arg0));
		return view;
	}

	

}
