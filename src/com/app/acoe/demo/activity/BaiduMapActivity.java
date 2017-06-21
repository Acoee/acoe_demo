package com.app.acoe.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.app.acoe.demo.R;
import com.app.acoe.demo.activity.baidumap.MapLocationActivity;
import com.app.acoe.demo.activity.baidumap.MapMarkerActivity;
import com.app.acoe.demo.activity.baidumap.MapSearchActivity;

/**
 * @author Acoe
 * @date 2016-3-29
 * @version V1.0.0
 */
public class BaiduMapActivity extends Activity implements OnClickListener {
	private TextView txtTitle;
	private Button btn1, btn2, btn3, btn4, btn5;
	private Button[] btns = new Button[] { btn1, btn2, btn3, btn4, btn5 };
	private int ids[] = new int[] { R.id.map_location_button, R.id.map_marker_button,
			R.id.map_path_button, R.id.map_guide_button, R.id.map_scenery_button };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.baidu_map_demo_activity);
		
		initUI();
	}
	
	/**
	 * 初始化界面
	 */
	private void initUI() {
		// 设置标题
		this.txtTitle = (TextView) findViewById(R.id.title_textview);
		this.txtTitle.setText("BaiDuMapSDK");
		// 控件、绑定事件
		for (int i = 0; i < btns.length; i++) {
			btns[i] = (Button) findViewById(ids[i]);
			btns[i].setOnClickListener(this);
		}
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.map_location_button:
			Intent locationIntent = new Intent(getApplicationContext(), MapLocationActivity.class);
			startActivity(locationIntent);
			break;
		case R.id.map_marker_button:
			Intent markerIntent = new Intent(getApplicationContext(), MapMarkerActivity.class);
			startActivity(markerIntent);
			break;
		case R.id.map_path_button:
			Intent searchIntent = new Intent(getApplicationContext(), MapSearchActivity.class);
			startActivity(searchIntent);
			break;
		case R.id.map_guide_button:
			break;
		case R.id.map_scenery_button:
			break;
		}
	}
}
