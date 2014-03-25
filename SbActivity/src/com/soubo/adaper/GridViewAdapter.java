package com.soubo.adaper;


import com.soubo.app.R;

import android.content.Context;
import android.graphics.BitmapFactory.Options;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridViewAdapter extends BaseAdapter{
	
	//∂®“Âcontext
	private Context mcontext;
	
    private int[] mImageId = {
   R.drawable.catergory_deskbook,
   R.drawable.catergory_deskbook,
   R.drawable.catergory_deskbook,
   R.drawable.catergory_deskbook,
   R.drawable.catergory_deskbook,
   R.drawable.catergory_deskbook,
   R.drawable.catergory_deskbook,
   R.drawable.catergory_deskbook,
    	
    };
    public GridViewAdapter(Context c){
    	mcontext=c;
    	
    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mImageId.length;
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

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView imageView=null;
		if(convertView==null){
			imageView=new ImageView(mcontext);
			imageView.setLayoutParams(new GridView.LayoutParams(320*320, position));
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			
		}else {
			imageView=(ImageView)convertView;
		}
		imageView.setImageResource(mImageId[position]);
		
		return imageView;
	}
    
}
