package com.soubo.adaper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;

import com.soubo.app.R;
import com.soubo.entity.CollectionInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class CollectionAdapter extends BaseAdapter{
	private LayoutInflater mInflater = null;// 得到一个LayoutInflater对象导入布局
	private Context context;

//private ArrayList<HashMap<String, String>> list;
private CollectionInfo info;
/*private List<Map<String, Object>> mData;    
public static Map<Integer, Boolean> isSelected; */   
	public CollectionAdapter(Context context,CollectionInfo collectioninfo){
		this.context=context;
//		this.list = lists; 
		this.info=collectioninfo;
		  mInflater = LayoutInflater.from(context);
//		
	}

	





	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 10;
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
	
		if(convertView==null){
			holder = new ViewHolder();
			// 加载布局
			convertView = mInflater.inflate(R.layout.activity_collection_item,null);
			holder.delete_body=(CheckBox)convertView.findViewById(R.id.delete_body);
			holder.collect_body=(ImageView)convertView.findViewById(R.id.collect_body);
			holder.collection_body_name=(TextView)convertView.findViewById(R.id.collection_body_name);
			holder.collection_prices=(TextView)convertView.findViewById(R.id.collection_prices);

			// 将布局设置在setTag
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
			// 设置TextView的显示
//	        holder.collection_body_name.setText(list.get(position).get("content").toString());
		    holder.collection_body_name.setText(info.getGoods_name().toString());
		    holder.collection_prices.setText(Double.toString(info.getPrice()));
	        // 根据flag来设置checkbox的选中状况
//		    holder.delete_body.setChecked(isSelected.get(position));    

		}
		return convertView;
	}
	
	static class ViewHolder {
	  private TextView collection_body_name,collection_prices;
	  private CheckBox delete_body;
	  private ImageView collect_body;
 
	}
	

}
