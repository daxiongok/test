package com.soubo.app;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.soubo.adaper.BuyGoodsAdapter;
import com.soubo.db.BuyGoodsHelper;
import com.soubo.entity.BuyGoodsInfo;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ShoppingCartApp extends Activity{
	// �����¼��δ��¼�ı���
	private LinearLayout unLogin, afterLogin;
	// ȫ�ֱ�����
	private VariableInfo info;
	private EditText update_address_text;
	private Button update_address_btn;
	// ��ʾ��Ʒ��Ϣ��listview
	private ListView display_buy_goods;
	private BuyGoodsHelper buyGoodsHelper;
	// listview��item��id
	private String goods_id, goods_name;
	// ����Ʒ��
	private List<BuyGoodsInfo> goods_list;
	//��ʾ�ܼ۸�Ŀؼ�,�ۺ��ܼ�,����ȯ
	private TextView settlement,after_folding_price,soubo_coupon;
	//�Ż�ȯ
	private ToggleButton toggle_coupon;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ȥ����
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ���ز���
		setContentView(R.layout.activity_unlogin_shopping_cart);
		init();
	}

	// ��ʾ��Ʒ
	public void displayGoods() {
		goods_list = new ArrayList<BuyGoodsInfo>();
		goods_list = buyGoodsHelper.queryBuyGoods();
		//������Ʒ���ܼ۸�
	    double total_price=0;
		BuyGoodsAdapter adapter = new BuyGoodsAdapter(getApplicationContext(),
				goods_list,display_buy_goods);
		if(goods_list!=null){
		for (BuyGoodsInfo goods : goods_list) {
			total_price+=goods.getGoods_price()*goods.getGoods_num();
		}
		}else{
			total_price=0;
		}
		if(!toggle_coupon.isChecked()){
			DecimalFormat df = new DecimalFormat("#.##");   
			//�õ��ۺ�۲�����С����λ��
			double price=Double.parseDouble(df.format(total_price-30));
			//double price=Math.round((total_price-30)*100/100);
			after_folding_price.setText("�ۺ��ܼ�Ϊ��"+total_price+"-"+30+"="+price);
		}else{
			after_folding_price.setText("�ۺ��ܼ�Ϊ��"+total_price+"-"+0+"="+total_price);
		}
		display_buy_goods.setAdapter(adapter);
		settlement.setText(total_price+"");
	}

	// �����ʼ��
	public void init() {
		// ʵ������¼/δ��¼ҳ�����
		unLogin = (LinearLayout) findViewById(R.id.layout_login);
		afterLogin = (LinearLayout) findViewById(R.id.after_login);
		update_address_btn = (Button) findViewById(R.id.update_address);
		update_address_text = (EditText) findViewById(R.id.address);
		display_buy_goods = (ListView) findViewById(R.id.goods_display);
		buyGoodsHelper = new BuyGoodsHelper(getApplicationContext());
		settlement=(TextView) findViewById(R.id.settlement_count);
		toggle_coupon=(ToggleButton) findViewById(R.id.toggle);
		after_folding_price=(TextView) findViewById(R.id.after_folding_price);
		
		// ʵ����ȫ�ֱ�������
		info = (VariableInfo) getApplication();
		// ͨ��ȫ�ֱ���������Ӧ����ʾ��ҳ��
		if (info.getLogin_status() == 1) {
			unLogin.setVisibility(View.GONE);
			afterLogin.setVisibility(View.VISIBLE);
			displayGoods();
			//ע�������Ĳ˵�
			registerForContextMenu(display_buy_goods);
		} else {
			afterLogin.setVisibility(View.GONE);
			unLogin.setVisibility(View.VISIBLE);
			//��������Ĳ˵���ע��
			unregisterForContextMenu(display_buy_goods);
		}
	}
	// ��ť�ĵ���¼�
	public void onAction(View v) {
		switch (v.getId()) {
		case R.id.login:// ��¼
			Intent intent = new Intent(this, LoginApp.class);
			startActivity(intent);
			finish();
			break;

		case R.id.return_image:// ������һ��
			break;
		case R.id.update_address://�޸��ջ���ַ
			Intent intent2=new Intent(this, setAddressApp.class);
			startActivityForResult(intent2, REQUEST_CODE);
			break;
		}

	}

	// ��дonCreateContextMenu������������һ�������Ĳ˵�ѡ��
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		if (v == display_buy_goods) {
			// ��ȡ��ǰ�������±�
			int i= ((AdapterContextMenuInfo) menuInfo).position;
			// ��˵�����Ӳ˵�ѡ��
			menu.setHeaderTitle("��Ʒ����");
			menu.add(0, 1, 1, "�ӹ��ﳵɾ����Ʒ");
			//�õ�Ҫɾ������Ʒ�������Ϣ
			TextView goods_name_tag = (TextView) v
					.findViewById(R.id.goods_name);
			//ȡֵ
			BuyGoodsInfo buygoods_id = (BuyGoodsInfo) goods_name_tag.getTag();
			goods_id=buygoods_id.getGoods_id();
			goods_name = buygoods_id.getGoods_name();
		}
	}
	//�����Ĳ˵���item��ѡ�����
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 1) {
			Builder builder = new Builder(this);
			builder.setMessage("��ȷ��Ҫɾ��" + goods_name + "��Ʒ��");
			//ȷ��ɾ����Ʒ
			builder.setPositiveButton("ȷ��", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					//�ҵ���Ʒ��ɾ��
					boolean flag = buyGoodsHelper.delBuyGoods(goods_id);
					if (flag == true) {
						//ˢ����Ʒ��ʾҳ��
						displayGoods();
						Toast.makeText(getApplicationContext(), "ɾ���ɹ���",
								Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(getApplicationContext(), "ɾ��ʧ�ܣ�",
								Toast.LENGTH_LONG).show();
					}
				}
			});
			//ȡ��ɾ����Ʒ�Ĳ���
			builder.setNegativeButton("ȡ��", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					return;
				}
			});
			builder.create().show();
		}
		return super.onContextItemSelected(item);
	}
	//��ǰҳ������ʱ�ر����ݿ�
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(buyGoodsHelper!=null){
			buyGoodsHelper.close();
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		displayGoods();
	}
	private static int REQUEST_CODE=1;
	// ��д����������ڻ�ȡ���ص�����
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if(requestCode==REQUEST_CODE){
			if(resultCode==RESULT_OK){
				if(data!=null){
					String address=data.getStringExtra("address");
					update_address_text.setText(address);
				}
			}
		}
	}
}
