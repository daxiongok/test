package com.soubo.adaper;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import com.soubo.app.BuyBadyApp;
import com.soubo.app.R;
import com.soubo.entity.BuyBodyInfo;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BuyBodyAdapter extends BaseAdapter {
	private LayoutInflater mInflater = null;// 得到一个LayoutInflater对象导入布局
	private Runnable runnable;
	private BuyBodyInfo info;
	private Context context;
	private BuyBodyInfo bodyInfo;
	protected List<BuyBodyInfo> reader;
    private ArrayList<BuyBodyInfo> infos ;

	public BuyBodyAdapter(Context context, ArrayList<BuyBodyInfo> reader) {
		// TODO Auto-generated constructor stub
		     this.context=context;
		     mInflater = LayoutInflater.from(context);
    		this.infos =reader;
	}

	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return infos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		// //如果convertView缓存为空 需新建view
		if (convertView == null) {


			holder = new ViewHolder();
			// 加载布局
			convertView = mInflater.inflate(R.layout.android_buy_bady_item,
					null);
			holder.buy_member = (TextView) convertView
					.findViewById(R.id.buy_member);
			holder.buy_time = (TextView) convertView
					.findViewById(R.id.buy_time);
			holder.buy_name = (TextView) convertView
					.findViewById(R.id.buy_name);
			holder.buy_xing = (TextView) convertView
					.findViewById(R.id.buy_xing);
			holder.buy_color = (TextView) convertView
					.findViewById(R.id.buy_color);
			holder.buy_price = (TextView) convertView
					.findViewById(R.id.buy_price);
			holder.buy_members = (TextView) convertView
					.findViewById(R.id.buy_members);
			holder.buy_times = (TextView) convertView
					.findViewById(R.id.buy_times);
			holder.buy_prices = (TextView) convertView
					.findViewById(R.id.buy_prices);
			holder.Return_shop = (TextView) convertView
					.findViewById(R.id.Return_shop);
			holder.buy_button = (Button) convertView
					.findViewById(R.id.buy_button);
			holder.buy_body = (ImageView) convertView
					.findViewById(R.id.buy_body);

			// 将布局设置在setTag
			convertView.setTag(holder);
           

		} else {
			holder = (ViewHolder) convertView.getTag();
			
		}
		holder.buy_member.setText(infos.get(position).getOrder_id()+"");
		holder.buy_time.setText(infos.get(position).getPay_time()+"");


		return convertView;
	}

	static class ViewHolder {
		public TextView buy_member, buy_time, buy_name, buy_xing, buy_color,
				buy_price, buy_members, buy_times, Return_shop, buy_prices;
		public ImageView buy_body;
		public Button buy_button;

	}

}
