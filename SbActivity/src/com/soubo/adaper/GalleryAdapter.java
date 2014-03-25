package com.soubo.adaper;

import java.util.ArrayList;
import java.util.List;

import com.soubo.app.R;
import com.soubo.app.R.drawable;
import com.soubo.app.VariableInfo;
import com.soubo.entity.ImageCacher;
import com.soubo.util.AsyncImageLoader;
import com.soubo.util.AsyncImageLoader.ImageCallback;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 商品详情的商品图片adapter
 * @author Administrator
 *
 */
public class GalleryAdapter extends BaseAdapter {
	private Context context;
	private List<String> image_url;
	private AsyncImageLoader asyncImageLoader;
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return image_url.size();
	}
	public GalleryAdapter(Context context,List<String> image_url){
		this.context=context;
		this.image_url=image_url;
		asyncImageLoader=new AsyncImageLoader(context);
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return image_url.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ImageView imageView = new ImageView(context);
      //设置样式 ：填充整个屏幕  
        imageView.setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.FILL_PARENT,  
                100));
      //设置缩放比例：保持原样  
        imageView.setScaleType(ImageView.ScaleType.CENTER); 
        //存放已下载的图片
        final List<Drawable> image=new ArrayList<Drawable>();
        for (int i = 0; i < image_url.size(); i++) {
        	//下载图片的路径
			String tag="http://hc.sou100.cn/"+image_url.get(position);
			//下载图片
			Drawable drawable=	asyncImageLoader.loadDrawable(ImageCacher.EnumImageType.goods_photo, tag, new ImageCallback() {
				public void imageLoaded(Drawable imageDrawable, String tag) {
					// TODO Auto-generated method stub
					if(imageView!=null && imageDrawable!=null){
						imageView.setImageDrawable(imageDrawable);
						//image.add(imageDrawable);
					}else{
						imageView.setImageResource(R.drawable.my_search3);
					}
				}
			});	
			//将下载的图片添加到集合中
			image.add(drawable);
		}
        //为控件添加图片
        if(image!=null){
        	imageView.setImageDrawable(image.get(position));
        }
        imageView.setTag(image);
        
        return imageView;
	}
}
