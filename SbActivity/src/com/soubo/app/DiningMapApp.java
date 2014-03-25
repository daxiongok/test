package com.soubo.app;

import java.util.ArrayList;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionInfo;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.soubo.util.MyPoiOverlay;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * 餐饮地图显示
 * 
 * @author Administrator
 * 
 */
public class DiningMapApp extends Activity {
	private MapView mapView;
	private MKSearch mkSearch;// 发起搜索服务对象
	private BMapManager bMapManager;// 地图管理器
	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		bMapManager = new BMapManager(getApplicationContext());
		// 初始化
		bMapManager.init(VariableInfo.strKey,
				new VariableInfo.MyGeneralListener());
		// bMapManager.init(KEY, null);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dining_map_layout);
		init();
	}

	private void init() {
		mapView = (MapView) findViewById(R.id.bmapView);
		imageView = (ImageView) findViewById(R.id.image_view);
		mapView.setBuiltInZoomControls(true);
		mkSearch = new MKSearch();
		mkSearch.init(bMapManager, new MySearchListener());
		mkSearch.setDrivingPolicy(MKSearch.EBUS_TIME_FIRST);
		// 设置启用内置的缩放控件,地图控制器
		MapController mMapController = mapView.getController();
		// 得到mMapView的控制权,可以用它控制和驱动平移和缩放
		// 确定地图的某点，纬度和经度、

		GeoPoint point = new GeoPoint((int) (29.982641 * 1E6),
				(int) (106.281157 * 1E6));
		// 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		mMapController.setCenter(point);// 设置地图中心点
		mMapController.setZoom(15);// 设置地图zoom级别,给地图控制器设置比例尺
		mapView.setTraffic(true); // 设置为交通视图
		
		mkSearch.poiSearchInCity("重庆", "杨花椒老火锅");
	}

	@Override
	protected void onDestroy() {
		mapView.destroy();
		if (bMapManager != null) {
			bMapManager.destroy();
			bMapManager = null;
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		mapView.onPause();
		if (bMapManager != null) {
			bMapManager.stop();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		mapView.onResume();
		if (bMapManager != null) {
			bMapManager.start();
		}
		super.onResume();
	}

	class MySearchListener implements MKSearchListener {

		@Override
		public void onGetAddrResult(MKAddrInfo arg0, int arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onGetDrivingRouteResult(MKDrivingRouteResult arg0, int arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onGetPoiDetailSearchResult(int arg0, int arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onGetPoiResult(MKPoiResult result, int arg1, int arg2) {
			ArrayList<MKPoiInfo> allPoi =new ArrayList<MKPoiInfo>();
					allPoi=result.getAllPoi();
			if (allPoi == null) {
				mapView.setVisibility(View.GONE);
				imageView.setImageResource(R.drawable.ic_launcher);
			} else {
				imageView.setVisibility(View.GONE);
				// 使用兴趣点覆盖物绘制搜索的结果，显示到地图中
				MyPoiOverlay poiOverlay = new MyPoiOverlay(DiningMapApp.this,
						mapView, mkSearch);
				poiOverlay.setData(allPoi);
				mapView.getOverlays().clear();// 清空所有的覆盖物
				mapView.getOverlays().add(poiOverlay);// 添加绘制的覆盖物
				mapView.refresh();// 刷新

				// for循环绘制每个位置
				for (MKPoiInfo mkPoiInfo : allPoi) {

					if (mkPoiInfo.pt != null) {

						mapView.getController().animateTo(mkPoiInfo.pt);
					}

				}
			}
		}

		@Override
		public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1,
				int arg2) {
			// TODO Auto-generated method stub

		}

		ArrayAdapter<String> sugAdapter = new ArrayAdapter<String>(
				getApplicationContext(),
				android.R.layout.simple_dropdown_item_1line);

		@Override
		public void onGetSuggestionResult(MKSuggestionResult res, int arg1) {
			// TODO Auto-generated method stub
			if (res == null || res.getAllSuggestions() == null) {
				return;
			}
			sugAdapter.clear();
			//EditText editText=(EditText) findViewById(R.id.city);
			//String city = editText.getText()
					//.toString();
			
			/**
			 * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
			 */
			mkSearch.suggestionSearch("杨花椒老火锅", "重庆");
			for (MKSuggestionInfo info : res.getAllSuggestions()) {
				if (info.key != null)
					sugAdapter.add(info.key);
			}
			sugAdapter.notifyDataSetChanged();
			

		}

		@Override
		public void onGetTransitRouteResult(MKTransitRouteResult arg0, int arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onGetWalkingRouteResult(MKWalkingRouteResult arg0, int arg1) {
			// TODO Auto-generated method stub

		}

	}
}
