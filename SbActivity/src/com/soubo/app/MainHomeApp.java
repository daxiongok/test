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

	// Tab��Ӧ�ı�ǩ��������
	private final static String HOME_PAGE = "home_page";
	private final static String SEARCH = "search";
	private final static String MEMBER_CENTER = "member_center";
	private final static String SHOPPING_CART = "shopping_cart";
	private final static String MOER = "moer";
	// ����tab��content��Ӧ��Intent
	private Intent MainHomeIntent, MySearchIntent, MemberCenterIntent,ShoppingCartIntent, MoreIntent;
	// ����RadioGroup�Ķ���
	private RadioGroup mainTab;
	private TabHost mTabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//ȥ����
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);
		initView();
	}

	// ҳ���ʼ��
	private void initView() {
		mainTab = (RadioGroup) findViewById(R.id.home_radio_button_group);
		mTabHost = this.getTabHost();
		// Intent��Ӧ��ҳ��
		MainHomeIntent = new Intent(this, SouboIndexApp.class);
		MySearchIntent = new Intent(this, SearchApp.class);
		MemberCenterIntent = new Intent(this, MenberCenterApp.class);
		ShoppingCartIntent = new Intent(this, ShoppingCartApp.class);
		MoreIntent = new Intent(this, MoreSetingApp.class);
		// ��TabHost����Activity
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
		// ��RadioGroup���õ���ļ����¼�
		mainTab.setOnCheckedChangeListener(this);

	}
	//RadioGroup����ı䷽������д
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch (checkedId) {
		// ��ҳ
		case R.id.home_page_rad:
			this.mTabHost.setCurrentTabByTag(HOME_PAGE);
			break;
		// ����
		case R.id.search_rad:
			this.mTabHost.setCurrentTabByTag(SEARCH);
			break;
		// ��Ա����
		case R.id.member_center_rad:
			this.mTabHost.setCurrentTabByTag(MEMBER_CENTER);
			break;
		// ���ﳵ
		case R.id.shopping_cart_rad:
			this.mTabHost.setCurrentTabByTag(SHOPPING_CART);
			break;
		// ����
		case R.id.more_rad:
			this.mTabHost.setCurrentTabByTag(MOER);
			break;

		}
	}

}
