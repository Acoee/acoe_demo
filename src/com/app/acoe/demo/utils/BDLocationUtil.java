package com.app.acoe.demo.utils;

import android.content.Context;

import com.app.acoe.demo.baidu.BDLocationFace;
import com.app.acoe.demo.shared.SharedConstants;
import com.app.acoe.demo.shared.SharedUtil;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

/**
 * @author Acoe
 * @date 2016-3-29
 * @version V1.0.0
 */
public class BDLocationUtil implements BDLocationListener {
	
	private BDLocationFace locationFace;
	private Context context;
	private LocationClient mLocationClient = null;
	
	public BDLocationUtil(Context context, BDLocationFace locationFace) {
		super();
		this.locationFace = locationFace;
		this.context = context;
		mLocationClient = new LocationClient(context);
		mLocationClient.registerLocationListener(BDLocationUtil.this);
		startLocation();
	}
	
	private void startLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy); // 可选，默认高精度
		option.setCoorType("bd0911"); // 可选，默认gcj02，设置返回的定位结果坐标系
		option.setScanSpan(0); // 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true); // 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true); // 可选，默认false，设置是否使用GPS
		option.setLocationNotify(true); // 可选，默认false，设置是否当GPS有效时按照1s一次频率输出GPS结果
		option.setIsNeedLocationDescribe(true); // 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescrible里得到，结果类似于“在XXX附件”
		option.setIsNeedLocationPoiList(true); // 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		option.setIgnoreKillProcess(false); // 可选，默认false，定位SDK内部是一个Service，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
		option.SetIgnoreCacheException(false); // 可选，默认false，设置是否手机Crash信息，默认收集
		option.setEnableSimulateGps(false); // 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}

	@Override
	public void onReceiveLocation(BDLocation arg0) {
		if (locationFace != null) {
			if (arg0.getLocType() == 61 || arg0.getLocType() == 161 && arg0.getLatitude() != 0.0) {
				new SharedUtil(context).save(SharedConstants.CITY_NAME, arg0.getCity());
				locationFace.locationResult(arg0);
			}
		}
	}

}
