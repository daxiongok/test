package com.soubo.app;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;

public class SouboIndexApp extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_index);
	}
//点击事件
public void onAction(View v){
	switch (v.getId()) {
	//生活日用
	case R.id.daily_life:
		Intent intent=new Intent(this, ProductDescription.class);
		intent.putExtra("museum", "daily_life");
		startActivity(intent);
		break;
		//数码馆
	case R.id.Museum_btn:
		Intent intent1=new Intent(this, ProductDescription.class);
		intent1.putExtra("museum", "museum");
		startActivity(intent1);
		break;
		//生活服务
	case R.id.living_services:
		Intent intent2=new Intent(this, ProductDescription.class);
		intent2.putExtra("museum", "living_services");
		startActivity(intent2);
		break;
		//在线订餐
	case R.id.online_ordering:
		Intent intent3=new Intent(this, ProductDescription.class);
		intent3.putExtra("museum", "online_ordering");
		startActivity(intent3);
		break;
		//休闲娱乐
	case R.id.leisure:
		Intent intent4=new Intent(this, ProductDescription.class);
		intent4.putExtra("museum", "leisure");
		startActivity(intent4);
		break;
		//跳蚤市场
	case R.id.flea:
		Intent intent5=new Intent(this, ProductDescription.class);
		intent5.putExtra("museum", "flea");
		startActivity(intent5);
		break;
	}
}

}
