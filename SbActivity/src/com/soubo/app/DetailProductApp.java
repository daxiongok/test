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
	// 存商品信息的集合
	private List<goodsInfo> goods_list = new ArrayList<goodsInfo>();
	// 商品类型id
	private String type_id;
	// 网格显示商品
	private PageGridView gridview;
	private GoodsHelper goodsHelper;
	private DetailProductItemAdapter adapter;
	private int lastItem, pageIndex = 0;// 页码;
	private LinearLayout viewFooter;

	ProgressBar blogBody_progressBar;// 主题ListView加载框

	// 界面初始化
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detail_product);
		gridview = (PageGridView) findViewById(R.id.gridview);
		//这里注意，一定要设置Gravity为Gravity.CENTER,否则不会出现页脚
	    gridview.setGravity(Gravity.CENTER);
		Intent intent = getIntent();
		// 取从上一个页面传过来的值
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
		// 底部view
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

	// 设置gridview的item的点击事件并传值
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

	// 启用多线程下载图片
	public class PageIndex extends AsyncTask<String, Integer, List<goodsInfo>> {
		boolean isRefresh = false;
		int curPageIndex = 0;
		boolean isLocalData = false;// 是否是从本地读取的数据

		public PageIndex(int page, boolean isRefresh) {
			curPageIndex = page;
			this.isRefresh = isRefresh;
		}

		/*
		 * 将在onPreExecute 方法执行后马上执行，该方法运行在后台线程中。 这里将主要负责执行那些很耗时的后台处理工作。 可以调用
		 * publishProgress方法来更新实时的任务进度。 该方法是抽象方法，子类必须实现
		 */
		@Override
		protected List<goodsInfo> doInBackground(String... params) {
			// 获取网络状态
			boolean isNetworkAvailable = NetWorkHelper
					.networkIsAvailable(getApplicationContext());
			int _pageIndex = curPageIndex;
			if (_pageIndex <= 0) {
				_pageIndex = 1;
			}

			// 优先读取本地数据
			List<goodsInfo> listBlogLocal = goodsHelper.GetGoodsByPage(
					_pageIndex, UrlAddress.BLOG_PAGE_SIZE);
			if (isNetworkAvailable) {// 有网络情况
				List<goodsInfo> new_goods_list = goodsHelper.GetBlogList(
						_pageIndex, type_id);
				switch (curPageIndex) {
				case -1:// 上拉
					List<goodsInfo> listTmp = new ArrayList<goodsInfo>();
					if (goods_list != null && goods_list.size() > 0) {// 避免首页无数据时
						if (new_goods_list != null && new_goods_list.size() > 0) {
							int size = new_goods_list.size();
							for (int i = 0; i < size; i++) {
								if (!goods_list.contains(new_goods_list.get(i))) {// 避免出现重复
									listTmp.add(goods_list.get(i));
								}
							}
						}
					}
					return listTmp;
				case 0:// 首次加载
				case 1:// 刷新
					if (new_goods_list != null && new_goods_list.size() > 0) {
						return new_goods_list;
					}
					break;
				default:// 下拉
					List<goodsInfo> listT = new ArrayList<goodsInfo>();
					if (goods_list != null && goods_list.size() > 0) {// 避免首页无数据时
						if (new_goods_list != null && new_goods_list.size() > 0) {
							int size = new_goods_list.size();
							for (int i = 0; i < size; i++) {
								if (!goods_list.contains(new_goods_list.get(i))) {// 避免出现重复
									listT.add(new_goods_list.get(i));
								}
							}
						}
					}
					return listT;
				}
			} else {// 无网络情况
				isLocalData = true;
				if (curPageIndex == -1) {// 上拉不加载数据
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
			// 网络不可用并且本地没有保存数据
			if (result == null || result.size() == 0) {// 没有新数据
				if (!NetWorkHelper.networkIsAvailable(getApplicationContext())
						&& curPageIndex > 1) {// 下拉并且没有网络
					Toast.makeText(getApplicationContext(),
							R.string.sys_network_error, Toast.LENGTH_SHORT)
							.show();
					gridview.removeViewInLayout(viewFooter);
				}
				return;
			}
			// 保存到数据库
			if (!isLocalData) {
				goodsHelper.SynchronyData2DB(result);
			}

			if (curPageIndex == -1) {// 上拉刷新
				adapter.InsertData(result);
			} else if (curPageIndex == 0) {// 首次加载
				goods_list = result;// dbHelper.GetTopBlogList();

				adapter = new DetailProductItemAdapter(getApplicationContext(),
						goods_list, gridview);
				gridview.setAdapter(adapter);
			} else if (curPageIndex == 1) {// 刷新
				try {// 避免首页无网络加载，按刷新按钮
					goods_list = result;
					if (adapter != null && adapter.getData() != null) {
						adapter.getData().clear();
						adapter.AddMoreData(goods_list);
					} else if (result != null) {
						adapter = new DetailProductItemAdapter(
								getApplicationContext(), goods_list, gridview);
						gridview.setAdapter(adapter);
					}
					 // 传递参数 
				} catch (Exception ex) {
				 Log.e("BlogActivity", ex.getMessage());
				}
			} else {// 下拉
				adapter.AddMoreData(result);
			}
		}

		@Override
		protected void onPreExecute() {
			// 主体进度条
			/*if (!isRefresh) {// 底部控件，刷新时不做处理
				TextView tvFooterMore = (TextView) findViewById(R.id.tvFooterMore);
				tvFooterMore.setText(R.string.pull_to_refresh_refreshing_label);
				tvFooterMore.setVisibility(View.VISIBLE);
				ProgressBar list_footer_progress = (ProgressBar) findViewById(R.id.list_footer_progress);
				list_footer_progress.setVisibility(View.VISIBLE);
			}*/
		}

	}

	// 关闭数据库
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (goodsHelper != null) {
			goodsHelper.Close();
		}
	}
}
