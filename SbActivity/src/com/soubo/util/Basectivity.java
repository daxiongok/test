package com.soubo.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
/**
 * 大多数app的基类
 * @author Administrator
 *
 */
public class Basectivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	/*
	 * 判断网络是否已连接，设置网络状态
	 */
	public void isNetConnection(View view){
		ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
		if(networkInfo!=null && networkInfo.isConnected()){
			Toast.makeText(getApplicationContext(), "网络已连接", 1).show();
		}else{
			AlertDialog.Builder builder = new Builder(this);
			builder.setTitle("设置网络");
			builder.setMessage("网络错误，请设置网络状态");
			builder.setPositiveButton("设置网络", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent();
					intent.setAction("android.intent.action.MAIN");
					intent.setAction("android.settings.WIRELESS_SETTINGS");
					intent.setAction("android.settings.AIRPLANE_MODE_SETTINGS");
					intent.addCategory("android.intent.category.DEFAULT");
					intent.addCategory("android.intent.category.VOICE_LAUNCH");
					startActivity(intent);
				}
				});
				builder.setNegativeButton("取消", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
				builder.create().show();
		}
	}
}
