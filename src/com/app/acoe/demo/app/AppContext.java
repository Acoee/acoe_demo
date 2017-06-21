package com.app.acoe.demo.app;

import com.baidu.mapapi.SDKInitializer;

import android.app.Application;

/**
 * @author Acoe
 * @date 2016-3-29
 * @version V1.0.0
 */
public class AppContext extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		
		SDKInitializer.initialize(getApplicationContext());
	}
	
}
