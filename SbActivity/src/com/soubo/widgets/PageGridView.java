package com.soubo.widgets;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

@SuppressLint("NewApi")
public class PageGridView extends LinearLayout {
	private GridView gridview;
	private LinearLayout footerView;

	public static final String TAG = "ListViewWithPage";

	public PageGridView(Context context) {
	    super(context);
	    init();
	}

	public PageGridView(Context context, AttributeSet attrs, int defStyle) {
	    super(context, attrs, defStyle);
	    init();
	}

	public PageGridView(Context context, AttributeSet attrs) {
	    super(context, attrs);
	    init();
	}

	private void init(){        
	    gridview = new GridView(getContext());
	    setOrientation(LinearLayout.VERTICAL);
	    addView(gridview);  

	    gridview.getLayoutParams().width = LinearLayout.LayoutParams.FILL_PARENT;
	    gridview.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
	    gridview.setColumnWidth(70);
	    gridview.setNumColumns(4);
	    gridview.setVerticalSpacing(10);
	    gridview.setHorizontalSpacing(10);
	    //����footer,����������ӽ�����������
	    footerView = new LinearLayout(getContext());

	    ProgressBar pb = new ProgressBar(getContext());
	    TextView tvMessage = new TextView(getContext());
	    tvMessage.setText("���ڼ������ݡ�����");
	    tvMessage.setTextSize(20);

	    footerView.addView(pb);
	    footerView.addView(tvMessage);
	    footerView.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);

	    addView(footerView);

	    footerView.getLayoutParams().width = LinearLayout.LayoutParams.FILL_PARENT;
	    footerView.getLayoutParams().height = 100;        
	    footerView.setVisibility(View.GONE);
	}

	//���ڵ��ô˷���һ�㶼Ϊ�����̣߳�����ֱ�Ӹ��¿ؼ�״̬�������Ҫһ��Handler��Э��
	public void updateFooter(int statue){
	    updateFooterViewHandler.sendEmptyMessage(statue);
	}

	private Handler updateFooterViewHandler = new Handler(){
	    @Override
	    public void handleMessage(android.os.Message msg) {
	        //����״̬ ���Կ���Ϊ����������Ҫ������ͷ�Ļ������Ը���״̬���޸Ŀؼ����ݣ�������ֻ�����Ƿ���ʾ����
	        footerView.setVisibility(msg.what);
	        //������View.GONE��ʱ�������Ѿ�������ɣ������Ҫ֪ͨ���ݸı�
	        if(msg.what==View.GONE){
	            ((BaseAdapter)gridview.getAdapter()).notifyDataSetChanged();
	        }
	    };
	};

	public void setOnScrollListener(OnScrollListener onScrollListener){
	    gridview.setOnScrollListener(onScrollListener);
	}

	public void setNumColumns(int number){
	    gridview.setNumColumns(number);
	}

	public void setVerticalSpacing(int spacing){
	    gridview.setVerticalSpacing(spacing);
	}

	public void setHorizontalSpacing(int spacing){
	    gridview.setHorizontalSpacing(spacing);
	}

	public void setColumnWidth(int width){
	    gridview.setColumnWidth(width);
	}

	public void setStretchMode(int stretchMode){
	    gridview.setStretchMode(stretchMode);
	}

	public void setAdapter(BaseAdapter adapter){
	    gridview.setAdapter(adapter);        
	}

	public void setOnItemClickListener(OnItemClickListener itemClickListener){
	    gridview.setOnItemClickListener(itemClickListener);
	}
}
