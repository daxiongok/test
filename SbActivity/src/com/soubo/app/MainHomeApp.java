package com.soubo.app;

import com.soubo.adaper.GalleryAdapter;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainHomeApp extends TabActivity implements OnCheckedChangeListener {

	// Tab对应的标签常量名称
	private final static String HOME_PAGE = "home_page";
	private final static String SEARCH = "search";
	private final static String MEMBER_CENTER = "member_center";
	private final static String SHOPPING_CART = "shopping_cart";
	private final static String MOER = "moer";
	// 定义tab的content对应的Intent
	private Intent MainHomeIntent, MySearchIntent, MemberCenterIntent,ShoppingCartIntent, MoreIntent;
	// 定义RadioGroup的对象
	private RadioGroup mainTab;
	private TabHost mTabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//去标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);
		initView();
	}

	// 页面初始化
	private void initView() {
		mainTab = (RadioGroup) findViewById(R.id.home_radio_button_group);
		mTabHost = this.getTabHost();
		// Intent对应的页面
		MainHomeIntent = new Intent(this, SouboIndexApp.class);
		MySearchIntent = new Intent(this, SearchApp.class);
		MemberCenterIntent = new Intent(this, MenberCenterApp.class);
		ShoppingCartIntent = new Intent(this, ShoppingCartApp.class);
		MoreIntent = new Intent(this, MoreSetingApp.class);
		// 给TabHost加载Activity
		mTabHost.addTab(mTabHost.newTabSpec(HOME_PAGE).setIndicator(HOME_PAGE)
				.setContent(MainHomeIntent));
		mTabHost.addTab(mTabHost.newTabSpec(SEARCH).setIndicator(SEARCH)
				.setContent(MySearchIntent));
		mTabHost.addTab(mTabHost.newTabSpec(MEMBER_CENTER)
				.setIndicator(MEMBER_CENTER).setContent(MemberCenterIntent));
		mTabHost.addTab(mTabHost.newTabSpec(SHOPPING_CART)
				.setIndicator(SHOPPING_CART).setContent(ShoppingCartIntent));
		mTabHost.addTab(mTabHost.newTabSpec(MOER).setIndicator(MOER)
				.setContent(MoreIntent));
		// 给RadioGroup设置点击的监听事件
		mainTab.setOnCheckedChangeListener(this);

	}
	//RadioGroup点击改变方法的重写
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch (checkedId) {
		// 首页
		case R.id.home_page_rad:
			this.mTabHost.setCurrentTabByTag(HOME_PAGE);
			break;
		// 搜索
		case R.id.search_rad:
			this.mTabHost.setCurrentTabByTag(SEARCH);
			break;
		// 会员中心
		case R.id.member_center_rad:
			this.mTabHost.setCurrentTabByTag(MEMBER_CENTER);
			break;
		// 购物车
		case R.id.shopping_cart_rad:
			this.mTabHost.setCurrentTabByTag(SHOPPING_CART);
			break;
		// 更多
		case R.id.more_rad:
			this.mTabHost.setCurrentTabByTag(MOER);
			break;

		}
	}

}
