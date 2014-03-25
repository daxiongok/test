package com.soubo.app;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.soubo.adaper.BuyGoodsAdapter;
import com.soubo.db.BuyGoodsHelper;
import com.soubo.entity.BuyGoodsInfo;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ShoppingCartApp extends Activity{
	// 定义登录和未登录的变量
	private LinearLayout unLogin, afterLogin;
	// 全局变量类
	private VariableInfo info;
	private EditText update_address_text;
	private Button update_address_btn;
	// 显示商品信息的listview
	private ListView display_buy_goods;
	private BuyGoodsHelper buyGoodsHelper;
	// listview的item的id
	private String goods_id, goods_name;
	// 存商品、
	private List<BuyGoodsInfo> goods_list;
	//显示总价格的控件,折后总价,代金券
	private TextView settlement,after_folding_price,soubo_coupon;
	//优惠券
	private ToggleButton toggle_coupon;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 加载布局
		setContentView(R.layout.activity_unlogin_shopping_cart);
		init();
	}

	// 显示商品
	public void displayGoods() {
		goods_list = new ArrayList<BuyGoodsInfo>();
		goods_list = buyGoodsHelper.queryBuyGoods();
		//所有商品的总价格
	    double total_price=0;
		BuyGoodsAdapter adapter = new BuyGoodsAdapter(getApplicationContext(),
				goods_list,display_buy_goods);
		if(goods_list!=null){
		for (BuyGoodsInfo goods : goods_list) {
			total_price+=goods.getGoods_price()*goods.getGoods_num();
		}
		}else{
			total_price=0;
		}
		if(!toggle_coupon.isChecked()){
			DecimalFormat df = new DecimalFormat("#.##");   
			//得到折后价并设置小数点位数
			double price=Double.parseDouble(df.format(total_price-30));
			//double price=Math.round((total_price-30)*100/100);
			after_folding_price.setText("折后总价为："+total_price+"-"+30+"="+price);
		}else{
			after_folding_price.setText("折后总价为："+total_price+"-"+0+"="+total_price);
		}
		display_buy_goods.setAdapter(adapter);
		settlement.setText(total_price+"");
	}

	// 界面初始化
	public void init() {
		// 实例化登录/未登录页面变量
		unLogin = (LinearLayout) findViewById(R.id.layout_login);
		afterLogin = (LinearLayout) findViewById(R.id.after_login);
		update_address_btn = (Button) findViewById(R.id.update_address);
		update_address_text = (EditText) findViewById(R.id.address);
		display_buy_goods = (ListView) findViewById(R.id.goods_display);
		buyGoodsHelper = new BuyGoodsHelper(getApplicationContext());
		settlement=(TextView) findViewById(R.id.settlement_count);
		toggle_coupon=(ToggleButton) findViewById(R.id.toggle);
		after_folding_price=(TextView) findViewById(R.id.after_folding_price);
		
		// 实例化全局变量的类
		info = (VariableInfo) getApplication();
		// 通过全局变量来控制应该显示的页面
		if (info.getLogin_status() == 1) {
			unLogin.setVisibility(View.GONE);
			afterLogin.setVisibility(View.VISIBLE);
			displayGoods();
			//注册上下文菜单
			registerForContextMenu(display_buy_goods);
		} else {
			afterLogin.setVisibility(View.GONE);
			unLogin.setVisibility(View.VISIBLE);
			//解除上下文菜单的注释
			unregisterForContextMenu(display_buy_goods);
		}
	}
	// 按钮的点击事件
	public void onAction(View v) {
		switch (v.getId()) {
		case R.id.login:// 登录
			Intent intent = new Intent(this, LoginApp.class);
			startActivity(intent);
			finish();
			break;

		case R.id.return_image:// 返回上一级
			break;
		case R.id.update_address://修改收货地址
			Intent intent2=new Intent(this, setAddressApp.class);
			startActivityForResult(intent2, REQUEST_CODE);
			break;
		}

	}

	// 重写onCreateContextMenu方法用来创建一组上下文菜单选项
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		if (v == display_buy_goods) {
			// 获取当前长按的下标
			int i= ((AdapterContextMenuInfo) menuInfo).position;
			// 向菜单中添加菜单选项
			menu.setHeaderTitle("商品管理");
			menu.add(0, 1, 1, "从购物车删除商品");
			//得到要删除的商品的相关信息
			TextView goods_name_tag = (TextView) v
					.findViewById(R.id.goods_name);
			//取值
			BuyGoodsInfo buygoods_id = (BuyGoodsInfo) goods_name_tag.getTag();
			goods_id=buygoods_id.getGoods_id();
			goods_name = buygoods_id.getGoods_name();
		}
	}
	//上下文菜单的item项选择操作
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 1) {
			Builder builder = new Builder(this);
			builder.setMessage("您确定要删除" + goods_name + "商品吗？");
			//确定删除商品
			builder.setPositiveButton("确定", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					//找到商品并删除
					boolean flag = buyGoodsHelper.delBuyGoods(goods_id);
					if (flag == true) {
						//刷新商品显示页面
						displayGoods();
						Toast.makeText(getApplicationContext(), "删除成功！",
								Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(getApplicationContext(), "删除失败！",
								Toast.LENGTH_LONG).show();
					}
				}
			});
			//取消删除商品的操作
			builder.setNegativeButton("取消", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					return;
				}
			});
			builder.create().show();
		}
		return super.onContextItemSelected(item);
	}
	//当前页面销毁时关闭数据库
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(buyGoodsHelper!=null){
			buyGoodsHelper.close();
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		displayGoods();
	}
	private static int REQUEST_CODE=1;
	// 重写这个方法用于获取返回的数据
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if(requestCode==REQUEST_CODE){
			if(resultCode==RESULT_OK){
				if(data!=null){
					String address=data.getStringExtra("address");
					update_address_text.setText(address);
				}
			}
		}
	}
}
