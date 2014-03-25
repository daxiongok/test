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
 * ������ͼ��ʾ
 * 
 * @author Administrator
 * 
 */
public class DiningMapApp extends Activity {
	private MapView mapView;
	private MKSearch mkSearch;// ���������������
	private BMapManager bMapManager;// ��ͼ������
	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		bMapManager = new BMapManager(getApplicationContext());
		// ��ʼ��
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
		// �����������õ����ſؼ�,��ͼ������
		MapController mMapController = mapView.getController();
		// �õ�mMapView�Ŀ���Ȩ,�����������ƺ�����ƽ�ƺ�����
		// ȷ����ͼ��ĳ�㣬γ�Ⱥ;��ȡ�

		GeoPoint point = new GeoPoint((int) (29.982641 * 1E6),
				(int) (106.281157 * 1E6));
		// �ø����ľ�γ�ȹ���һ��GeoPoint����λ��΢�� (�� * 1E6)
		mMapController.setCenter(point);// ���õ�ͼ���ĵ�
		mMapController.setZoom(15);// ���õ�ͼzoom����,����ͼ���������ñ�����
		mapView.setTraffic(true); // ����Ϊ��ͨ��ͼ
		
		mkSearch.poiSearchInCity("����", "����ϻ��");
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
				// ʹ����Ȥ�㸲������������Ľ������ʾ����ͼ��
				MyPoiOverlay poiOverlay = new MyPoiOverlay(DiningMapApp.this,
						mapView, mkSearch);
				poiOverlay.setData(allPoi);
				mapView.getOverlays().clear();// ������еĸ�����
				mapView.getOverlays().add(poiOverlay);// ��ӻ��Ƶĸ�����
				mapView.refresh();// ˢ��

				// forѭ������ÿ��λ��
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
			 * ʹ�ý������������ȡ�����б������onSuggestionResult()�и���
			 */
			mkSearch.suggestionSearch("����ϻ��", "����");
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
