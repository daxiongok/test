package com.soubo.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.soubo.adaper.BuyBodyAdapter;
import com.soubo.adaper.CollectionAdapter;
import com.soubo.entity.BuyBodyInfo;
import com.soubo.entity.CollectionInfo;
import com.soubo.entity.Memberinfo;
import com.soubo.util.NetWorkHelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CollectionBodyApp extends Activity{
	private ListView listview;
	private CheckBox checkbox_delete;
	private  TextView delete;
    private int checkbocnum;//记录选中的条目数量
/*    private ArrayList<HashMap<String, String>> lists;
*/    private String resultTemp2;
	
   @Override
public  void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_collection);
	listview=(ListView)findViewById(R.id.collection_listview);
	checkbox_delete=(CheckBox)findViewById(R.id.checkbox_delete);
	delete=(TextView)findViewById(R.id.delete);
   //获取数据，且解析数据
	Thread thread=new Thread(new Runnable() {
		
		@Override
		public void run() {
			String url = "http://192.168.1.108:7000/shops_collectWebService.asmx/HelloWorld";
			// TODO Auto-generated method stub
			List<NameValuePair> list = new ArrayList<NameValuePair>();

			list.add(new BasicNameValuePair("user_Id", "7"));
			// list.add(new BasicNameValuePair("param",param.toString() ));
			// post方法请求及其返回的数据
			String collection = NetWorkHelper.doPost(url, list);
			System.out.println("ssssssssssssssssssssssssssssssssssssssss");
			System.out.println("bodyinfo===================>>" + collection);
			//截取字符串
			int max = collection.lastIndexOf("</string>");

			int min = collection.substring(0, max).lastIndexOf(">");

			String result = collection.substring(min + 1, max);
			System.out
					.println("result==========================================??>>"
							+ result);
			  String resultTemp = result;
			 
			 String resultTemp1 = resultTemp.substring(
			 resultTemp.indexOf("[") + 1, resultTemp.indexOf("]"));
			 
			 resultTemp = resultTemp.substring(resultTemp.indexOf("]") +
			  1);
			  
			  resultTemp2 = resultTemp.substring(
			  resultTemp.indexOf("[") + 1, resultTemp.indexOf("]"));
			  
			  System.out.println("json1=========================>>" +
			  resultTemp1);
			  System.out.println("json2=========================>>" +
			  resultTemp2);
			  System.out.println("==============??????????===================");

			Gson gson = new Gson();
			CollectionInfo collectioninfo = gson.fromJson(resultTemp2, CollectionInfo.class);
			System.out.println("collectioninfo===============================>>"+collectioninfo.getGoods_name());
			System.out.println("collectioninfo===============================>>"+collectioninfo.getPrice());
			CollectionAdapter collectionAdapter = new CollectionAdapter(getApplicationContext(), collectioninfo);
			listview.setAdapter(collectionAdapter);

		}
	}); 
     thread.start();
	
    
	
	
  }
   

}
