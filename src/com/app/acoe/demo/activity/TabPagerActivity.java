package com.app.acoe.demo.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.app.acoe.demo.R;
import com.app.acoe.demo.adapter.TabPagerAdapter;
import com.viewpagerindicator.TabPageIndicator;

/**
 * @author Acoe
 * @date 2016-3-24
 * @version V1.0.0
 */
public class TabPagerActivity extends FragmentActivity {
	/**控件*/
	private TextView txtTitle;
	private TabPageIndicator indicator;
	private ViewPager viewPager;
	/**适配器*/
	private TabPagerAdapter pagerAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_pager_demo_activity);
		
		initUI();
	}
	
	/**
	 * 初始化界面
	 */
	private void initUI() {
		// 标题
		txtTitle = (TextView) findViewById(R.id.title_textview);
		txtTitle.setText("TabPager");
		// 控件
		indicator = (TabPageIndicator) findViewById(R.id.indicator);
		viewPager = (ViewPager) findViewById(R.id.pager);
		// 设置pager
		viewPager.setOffscreenPageLimit(1);
		pagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(pagerAdapter);
		indicator.setViewPager(viewPager);
	}
}
