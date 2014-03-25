package com.soubo.app;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soubo.adaper.BuyBodyAdapter;
import com.soubo.entity.BuyBodyInfo;
import com.soubo.entity.Memberinfo;
import com.soubo.util.NetWorkHelper;

import android.R.string;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.CursorJoiner.Result;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.renderscript.Type;
import android.util.JsonReader;
import android.widget.ListView;
import android.widget.TextView;

public class BuyBadyApp extends Activity {
	// 定义控件
	private TextView tv_or_id, tv_time, tv_goods_id, tv_name, tv_spec_id,
			tv_spice, tv_quantity, tv_goods_image,order_id,pay_time,goods_name,price;
	private ListView listview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buy_bady);
		listview = (ListView) findViewById(R.id.body_listview);
		order_id=(TextView)findViewById(R.id.buy_member);
		//
		// 设置参数
		//
     
		Thread thread = new Thread(new Runnable() {

			@SuppressLint("NewApi")
			@Override
			public void run() {
				String url = "http://192.168.1.108:7000/shops_orderWebService.asmx/getShops_order";
				// TODO Auto-generated method stub
				List<NameValuePair> list = new ArrayList<NameValuePair>();

				list.add(new BasicNameValuePair("buyer_id", "8"));
				// list.add(new BasicNameValuePair("param",param.toString() ));
				// post方法请求及其返回的数据
				String bodyinfo = NetWorkHelper.doPost(url, list);
				System.out.println("ssssssssssssssssssssssssssssssssssssssss");
				System.out.println("bodyinfo===================>>" + bodyinfo);
				int max = bodyinfo.lastIndexOf("</string>");

				int min = bodyinfo.substring(0, max).lastIndexOf(">");

				String result = bodyinfo.substring(min + 1, max);
				System.out
						.println("result==========================================??>>"
								+ result);

				ArrayList<BuyBodyInfo> reader = new ArrayList<BuyBodyInfo>();
				try {
					JSONArray jarr = new JSONArray(result);
					if (jarr.length() > 0) {
						for (int i = 0; i < jarr.length(); i++) {
							JSONObject jobj = jarr.getJSONObject(i);
							BuyBodyInfo buybodyinfo = new BuyBodyInfo();
							buybodyinfo.order_id = jobj.getInt("order_id");
							buybodyinfo.pay_time=jobj.getString("pay_time");
			
							System.out
									.println("buybodyinfo===????????????<>>>>>>>>>>"
											+ jobj.getInt("order_id"));
							reader.add(buybodyinfo);
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				BuyBodyAdapter buybodyadapter = new BuyBodyAdapter(getApplicationContext(), reader);
				listview.setAdapter(buybodyadapter);

			}

		});
		thread.start();

		//
	}
}
