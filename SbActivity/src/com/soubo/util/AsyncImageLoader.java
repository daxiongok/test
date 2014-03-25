package com.soubo.util;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.HashMap;

import com.soubo.app.R;
import com.soubo.entity.ImageCacher;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class AsyncImageLoader {
	/*
	 * �첽����ͼƬ
	 */
	/**
	 * android Listview ������SoftReference�첽����ͼƬ ���ƣ�����˵������������ǹ����ڴ棬��ֹ�ڴ������
	 * ����һ��Ҳ���൱��map����ʱ����ЩͼƬdrawable�����ǿ���ֱ�����ã��ܺ��˽����OOM�쳣.
	 */
	// ������
	private HashMap<String, SoftReference<Drawable>> imageCache;

	// ����Context����
	private Context curContext;
	public AsyncImageLoader(Context context){
		this.curContext=context;
		imageCache=new HashMap<String, SoftReference<Drawable>>();
	}
	
	/**
	 * ֱ������ͼƬ
	 * @param imageUrl
	 * @param imageType
	 */
	public void loadDrawable(final String imagaeUrl,final ImageCacher.EnumImageType imageType){
		final String folder=ImageCacher.GetImageFolder(imageType);
		new Thread(){
			public void run() {
				NetWorkHelper.loadImageFromUrlWithStore(folder, imagaeUrl);
			};
		}.start();
	}
	
	
	
	/**
	 * ��ͼƬ���ص����ز�����
	 * 
	 * @param imgType //ͼƬ����
	 * @param tag     //ͼƬ��ַ
	 * @param imageCallback  //�ص��ӿ�
	 * @return
	 */
	public Drawable loadDrawable(final ImageCacher.EnumImageType imgType,
			final String tag, final ImageCallback imageCallback) {
		Drawable sampleDrawable = curContext.getResources().getDrawable(R.drawable.my_search3);
		if (tag.trim().equals("")) {
			return sampleDrawable;
		}
		System.out.println("tag===>"+tag);
		//�ָ��ַ���
		String[] twoParts = tag.split("\\|", 2);
		final String imageUrl = twoParts[0];
		System.out.println("imageUrl===>"+imageUrl);
		final String folder = ImageCacher.GetImageFolder(imgType);
		String outFilename = folder
				+ imageUrl.substring(imageUrl.lastIndexOf("/")+1);
		Log.i("����", tag);
		Log.i("����", outFilename);
		File file = new File(outFilename);
		//���������ʹӻ�����ȡͼƬ
		if (imageCache.containsKey(imageUrl)) {
			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			Drawable drawable = softReference.get();// ����
			/**
			 * ��ֹ�ڴ����������һ��Ҳ���൱��map����ʱ����ЩͼƬ drawable�����ǿ���ֱ�����ã�
			 * �ܺ��˽����OOM�쳣.
			 */
			if (drawable != null) {// �ҳ��������û��յĶ���
				// ��drawableΪ��־����haspmap���Ƴ�
				return drawable;
			}
		} else if (file.exists()) {
			Bitmap bitmap = BitmapFactory.decodeFile(outFilename);
			Drawable drawable = new BitmapDrawable(bitmap);
			return drawable;
		}

		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				imageCallback.imageLoaded((Drawable) message.obj, tag);
			}
		};
		// �����߳�����ͼƬ
		new Thread() {
			public void run() {
				Drawable drawable = NetWorkHelper.loadImageFromUrlWithStore(folder,imageUrl);
				if (drawable == null) {
					drawable = NetWorkHelper.loadImageFromUrl(imageUrl);
					if (drawable != null) {
						imageCache.put(imageUrl, new SoftReference<Drawable>(
								drawable));
						Message message = handler.obtainMessage(0, drawable);
						handler.sendMessage(message);
					}
				} else
					// �����ص�ͼƬ������������
					imageCache.put(imageUrl, new SoftReference<Drawable>(
							drawable));
				Message message = handler.obtainMessage(0, drawable);
				handler.sendMessage(message);
			}
		}.start();
		return sampleDrawable;
	}
	
	// �ص��ӿ�
	public interface ImageCallback {
				public void imageLoaded(Drawable imageDrawable, String tag);
			}
}
