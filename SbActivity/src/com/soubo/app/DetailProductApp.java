package com.soubo.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.soubo.adaper.DetailProductItemAdapter;
import com.soubo.adaper.GridViewAdapter;
import com.soubo.db.GoodsHelper;
import com.soubo.entity.ImageCacher;
import com.soubo.entity.ProductTypeInfo;
import com.soubo.entity.UrlAddress;
import com.soubo.entity.goodsInfo;
import com.soubo.util.AsyncImageLoader;
import com.soubo.util.JsonUtil;
import com.soubo.util.NetWorkHelper;
import com.soubo.widgets.PageGridView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class DetailProductApp extends Activity implements OnItemClickListener {
	// ����Ʒ��Ϣ�ļ���
	private List<goodsInfo> goods_list = new ArrayList<goodsInfo>();
	// ��Ʒ����id
	private String type_id;
	// ������ʾ��Ʒ
	private PageGridView gridview;
	private GoodsHelper goodsHelper;
	private DetailProductItemAdapter adapter;
	private int lastItem, pageIndex = 0;// ҳ��;
	private LinearLayout viewFooter;

	ProgressBar blogBody_progressBar;// ����ListView���ؿ�

	// �����ʼ��
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ȥ����
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detail_product);
		gridview = (PageGridView) findViewById(R.id.gridview);
		//����ע�⣬һ��Ҫ����GravityΪGravity.CENTER,���򲻻����ҳ��
	    gridview.setGravity(Gravity.CENTER);
		Intent intent = getIntent();
		// ȡ����һ��ҳ�洫������ֵ
		type_id = intent.getStringExtra("type_id");
		if ("".equals(type_id) || type_id == null) {
			type_id = "1283";
		}
		goodsHelper = new GoodsHelper(getApplicationContext());
		gridview.setOnItemClickListener(DetailProductApp.this);
		init();
		new PageIndex(0, true).execute();
	}

	public void init() {
		// �ײ�view
		LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		viewFooter = (LinearLayout) mInflater.inflate(R.layout.listview_footer,
				null, false);
		
		gridview.setOnScrollListener(new OnScrollListener() {

			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					pageIndex = pageIndex + 1;
					new PageIndex(pageIndex, false).execute();
				}

			}

			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				lastItem = firstVisibleItem - 2 + visibleItemCount;
				if(view.getLastVisiblePosition() != (view.getCount() - 1)){
	                gridview.updateFooter(View.GONE);
	            }
			}
		});
	}

	// ����gridview��item�ĵ���¼�����ֵ
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		TextView textView = (TextView) arg1
				.findViewById(R.id.product_price);
		String goods_id = textView.getTag().toString();
		if ("2718".equals(type_id) || "2719".equals(type_id)
				|| "2720".equals(type_id) || "2721".equals(type_id)
				|| "2722".equals(type_id)) {
			Intent intent=new Intent(this, DiningDetailApp.class);
			intent.putExtra("goods_id", goods_id);
			startActivity(intent);
		} else {
			Intent intent = new Intent(this, GoodsDetailApp.class);
			intent.putExtra("goods_id", goods_id);
			startActivity(intent);
		}
	}

	// ���ö��߳�����ͼƬ
	public class PageIndex extends AsyncTask<String, Integer, List<goodsInfo>> {
		boolean isRefresh = false;
		int curPageIndex = 0;
		boolean isLocalData = false;// �Ƿ��Ǵӱ��ض�ȡ������

		public PageIndex(int page, boolean isRefresh) {
			curPageIndex = page;
			this.isRefresh = isRefresh;
		}

		/*
		 * ����onPreExecute ����ִ�к�����ִ�У��÷��������ں�̨�߳��С� ���ｫ��Ҫ����ִ����Щ�ܺ�ʱ�ĺ�̨�������� ���Ե���
		 * publishProgress����������ʵʱ��������ȡ� �÷����ǳ��󷽷����������ʵ��
		 */
		@Override
		protected List<goodsInfo> doInBackground(String... params) {
			// ��ȡ����״̬
			boolean isNetworkAvailable = NetWorkHelper
					.networkIsAvailable(getApplicationContext());
			int _pageIndex = curPageIndex;
			if (_pageIndex <= 0) {
				_pageIndex = 1;
			}

			// ���ȶ�ȡ��������
			List<goodsInfo> listBlogLocal = goodsHelper.GetGoodsByPage(
					_pageIndex, UrlAddress.BLOG_PAGE_SIZE);
			if (isNetworkAvailable) {// ���������
				List<goodsInfo> new_goods_list = goodsHelper.GetBlogList(
						_pageIndex, type_id);
				switch (curPageIndex) {
				case -1:// ����
					List<goodsInfo> listTmp = new ArrayList<goodsInfo>();
					if (goods_list != null && goods_list.size() > 0) {// ������ҳ������ʱ
						if (new_goods_list != null && new_goods_list.size() > 0) {
							int size = new_goods_list.size();
							for (int i = 0; i < size; i++) {
								if (!goods_list.contains(new_goods_list.get(i))) {// ��������ظ�
									listTmp.add(goods_list.get(i));
								}
							}
						}
					}
					return listTmp;
				case 0:// �״μ���
				case 1:// ˢ��
					if (new_goods_list != null && new_goods_list.size() > 0) {
						return new_goods_list;
					}
					break;
				default:// ����
					List<goodsInfo> listT = new ArrayList<goodsInfo>();
					if (goods_list != null && goods_list.size() > 0) {// ������ҳ������ʱ
						if (new_goods_list != null && new_goods_list.size() > 0) {
							int size = new_goods_list.size();
							for (int i = 0; i < size; i++) {
								if (!goods_list.contains(new_goods_list.get(i))) {// ��������ظ�
									listT.add(new_goods_list.get(i));
								}
							}
						}
					}
					return listT;
				}
			} else {// ���������
				isLocalData = true;
				if (curPageIndex == -1) {// ��������������
					return null;
				}
				return listBlogLocal;
			}

			return null;
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected void onPostExecute(List<goodsInfo> result) {
			super.onPostExecute(result);
			// ���粻���ò��ұ���û�б�������
			if (result == null || result.size() == 0) {// û��������
				if (!NetWorkHelper.networkIsAvailable(getApplicationContext())
						&& curPageIndex > 1) {// ��������û������
					Toast.makeText(getApplicationContext(),
							R.string.sys_network_error, Toast.LENGTH_SHORT)
							.show();
					gridview.removeViewInLayout(viewFooter);
				}
				return;
			}
			// ���浽���ݿ�
			if (!isLocalData) {
				goodsHelper.SynchronyData2DB(result);
			}

			if (curPageIndex == -1) {// ����ˢ��
				adapter.InsertData(result);
			} else if (curPageIndex == 0) {// �״μ���
				goods_list = result;// dbHelper.GetTopBlogList();

				adapter = new DetailProductItemAdapter(getApplicationContext(),
						goods_list, gridview);
				gridview.setAdapter(adapter);
			} else if (curPageIndex == 1) {// ˢ��
				try {// ������ҳ��������أ���ˢ�°�ť
					goods_list = result;
					if (adapter != null && adapter.getData() != null) {
						adapter.getData().clear();
						adapter.AddMoreData(goods_list);
					} else if (result != null) {
						adapter = new DetailProductItemAdapter(
								getApplicationContext(), goods_list, gridview);
						gridview.setAdapter(adapter);
					}
					 // ���ݲ��� 
				} catch (Exception ex) {
				 Log.e("BlogActivity", ex.getMessage());
				}
			} else {// ����
				adapter.AddMoreData(result);
			}
		}

		@Override
		protected void onPreExecute() {
			// ���������
			/*if (!isRefresh) {// �ײ��ؼ���ˢ��ʱ��������
				TextView tvFooterMore = (TextView) findViewById(R.id.tvFooterMore);
				tvFooterMore.setText(R.string.pull_to_refresh_refreshing_label);
				tvFooterMore.setVisibility(View.VISIBLE);
				ProgressBar list_footer_progress = (ProgressBar) findViewById(R.id.list_footer_progress);
				list_footer_progress.setVisibility(View.VISIBLE);
			}*/
		}

	}

	// �ر����ݿ�
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (goodsHelper != null) {
			goodsHelper.Close();
		}
	}
}
