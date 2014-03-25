package com.soubo.adaper;

import java.util.ArrayList;
import java.util.List;

import com.soubo.app.R;
import com.soubo.app.R.drawable;
import com.soubo.entity.ImageCacher;
import com.soubo.entity.UrlAddress;
import com.soubo.entity.goodsInfo;
import com.soubo.util.AsyncImageLoader;
import com.soubo.util.AsyncImageLoader.ImageCallback;
import com.soubo.widgets.PageGridView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
/**
 * ��Ʒ������Ϣ��ʾadapter
 * @author Administrator
 *
 */
public class DetailProductItemAdapter extends BaseAdapter {
	private ImageView goods_iamge;
	private TextView goods_name,goods_price;
	private AsyncImageLoader asyncImageLoader;
	// ��Ʒ��Ϣ
	String image_tag="";
	private List<goodsInfo> goods_list;
	private PageGridView gridView;
	public DetailProductItemAdapter(Context context,
			List<goodsInfo> goods_list,PageGridView gridView) {
		asyncImageLoader=new AsyncImageLoader(context);
		this.context = context;
		this.goods_list = goods_list;
		this.gridView=gridView;
	}
	/**
	 * ��������
	 * 
	 * @param list
	 */
	public void AddMoreData(List<goodsInfo> list) {
		this.goods_list.addAll(list);
		this.notifyDataSetChanged();
	}

	private Context context;
	/**
	 * ����
	 * 
	 * @param list
	 */
	public void InsertData(List<goodsInfo> list) {
		this.goods_list.addAll(0, list);
		this.notifyDataSetChanged();
	}
public List<goodsInfo> getData(){
	return goods_list;
}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return goods_list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return goods_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		goodsInfo goods = goods_list.get(position);
		if (convertView == null) {
			// ��convertViewΪ����ôͨ��LayoutInflater����ز��֣�����һ��view����
			convertView = LayoutInflater.from(context).inflate(
					R.layout.detail_product_item, null);
			// ͨ��convertView������findViewById����������id������Ӧ���ӿؼ�
			goods_iamge = (ImageView) convertView.findViewById(R.id.carImg);
			goods_name = (TextView) convertView
					.findViewById(R.id.product_name);
			 goods_price = (TextView) convertView
					.findViewById(R.id.product_price);
		}else if(convertView!=null && convertView.getId()==R.id.gridview){
			image_tag=(String) convertView.getTag();
		}
		//goods_iamge=(ImageView) convertView.getTag();
		
		String default_image = goods.getDefault_image();
		if (default_image.contains("?")) {
			// �ض�?����ַ�����������ЧͼƬ
			default_image = default_image.substring(0,
					default_image.indexOf("?"));
		}
		String tag = "http://hc.sou100.cn/" + default_image;
		goods_iamge.setTag(tag);
		Drawable drawable = asyncImageLoader.loadDrawable(
				ImageCacher.EnumImageType.goods_photo, tag,
				new ImageCallback() {
					//��μ�ʵ�֣������һ�μ���urlʱ���淽����ִ��
					public void imageLoaded(Drawable imageDrawable, String tag) {
						ImageView imageView=(ImageView) gridView.findViewWithTag(tag);
						if (imageView != null && imageDrawable != null) {
							imageView.setImageDrawable(imageDrawable);
						} else {
							try {
								imageView.setImageResource(R.drawable.my_search3);
								
							} catch (Exception e) {
								// TODO: handle exception
								
							}
							
						}
					}
				});

		// ����ͼƬ
		if (drawable != null) {
			goods_iamge.setImageDrawable(drawable);
		} 
		// �����ı�
		goods_name.setText(goods.getGoods_name());
		// �����ı�
		goods_price.setText("��" + goods.getPrice());
		goods_price.setTag(goods.getGoods_id());
		return convertView;
	}
}
