package com.app.acoe.demo.activity.baidumap;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.app.acoe.demo.R;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

/**
 * @author Acoe
 * @date 2016-3-30
 * @version V1.0.0
 */
public class MapMarkerActivity extends Activity implements OnMapClickListener, OnMarkerClickListener {
	private TextView txtTitle; // 标题
	private MapView mBaiduMapView; // 百度地图控件
	private BaiduMap mBaiduMap; // 百度地图管理类
	private String[] titles = new String[] { "one", "two", "three", "four" };
	private LatLng[] latLngs = new LatLng[] { new LatLng(22.539895, 114.058935), new LatLng(22.540729, 114.066337),
			new LatLng(22.543763, 114.06458), new LatLng(22.538614, 114.062811)};
	
	
	
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
		// 控件
		mBaiduMapView = (MapView) findViewById(R.id.map);
		mBaiduMap = mBaiduMapView.getMap();
		mBaiduMap.setOnMapClickListener(this);
		initMarker();
	}
	
	/**
	 * 初始化地图标记
	 */
	private void initMarker() {
		mBaiduMap.clear(); // 先清除地图上的所有标记
		LatLng latLng = null;
		OverlayOptions overlayOptions = null;
		// 设置地图类型MAP_TYPE_NORMAL 普通图；MAP_TYPE_SATELLITE 卫星图
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		// 开启交通图
		mBaiduMap.setTrafficEnabled(true);
		MapStatusUpdate statusUpdate = MapStatusUpdateFactory.zoomTo(17);
		mBaiduMap.setMapStatus(statusUpdate);
		BitmapDescriptor descriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
		// 循环添加四个覆盖物到地图上
		for (int i = 0; i < titles.length; i++) {
			latLng = latLngs[i];
			overlayOptions = new MarkerOptions().position(latLng).icon(descriptor);
			Marker marker = (Marker) mBaiduMap.addOverlay(overlayOptions);
			Bundle bundle = new Bundle();
			bundle.putString("info", titles[i] + "个");
			marker.setExtraInfo(bundle);
		}
		// 将最后一个坐标设置为地图中心
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
		mBaiduMap.setMapStatus(u);
		mBaiduMap.setOnMarkerClickListener(this);
	}

	@Override
	public void onMapClick(LatLng arg0) {
		mBaiduMap.hideInfoWindow();
	}

	@Override
	public boolean onMapPoiClick(MapPoi arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		final String msg = marker.getExtraInfo().getString("info");
		InfoWindow mInfoWindow;
		// 生成一个TextView用户在地图中显示InfoWindow
		TextView txtLocation = new TextView(getApplicationContext());
		txtLocation.setBackgroundResource(R.drawable.shape_popup);
		txtLocation.setPadding(30, 20, 30, 20);
		txtLocation.setText(msg);
		final LatLng ll = marker.getPosition();
		Point p = mBaiduMap.getProjection().toScreenLocation(ll);
		p.y -= 47;
		LatLng llInfo = mBaiduMap.getProjection().fromScreenLocation(p);
		mInfoWindow = new InfoWindow(txtLocation, llInfo, -47);
		mBaiduMap.showInfoWindow(mInfoWindow);
		txtLocation.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(MapMarkerActivity.this, msg, Toast.LENGTH_SHORT).show();
			}
		});
		return true;
	}
}
