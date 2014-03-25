package com.soubo.app;

import java.util.ArrayList;
import java.util.List;

import com.soubo.app.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ViewFlipper;

/**
 * 显示商品全图
 * 
 * @author Administrator
 * 
 */
public class GoodsImageApp extends Activity implements OnGestureListener{
	private ViewFlipper viewFlipper;
	private static final int SLIDE_RANGE = 100;
	private static final int AUTO_SLIDE_TIME = 5;
	private GestureDetector gestureDetector = null;
	private int mFiSize = 0;
	private VariableInfo info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.goods_image_display);
		viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);

		// 从上一个页面取值
		Intent intent = getIntent();
		info = (VariableInfo) getApplication();

		// * ArrayList<Drawable> iamge_list = (ArrayList<Drawable>)
		// * intent.getExtras().get( "image");

		ArrayList<Bitmap> iamge_list = info.getBitmap();
		mFiSize = iamge_list.size();
		System.out.println("mFiSize" + mFiSize);
		// 去下表
		int position = intent.getIntExtra("position", 0);
		gestureDetector = new GestureDetector(this);
		// 加载图片
		for (int i = 0; i < iamge_list.size(); i++) {
			ImageView imageView = new ImageView(this);
			imageView.setImageBitmap(iamge_list.get(i));
			viewFlipper.addView(imageView, new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		}
		viewFlipper.setDisplayedChild(position); // 当前显示的图片位置
		viewFlipper.setAutoStart(true);
		viewFlipper.setFlipInterval(AUTO_SLIDE_TIME * 1000);
		if (viewFlipper.isAutoStart() && !viewFlipper.isFlipping()) {
			viewFlipper.startFlipping();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		viewFlipper.stopFlipping();
		viewFlipper.setAutoStart(false);
		return gestureDetector.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 定义从右侧进入的动画效果
	 * 
	 * @return
	 */
	protected Animation inFromRightAnimation() {
		Animation inFromRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromRight.setDuration(200);
		inFromRight.setInterpolator(new AccelerateInterpolator());
		return inFromRight;
	}

	/**
	 * 定义从左侧退出的动画效果
	 * 
	 * @return
	 */
	protected Animation outToLeftAnimation() {
		Animation outtoLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outtoLeft.setDuration(200);
		outtoLeft.setInterpolator(new AccelerateInterpolator());
		return outtoLeft;
	}

	/**
	 * 定义从左侧进入的动画效果
	 * 
	 * @return
	 */
	protected Animation inFromLeftAnimation() {
		Animation inFromLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromLeft.setDuration(200);
		inFromLeft.setInterpolator(new AccelerateInterpolator());
		return inFromLeft;
	}

	/**
	 * 定义从右侧退出时的动画效果
	 * 
	 * @return
	 */
	protected Animation outToRightAnimation() {
		Animation outtoRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outtoRight.setDuration(200);
		outtoRight.setInterpolator(new AccelerateInterpolator());
		return outtoRight;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		if (e2.getX() - e1.getX() > SLIDE_RANGE) {
			if (viewFlipper.getDisplayedChild() == 0) { // 滑动到第一张不能再向左滑动
				viewFlipper.stopFlipping();
				return false;
			} else {
				// 当像右侧滑动的时候  
				viewFlipper.setInAnimation(inFromLeftAnimation());  
                viewFlipper.setOutAnimation(outToRightAnimation());  
				viewFlipper.showPrevious();
				return true;
			}
		} else if (e2.getX() - e1.getX() < -SLIDE_RANGE) {
			if (viewFlipper.getDisplayedChild() == mFiSize - 1) { // 滑动到最后一张不能再向右滑动
				viewFlipper.stopFlipping();
				return false;
			} else {
				viewFlipper.setInAnimation(inFromRightAnimation());  
                //设置View退出屏幕时候使用的动画  
				viewFlipper.setOutAnimation(outToLeftAnimation());  
				viewFlipper.showNext();
				return true;
			}
		}
		return true;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
}
