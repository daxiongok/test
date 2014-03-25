package com.soubo.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
/**
 * ���ù��ﳵ�е�adapter
 * @author Administrator
 *
 */
public class LinearLayoutForListview extends ListView {

	public LinearLayoutForListview(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public LinearLayoutForListview(Context context, AttributeSet attrs) {

		super(context, attrs);

	}

	public LinearLayoutForListview(Context context, AttributeSet attrs,

	int defStyle) {

		super(context, attrs, defStyle);

	}

	@Override
	/**
	 
	 * ��д�÷������ﵽʹListView��ӦScrollView��Ч��
	 
	 */
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
		MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);

	}

}