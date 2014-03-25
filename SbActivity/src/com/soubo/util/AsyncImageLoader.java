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
	 * 异步加载图片
	 */
	/**
	 * android Listview 软引用SoftReference异步加载图片 机制：简单来说，它会帮助我们管理内存，防止内存溢出，
	 * 另外一点也就相当于map，临时缓存些图片drawable让我们可以直接引用，很好了解决了OOM异常.
	 */
	// 软引用
	private HashMap<String, SoftReference<Drawable>> imageCache;

	// 定义Context对象
	private Context curContext;
	public AsyncImageLoader(Context context){
		this.curContext=context;
		imageCache=new HashMap<String, SoftReference<Drawable>>();
	}
	
	/**
	 * 直接下载图片
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
	 * 将图片下载到本地并保存
	 * 
	 * @param imgType //图片类型
	 * @param tag     //图片地址
	 * @param imageCallback  //回调接口
	 * @return
	 */
	public Drawable loadDrawable(final ImageCacher.EnumImageType imgType,
			final String tag, final ImageCallback imageCallback) {
		Drawable sampleDrawable = curContext.getResources().getDrawable(R.drawable.my_search3);
		if (tag.trim().equals("")) {
			return sampleDrawable;
		}
		System.out.println("tag===>"+tag);
		//分割字符串
		String[] twoParts = tag.split("\\|", 2);
		final String imageUrl = twoParts[0];
		System.out.println("imageUrl===>"+imageUrl);
		final String folder = ImageCacher.GetImageFolder(imgType);
		String outFilename = folder
				+ imageUrl.substring(imageUrl.lastIndexOf("/")+1);
		Log.i("下载", tag);
		Log.i("本地", outFilename);
		File file = new File(outFilename);
		//如果缓存过就从缓存中取图片
		if (imageCache.containsKey(imageUrl)) {
			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			Drawable drawable = softReference.get();// 缓存
			/**
			 * 防止内存溢出，另外一点也就相当于map，临时缓存些图片 drawable让我们可以直接引用，
			 * 很好了解决了OOM异常.
			 */
			if (drawable != null) {// 找出被软引用回收的对象
				// 以drawable为标志，从haspmap中移除
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
		// 开启线程下载图片
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
					// 将下载的图片保存至缓存中
					imageCache.put(imageUrl, new SoftReference<Drawable>(
							drawable));
				Message message = handler.obtainMessage(0, drawable);
				handler.sendMessage(message);
			}
		}.start();
		return sampleDrawable;
	}
	
	// 回调接口
	public interface ImageCallback {
				public void imageLoaded(Drawable imageDrawable, String tag);
			}
}
