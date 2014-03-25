package com.soubo.app;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.soubo.adaper.GalleryAdapter;
import com.soubo.db.BuyGoodsHelper;
import com.soubo.entity.BuyGoodsInfo;
import com.soubo.entity.UrlAddress;
import com.soubo.entity.goodsDetailInfo;
import com.soubo.util.JsonUtil;
import com.soubo.util.NetWorkHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;

/*
 * ��������
 */
public class DiningDetailApp extends Activity implements OnItemClickListener {
	// ��ƷͼƬ��ʾ�Ļ���
	private Gallery gallery;
	// ��ƷͼƬ�������۸񡢵�λ��ԭ�ۡ����ơ���桢������
	private TextView goods_image_num, goods_price_text, unit_text,
			list_price_text, goods_name_text, unit, kucun_text;
	// ����ƷͼƬ�ı���
	private List<String> goods_image;
	// ȫ�ֱ���
	private VariableInfo info;
	// ��Ʒid
	private String goods_id;
	private EditText goods_num;
	// ��Ʒ�۸�
	private double price = 0;
	// ������Ʒ���ݿ������
	private BuyGoodsHelper buyGoodsHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dining_detail_layout);
		init();
	}

	// �����ʼ��
	private void init() {
		// ʵ�������ݿ������
		buyGoodsHelper = new BuyGoodsHelper(getApplicationContext());
		info = (VariableInfo) getApplication();
		goods_price_text = (TextView) findViewById(R.id.goods_price);
		unit_text = (TextView) findViewById(R.id.unit_display);
		list_price_text = (TextView) findViewById(R.id.list_price);
		goods_name_text = (TextView) findViewById(R.id.goods_name);
		unit = (TextView) findViewById(R.id.uint);
		kucun_text = (TextView) findViewById(R.id.ku_cun);
		goods_num = (EditText) findViewById(R.id.goods_num);
		// ȡ����һ��ҳ�洫������ֵ
		Intent intent = getIntent();
		goods_id = intent.getStringExtra("goods_id");
		// ��Ʒ��ϸ��Ϣ
		new goodsDetailThread().start();
		// ���������Ʒ�����Ŀؼ����õ���¼�
		goods_num.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				goods_num.setFocusable(true);
				goods_num.setFocusableInTouchMode(true);
				goods_num.requestFocus();
			}
		});
		goods_image = new ArrayList<String>();
		// ʵ�����������ؼ�
		goods_image_num = (TextView) findViewById(R.id.image_num);
		gallery = (Gallery) findViewById(R.id.goods_image_gallery);
		gallery.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		goods_image_num.setText((info.getCurrent_index() + 1) + "/"
				+ goods_image.size());
		List<Drawable> iamge_list = (List<Drawable>) arg1.getTag();
		ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
		for (Drawable drawable : iamge_list) {
			BitmapDrawable bd = (BitmapDrawable) drawable;
			bitmaps.add(bd.getBitmap());
		}
		info.setBitmap(bitmaps);
		Intent intent = new Intent(this, GoodsImageApp.class);
		intent.putExtra("position", arg2);
		startActivity(intent);
	}
	
	// ��Ʒ����ҳ����ʾ����������߳�
		private class goodsDetailThread extends Thread {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				// �ӷ�����ȡֵ
				String goods_detail_web = NetWorkHelper
						.getDataDoGet(UrlAddress.goods_detail_url + "?goods_id="
								+ goods_id);
				System.out.println("detail==>"+goods_detail_web);
				// ��ȡ��Ʒ������Ϣ
				String goods_detail = goods_detail_web.substring(
						goods_detail_web.indexOf("{"),
						goods_detail_web.indexOf("}") + 1);
				// ��ȡ��Ʒ������Ϣ
				String goods_spec = goods_detail_web.substring(
						goods_detail_web.lastIndexOf("{"),
						goods_detail_web.lastIndexOf("}"));
				System.out.println("json==>" + goods_spec);
				// ��ȡ��ƷͼƬ����������
				int max = goods_detail_web.lastIndexOf("[");
				int min = goods_detail_web.substring(0, max).lastIndexOf("[");
				String goods_photo = goods_detail_web.substring(min, max);
				goods_image = JsonUtil.jsonCartImage(goods_photo);
				// ��������
				Gson gson = new Gson();
				System.out.println("fdfs==?"+goods_detail);
				goodsDetailInfo goods_detail_info = gson.fromJson(goods_detail,
						goodsDetailInfo.class);
				if (goods_detail != null) {// ��Ʒ������Ϣ
					Message msg = mHandler.obtainMessage();
					msg.what = 1;
					msg.obj = goods_detail_info;
					mHandler.sendMessage(msg);
					if (goods_photo != null || !"".equals(goods_photo)) {//��ƷͼƬ������Ϣ
						Message msg1 = mHandler.obtainMessage();
						msg1.what = 2;
						msg1.obj = goods_image;
						mHandler.sendMessage(msg1);
					}else{
						return;
					}
				} else {// ��������������������⣩
					Message msg = mHandler.obtainMessage();
					msg.what = -1;
					mHandler.sendMessage(msg);
				}
				super.run();
			}
		}

		// ���Ʒ������ʾ�߳���ص�handler
		Handler mHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				// ��ȡ��������Ϣʧ��
				case -1:
					Toast.makeText(getApplicationContext(), "�������Ӵ����������磡",
							Toast.LENGTH_LONG).show();
					break;
				// ��ȡ��������Ϣ�ɹ�
				case 1:
					// ȡ�̴߳��ݹ�����ֵ
					goodsDetailInfo goods_detail = (goodsDetailInfo) msg.obj;
					price = goods_detail.getPrice();
					// ��ui����Ŀؼ���ֵ
					goods_price_text.setText("��" + goods_detail.getPrice() + "");
					list_price_text.setText("�˾����ѣ���" + goods_detail.getMk_price()
							+ "Ԫ");
					System.out.println("unt==>" + goods_detail.getUnit());
					if ("".equals(goods_detail.getUnit())
							|| goods_detail.getUnit() == null) {
						unit_text.setText("Ԫ/��");
						kucun_text.setText("(���9999��)");
						unit.setText("��");
					} else {
						unit_text.setText("Ԫ/" + goods_detail.getUnit() + "");
						unit.setText(goods_detail.getUnit() + "");
						kucun_text
								.setText("(���9999" + goods_detail.getUnit() + ")");
					}
					goods_name_text.setText(goods_detail.getGoods_name() + "");
					break;
				case 2://ͼƬ��Ϣ��ʾ
					List<String> image = (List<String>) msg.obj;
					gallery.setAdapter(new GalleryAdapter(getApplicationContext(),
							image));// ����ͼƬ������
					goods_image_num.setText("1/"+image.size());
					break;
				}
			};
		};

		// ��Ʒ��ϸ��Ϣ�İ�ť�ȿؼ��ĵ���¼�
		public void onAction(View v) {
			switch (v.getId()) {
			case R.id.add_goods:// �����Ʒ�����ﳵ
				System.out.println("goods==>" + info.getLogin_status());
				if (info.getLogin_status() == 0) {
					this.alterExit("������ʾ��", "�ף��㻹û�е�¼Ŷ!\t�������밴ȷ������");
					return;
				} else {
					BuyGoodsInfo buy_goods = new BuyGoodsInfo();
					buy_goods.setGoods_id(goods_id);
					buy_goods.setGoods_name(goods_name_text.getText() + "");
					buy_goods.setGoods_num(Integer.parseInt(goods_num.getText()
							+ ""));
					buy_goods.setGoods_price(price);
					buy_goods.setUnit(unit_text.getText() + "");
					buy_goods.setGoods_photo(R.drawable.ind22_04);
					boolean flag = buyGoodsHelper.addBuyGoods(buy_goods);
					if (flag == true) {
						Toast.makeText(getApplicationContext(), "��ӳɹ���",
								Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(getApplicationContext(), "���ʧ�ܣ�",
								Toast.LENGTH_LONG).show();
					}
				}
				break;
			case R.id.shou_cang:// �ղ���Ʒ
				break;
			case R.id.look_map:// �鿴������ͼ
				Intent diningMapIntent=new Intent(this, DiningMapApp.class);
				startActivity(diningMapIntent);
				break;
			case R.id.buy_consulting:// ������ѯ
				break;
			}
		}

		/**
		 * �����һ����ʾ�Ի���
		 * 
		 * @param title
		 * @param message
		 */
		public void alterExit(String title, String message) {
			// ʵ����һ��AlterDialog.Builder�Ķ���
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			// ���öԻ���ı���
			builder.setTitle(title);
			// ���öԻ������ʾ��Ϣ
			builder.setMessage(message);
			// ���öԻ���İ�ť
			builder.setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// ����
							Intent intent = new Intent(getApplicationContext(),
									LoginApp.class);
							startActivity(intent);
							return;
						}
					}).setNegativeButton(R.string.cancel,
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// ����
							return;
						}
					});
			// �����Ի���
			builder.create();
			// ��ʾ�Ի���
			builder.show();
		}
}
