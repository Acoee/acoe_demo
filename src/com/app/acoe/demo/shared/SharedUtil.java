package com.app.acoe.demo.shared;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Acoe
 * @date 2016-3-29
 * @version V1.0.0
 */
public class SharedUtil {
	private SharedPreferences sharedMap;
	
	public SharedUtil(Context context) {
		super();
		if (sharedMap == null) {
			sharedMap = context.getSharedPreferences(SharedConstants.MAP_NAME, Context.MODE_PRIVATE);
		}
	}
	
	public String getValue(String key) {
		return sharedMap.getString(key, "");
	}
	
	public void save(String key, String value) {
		sharedMap.edit().putString(key, value).commit();
	}
}
