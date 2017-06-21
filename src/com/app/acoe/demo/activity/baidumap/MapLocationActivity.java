package com.app.acoe.demo.activity.baidumap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.app.acoe.demo.R;
import com.app.acoe.demo.baidu.BDLocationFace;
import com.app.acoe.demo.utils.BDLocationUtil;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerDragListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

/**
 * @author Acoe
 * @date 2016-3-29
 * @version V1.0.0
 */
public class MapLocationActivity extends Activity implements OnMarkerClickListener, OnMarkerDragListener, OnGetGeoCoderResultListener {
	private TextView txtTitle;
	private MapView mBaiduMapView; // 地图界面
	private ProgressDialog progressDialog; // 进度条
	private BaiduMap mBaiduMap; // 地图的管理类
	private BDLocation bdLocation; // 定位类
	private GeoCoder geoCoder; // 经纬度地理位置坐标反转类
	private View popView; // 弹框
	private TextView txtPop; // 弹框上的文字

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_location_activity);
		
		initUI();
	}
	
	/**
	 * 初始化界面
	 */
	private void initUI() {
		// 设置标题
		this.txtTitle = (TextView) findViewById(R.id.title_textview);
		this.txtTitle.setText("BaiDuMapSDK");
		mBaiduMapView = (MapView) findViewById(R.id.map);
		mBaiduMap = mBaiduMapView.getMap();
		initLocation();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mBaiduMapView.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mBaiduMapView.onPause();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mBaiduMapView.onDestroy();
	}
	
	/**
	 * 初始化定位 
	 */
	private void initLocation() {
		progressDialog = ProgressDialog.show(MapLocationActivity.this, "定位", "正在定位……");
		new BDLocationUtil(getApplicationContext(), new BDLocationFace() {
			@Override
			public void locationResult(BDLocation location) {
				if (progressDialog != null) {
					progressDialog.dismiss();
				}
				bdLocation = location;
				addMarker(null);
			}
		});
	}
	
	private void addMarker(Marker marker) {
		// 设置地图类型 MAP_TYPE_NORMAL 普通图；MAP_TYPE_SATELLITE 卫星图
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		// 开启交通图
		mBaiduMap.setTrafficEnabled(true);
		// 设置地图当前级别
		MapStatusUpdate statusUpdate = MapStatusUpdateFactory.zoomTo(19);
		mBaiduMap.setMapStatus(statusUpdate);
		// 构建覆盖物的信息
		LatLng latLng = null;
		if (marker == null) {
			latLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
		} else {
			latLng = new LatLng(marker.getPosition().latitude, marker.getPosition().latitude);
		}
		BitmapDescriptor descriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
		OverlayOptions option = new MarkerOptions().position(latLng).icon(descriptor).draggable(true);
		// 清除地图上的所有覆盖物
		mBaiduMap.clear();
		// 将覆盖物添加到地图上
		mBaiduMap.addOverlay(option);
		// 将覆盖物设置为地图中心
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
		// 以动画方式更新地图状态，动画耗时300ms
		mBaiduMap.animateMapStatus(u);
		mBaiduMap.setOnMarkerClickListener(MapLocationActivity.this);
		mBaiduMap.setOnMarkerDragListener(MapLocationActivity.this);
	}
	
	@Override
	public boolean onMarkerClick(Marker arg0) {
		if (geoCoder == null) {
			geoCoder = GeoCoder.newInstance();
			geoCoder.setOnGetGeoCodeResultListener(MapLocationActivity.this);
		}
		geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(arg0.getPosition()));
		return true;
	}

	/**
	 * 覆写覆盖物拖拽过程中的方法
	 */
	@Override
	public void onMarkerDrag(Marker arg0) {
	}

	/**
	 * 覆写覆盖物拖拽结束的方法
	 */
	@Override
	public void onMarkerDragEnd(Marker arg0) {
	}

	/**
	 * 覆写覆盖物拖拽开始的方法
	 */
	@Override
	public void onMarkerDragStart(Marker arg0) {
		mBaiduMap.hideInfoWindow();
	}

	/**
	 * 坐标换算 根据地址得到坐标
	 */
	@Override
	public void onGetGeoCodeResult(GeoCodeResult arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 坐标换算，根据坐标得到地址
	 */
	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) {
		addPopMarker(arg0.getLocation(), arg0.getAddress());
	}
	
	@SuppressLint("InflateParams")
	private void addPopMarker(LatLng pt, String address) {
		// 创建InforWindow展示的view
		if (popView == null) {
			popView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.popup, null);
			txtPop = (TextView) popView.findViewById(R.id.popup_text);
		}
		txtPop.setText(address);
	}
}
