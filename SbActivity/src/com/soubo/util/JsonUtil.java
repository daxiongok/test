package com.soubo.util;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.soubo.entity.goodsDetailInfo;
import com.soubo.entity.goodsInfo;

public class JsonUtil {
	public static ArrayList<goodsInfo> jsonGoods(String jsonStr) {
		Gson gson = new Gson();
		// ��json���ݽ�����ArrayList<goodsInfo>����
		// goodsInfoʵ�������jsonStr������Ҫ������json�ַ���
		Type type = new TypeToken<ArrayList<goodsInfo>>() {
		}.getType();
		// ��������
		ArrayList<goodsInfo> goods = gson.fromJson(jsonStr, type);
		return goods;
	}

	public static ArrayList<goodsDetailInfo> jsonGoodsDetail(String jsonStr) {
		Gson gson = new Gson();
		Type type = new TypeToken<ArrayList<goodsDetailInfo>>() {
		}.getType();
		ArrayList<goodsDetailInfo> goodsDetail = gson.fromJson(jsonStr, type);
		return goodsDetail;
	}

	public static List<String> jsonCartImage(String strJson) {
		List<String> image_url=new ArrayList<String>();
		try {
			// �����Ҫ����JSON���ݣ���ҪҪ����һ��JsonReader����
			JsonReader jsonReader = new JsonReader(new StringReader(strJson));
			jsonReader.beginArray();
			while (jsonReader.hasNext()) {
				jsonReader.beginObject();
				while (jsonReader.hasNext()) {
					String tagName = jsonReader.nextName();
					if (tagName.equals("image_url")) {
						image_url.add(jsonReader.nextString());
					}
				}
				jsonReader.endObject();
			}
			jsonReader.endArray();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return image_url;
	}
}
