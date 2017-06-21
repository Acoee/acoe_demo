package com.app.acoe.demo.activity.baidumap;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.app.acoe.demo.R;
import com.app.acoe.demo.baidu.BDLocationFace;
import com.app.acoe.demo.bean.LocationInfo;
import com.app.acoe.demo.utils.BDLocationUtil;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

/**
 * @author Acoe
 * @date 2016-3-31
 * @version V1.0.0
 */
public class MapSearchActivity extends Activity implements OnClickListener, OnMarkerClickListener {
	private TextView txtTitle;
	private Button btnLocation, btnSearch;
	private MapView mapView;
	private BaiduMap mBaiduMap;
	private BDLocation bdLocation = null;
	private boolean isFirst = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_search_activity);
		
		initUI();
	}

	/**
	 * 初始化界面
	 */
	private void initUI() {
		// 设置标题
		this.txtTitle = (TextView) findViewById(R.id.title_textview);
		this.txtTitle.setText("BaiDuMapSDK");
		// 控件
		mapView = (MapView) findViewById(R.id.map);
		mBaiduMap = mapView.getMap();
		btnLocation = (Button) findViewById(R.id.location);
		btnSearch = (Button) findViewById(R.id.search);
		// 绑定事件
		btnLocation.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		initLocation();
	}
	
	/**
	 * 发起定位请求
	 */
	private void initLocation() {
		new BDLocationUtil(this, new BDLocationFace() {
			@Override
			public void locationResult(BDLocation location) {
				if (isFirst) {
					isFirst = false;
					initLocation();
				} else {
					mBaiduMap.clear();
					bdLocation = location;
					LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
					// 设置地图类型 MAP_TYPE_NORMAL 普通图；MAP_TYPE_SATELLITE 卫星图
					mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
					// 开启交通图
					mBaiduMap.setTrafficEnabled(true);
					MapStatusUpdate statusUpdate = MapStatusUpdateFactory.zoomTo(18);
					mBaiduMap.setMapStatus(statusUpdate);
					BitmapDescriptor descriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
					OverlayOptions overlayOptions = new MarkerOptions().position(latLng).icon(descriptor);
					Marker marker = (Marker) mBaiduMap.addOverlay(overlayOptions);
					// 坐标设置为地图中心
					MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
					mBaiduMap.setMapStatus(u);
					mBaiduMap.setOnMarkerClickListener(MapSearchActivity.this);
					addWindow(marker.getPosition());
				}
			}
		});
	}
	
	/**
	 * 
	 */
	private void addWindow(LatLng latLng) {
		// 生成一个TextView在地图中显示InfoWindow
		TextView tv = new TextView(this);
		tv.setBackgroundResource(R.drawable.shape_popup);
		tv.setPadding(30, 20, 30, 20);
		tv.setText(bdLocation.getAddrStr());
		Point p = mBaiduMap.getProjection().toScreenLocation(latLng);
		p.y -= 47;
		LatLng llInfo = mBaiduMap.getProjection().fromScreenLocation(p);
		InfoWindow mInfoWindow = new InfoWindow(tv, llInfo, -47);
		mBaiduMap.showInfoWindow(mInfoWindow);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.location:
			initLocation();
			break;
		case R.id.search:
			break;
		}
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		addWindow(arg0.getPosition());
		return true;
	}
	
	/**
	 * 将BDLocation对象转换为序列化对象
	 * @param location
	 * @return
	 */
	private LocationInfo getLocationInfo(BDLocation location) {
		LocationInfo info = new LocationInfo();
		info.address = location.getAddrStr();
		info.city = location.getCity();
		info.cityCode = location.getCityCode();
		info.country = location.getCountry();
		info.countryCode = location.getCountryCode();
		info.district = location.getDistrict();
		info.latitude = location.getLatitude();
		info.longitude = location.getLongitude();
		info.province = location.getProvince();
		info.street = location.getStreet();
		info.streetNumber = location.getStreetNumber();
		return info;
	}
}
