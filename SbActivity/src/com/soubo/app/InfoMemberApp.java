package com.soubo.app;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.soubo.entity.BuyBodyInfo;
import com.soubo.entity.InfoMemberInfo;
import com.soubo.entity.Memberinfo;
import com.soubo.util.NetWorkHelper;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.webkit.WebView;
import android.widget.TextView;

public class InfoMemberApp extends Activity{
	 private TextView tv_title;
	 private WebView tv_context;
  @Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub

	 super.onCreate(savedInstanceState);
	 setContentView(R.layout.activity_infos_member);
	 tv_title=(TextView)findViewById(R.id.title);
	 tv_context=(WebView)findViewById(R.id.context);
	 //将文字类型转换为UTF-8
	 tv_context.getSettings().setDefaultTextEncodingName("utf-8");

	 Thread thread=new Thread(new Runnable(){
		@Override
		public void run() {
			// TODO Auto-generated method stub
		   String url="http://192.168.1.108:7000/Shops_message.asmx/Getmessage";
			String member = NetWorkHelper.getDataDoGet(url + "?msId=3");
			System.out.println("member=====================???>>>>>>>>>>>"
					+ member);
			int max = member.lastIndexOf("</string>");

			int min = member.substring(0, max).lastIndexOf(">");

			String result = member.substring(min + 1, max);
			System.out
					.println("resultsssssssssssssssssssssssssssssssssssss==>"
							+ result);
			Gson gson = new Gson();
			InfoMemberInfo infomemberinfo = gson.fromJson(result, InfoMemberInfo.class);
			System.out.println("member==>" + infomemberinfo.getContext());
			System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			Message mess = mhandler.obtainMessage();
			mess.what = 1;
			mess.obj = infomemberinfo;
			mhandler.sendMessage(mess);
		
		
		}
	});
	 thread.start();
   }
  Handler mhandler = new Handler() {
		public void handleMessage(Message mess) {

			switch (mess.what) {
			case 1:
				InfoMemberInfo infomemberinfo = (InfoMemberInfo) mess.obj;
				System.out.println("handler==>" + infomemberinfo.getContext());
			/*	textview.setText(memberinfo.getUsername());
				textview1.setText(memberinfo.getUser_grade() + "");
				textview2.setText(memberinfo.getGrade_points() + "");
*/                
				tv_title.setText(infomemberinfo.getTitle());
				tv_context.loadData(infomemberinfo.getContext().toString(), "text/html", "utf-8");
				
				break;

			default:

				break;

			}

		}

	};
  
}
