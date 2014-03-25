package com.soubo.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.soubo.entity.Memberinfo;
import com.soubo.util.NetWorkHelper;

import android.R.string;
import android.app.Activity;
import android.app.SearchManager.OnCancelListener;
import android.app.UiAutomation.OnAccessibilityEventListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MenberCenterApp extends Activity {
	private TextView textview, textview1, textview2;
	private TextView bodytextview, sellbodytextview, collectiontextview;
	private LinearLayout unLogin_member, Login_member;

	// List<String> userId = new ArrayList<String>();
	List<String> username = new ArrayList<String>();
	List<String> user_grade = new ArrayList<String>();
	List<String> grade_pionts = new ArrayList<String>();
	private int MESSAGE = 1;
	VariableInfo info = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.member_center);
		info = (VariableInfo) getApplication();
		textview = (TextView) findViewById(R.id.store_name);
		textview1 = (TextView) findViewById(R.id.store_vip);
		textview2 = (TextView) findViewById(R.id.store_point);
		bodytextview = (TextView) findViewById(R.id.buy_body);
		unLogin_member = (LinearLayout) findViewById(R.id.unlogin_member);
		Login_member = (LinearLayout) findViewById(R.id.member_layout);
		// sellbodytextview=(TextView)findViewById(R.id.sell_bodys);
		collectiontextview = (TextView) findViewById(R.id.collection_bodys);
		if (info.getLogin_status() == 1) {
			Login_member.setVisibility(View.VISIBLE);
			unLogin_member.setVisibility(View.GONE);
		} else {
			Login_member.setVisibility(View.GONE);
			unLogin_member.setVisibility(View.VISIBLE);
		}
		bodytextview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MenberCenterApp.this, BuyBadyApp.class);
				startActivity(intent);
			}
		});
		/*
		 * sellbodytextview.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub Intent intent=new Intent();
		 * intent.setClass(MenberCenterApp.this, SellBodyApp.class);
		 * startActivity(intent); } });
		 */
		collectiontextview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MenberCenterApp.this, CollectionBodyApp.class);
				startActivity(intent);
			}
		});

		// 获取用户信息线程
		Thread thread = new Thread(new Runnable() {
			String url ="http://192.168.1.108:7000/userStatesWebService.asmx/findShops_memberInfoById";

			@Override
			public void run() {
				// 设置参数
				// List<NameValuePair> list = new ArrayList<NameValuePair>();

				// list.add(new BasicNameValuePair("userId", "1"));
				// list.add(new BasicNameValuePair("param",param.toString() ));
				// post方法请求及其返回的数据
				// String member = NetWorkHelper.doPost(url, list);
				String member = NetWorkHelper.getDataDoGet(url + "?userId=1");
				System.out.println("member=====================???>>>>>>>>>>>"
						+ member);
				int max = member.lastIndexOf("</string>");

				int min = member.substring(0, max).lastIndexOf(">");

				String result = member.substring(min + 1, max);
				System.out
						.println("resultsssssssssssssssssssssssssssssssssssss==>"
								+ result);
				Gson gson = new Gson();
				Memberinfo memberinfo = gson.fromJson(result, Memberinfo.class);

				System.out.println("member==>" + memberinfo.getUsername());
				System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
				Message mess = mhandler.obtainMessage();
				mess.what = 1;
				mess.obj = memberinfo;
				mhandler.sendMessage(mess);

			}
		});
		thread.start();
	}

	Handler mhandler = new Handler() {
		public void handleMessage(Message mess) {

			switch (mess.what) {
			case 1:
				Memberinfo memberinfo = (Memberinfo) mess.obj;
				System.out.println("handler==>" + memberinfo.getUsername());
				textview.setText(memberinfo.getUsername());
				textview1.setText(memberinfo.getUser_grade() + "");
				textview2.setText(memberinfo.getGrade_points() + "");

				break;

			default:

				break;

			}

		}

	};

	public void onAction(View v) {
		switch (v.getId()) {
		case R.id.login:// 登录
			Intent intent = new Intent(this, LoginApp.class);
			startActivity(intent);
			finish();
			break;

		default:
			break;
		}
	}

}
