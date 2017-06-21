package com.app.acoe.demo.activity;

import com.app.acoe.demo.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * @author Acoe
 * @date 2016-7-12
 * @version V1.0.0
 */
public class CouponBgActivity extends Activity {
	private TextView txtTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coupon_bg_demo_activity);
		
		initUI();
	}
	
	/**
	 * 
	 */
	private void initUI() {
		// 设置标题
		this.txtTitle = (TextView) findViewById(R.id.title_textview);
		this.txtTitle.setText("CouponBg");
	}
}
