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
 * ��Ʒ�������ƷͼƬadapter
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
      //������ʽ �����������Ļ  
        imageView.setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.FILL_PARENT,  
                100));
      //�������ű���������ԭ��  
        imageView.setScaleType(ImageView.ScaleType.CENTER); 
        //��������ص�ͼƬ
        final List<Drawable> image=new ArrayList<Drawable>();
        for (int i = 0; i < image_url.size(); i++) {
        	//����ͼƬ��·��
			String tag="http://hc.sou100.cn/"+image_url.get(position);
			//����ͼƬ
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
			//�����ص�ͼƬ��ӵ�������
			image.add(drawable);
		}
        //Ϊ�ؼ����ͼƬ
        if(image!=null){
        	imageView.setImageDrawable(image.get(position));
        }
        imageView.setTag(image);
        
        return imageView;
	}
}
