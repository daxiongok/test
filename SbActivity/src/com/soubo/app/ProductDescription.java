package com.soubo.app;

import java.util.Arrays;
import java.util.List;


import com.soubo.adaper.CategoriesAdapter;
import com.soubo.entity.ProductTypeInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

/*
 * ��Ʒ���Ͷ�������
 */
public class ProductDescription extends Activity{
	// ��ʾ��Ʒ���͵������б��
	private ListView product_description_display;
	// ��װ��Ӧ���е���Ʒ���͵�������
	private List<ProductTypeInfo> type;
	// �����������Ʒ��������
	ProductTypeInfo[] museum_type = {
			new ProductTypeInfo(1283, "�ֻ�ͨѶ", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(1284, "�ֻ����", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(1285, "��Ӱ����", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(1286, "�������", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(1287, "ʱ��Ӱ��", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(1951, "��������", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(1952, "�������", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(1953, "�칫��ӡ", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(1954, "��װ��", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(1955, "�����Ʒ", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(1956, "�����Ʒ", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(3114, "ƽ�����", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(1327, "��ҵ�", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(1328, "�������", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(1329, "��������", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(1330, "���˻���", R.drawable.my_search1,
					R.drawable.my_search1),

	};

	// ��������
	ProductTypeInfo[] daily_life_type = {
			new ProductTypeInfo(1320, "ʳƷ����", R.drawable.my_search2,
					R.drawable.my_search1),
			new ProductTypeInfo(1319, "ĸӤ���������", R.drawable.my_search2,
					R.drawable.my_search1),
			new ProductTypeInfo(1323, "�ҾӼҷ�/԰�չ�", R.drawable.my_search2,
					R.drawable.my_search1),
			new ProductTypeInfo(1324, "�ӱ��鱦��", R.drawable.my_search2,
					R.drawable.my_search1),
			new ProductTypeInfo(1325, "�����˶���������Ʒ", R.drawable.my_search2,
					R.drawable.my_search1),
			new ProductTypeInfo(1326, "������Ʒ��", R.drawable.my_search2,
					R.drawable.my_search1),
			new ProductTypeInfo(1651, "��װ���", R.drawable.my_search2,
					R.drawable.my_search1),
			new ProductTypeInfo(1654, "�ƾ�", R.drawable.my_search2,
					R.drawable.my_search1),
			new ProductTypeInfo(1658, "��װ����", R.drawable.my_search2,
					R.drawable.my_search1), };
	// �������
	ProductTypeInfo[] living_services_type = {
			new ProductTypeInfo(2725, "������Ӱ", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(2829, "��������", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(2830, "��������", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(2831, "װ�η���", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(2832, "�������", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(2833, "ҽ�Ʊ���", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(2834, "��������", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(2835, "������ѵ", R.drawable.my_search1,
					R.drawable.my_search1), };

	// ���߶���
	ProductTypeInfo[] online_ordering_type = {
			new ProductTypeInfo(2718, "ɢ��Ԥ��", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(2719, "��ϯԤ��", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(2720, "��ʳ����", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(2721, "���Ż�ȯ", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(2722, "�Ҵ���ȯ", R.drawable.my_search1,
					R.drawable.my_search1), };

	// ��������
	ProductTypeInfo[] leisure_type = {
			new ProductTypeInfo(1322, "����������", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(1757, "�����٤", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(1758, "��������", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(1321, "����ͼ�顢�����", R.drawable.my_search1,
					R.drawable.my_search1), };

	// �����г�
	ProductTypeInfo[] flea_type = {
			new ProductTypeInfo(2770, "�۶��ֳ�", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(2771, "�۶��ֵ�", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(2772, "���ݳ���", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(2773, "��¥��", R.drawable.my_search1,
					R.drawable.my_search1),
			new ProductTypeInfo(2774, "��������", R.drawable.my_search1,
					R.drawable.my_search1)
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//ȥ����
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.search_general_main);
		init();
		System.out.println("sdfsdfa");

	}

	// ��ʼ������
	public void init() {
		Intent intent = getIntent();
		String typeName = intent.getStringExtra("museum");
		if (typeName.equals("museum")) {// �����
			// ������ת��Ϊ����
			type = Arrays.asList(museum_type);
			product_description_display = (ListView) findViewById(R.id.product_description_display);
			CategoriesAdapter adapter = new CategoriesAdapter(this, type,
					product_description_display);
			// ��listView������
			product_description_display.setAdapter(adapter);
		} else if (typeName.equals("daily_life")) {// ��������
			// ������ת��Ϊ����
			type = Arrays.asList(daily_life_type);
			product_description_display = (ListView) findViewById(R.id.product_description_display);
			CategoriesAdapter adapter = new CategoriesAdapter(this, type,
					product_description_display);
			// ��listView������
			product_description_display.setAdapter(adapter);
		} else if (typeName.equals("living_services")) {// �������
			// ������ת��Ϊ����
			type = Arrays.asList(living_services_type);
			product_description_display = (ListView) findViewById(R.id.product_description_display);
			CategoriesAdapter adapter = new CategoriesAdapter(this, type,
					product_description_display);
			// ��listView������
			product_description_display.setAdapter(adapter);
		} else if (typeName.equals("online_ordering")) {// ���߶���
			// ������ת��Ϊ����
			type = Arrays.asList(online_ordering_type);
			product_description_display = (ListView) findViewById(R.id.product_description_display);
			CategoriesAdapter adapter = new CategoriesAdapter(this, type,
					product_description_display);
			// ��listView������
			product_description_display.setAdapter(adapter);
		} else if (typeName.equals("leisure")) {// ��������
			// ������ת��Ϊ����
			type = Arrays.asList(leisure_type);
			product_description_display = (ListView) findViewById(R.id.product_description_display);
			CategoriesAdapter adapter = new CategoriesAdapter(this, type,
					product_description_display);
			// ��listView������
			product_description_display.setAdapter(adapter);
		} else if (typeName.equals("flea")) {// �����г�
			// ������ת��Ϊ����
			type = Arrays.asList(flea_type);
			product_description_display = (ListView) findViewById(R.id.product_description_display);
			CategoriesAdapter adapter = new CategoriesAdapter(this, type,
					product_description_display);
			// ��listView������
			product_description_display.setAdapter(adapter);
		}
		// ����ListView��item����¼�
		product_description_display
				.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						TextView type_name_text = (TextView) arg1
								.findViewById(R.id.product_description);
						//�õ���Ʒ���͵�id
						String type_name = type_name_text.getTag().toString();
						//ҳ����ת����ֵ
						Intent intent=new Intent();
						intent.putExtra("type_id", type_name);
						intent.setClass(ProductDescription.this, DetailProductApp.class);
						startActivity(intent);
					}
				});
	}
}
