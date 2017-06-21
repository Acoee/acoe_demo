package com.app.acoe.demo.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.app.acoe.demo.R;
import com.app.acoe.demo.adapter.DemoListAdapter;
import com.app.acoe.demo.bean.DemoBean;

public class DemoMainActivity extends Activity {
	private TextView txtTitle;
	private ListView listView;
	private DemoListAdapter adapter;
	private ArrayList<DemoBean> datas;
	private ArrayList<Intent> intents;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo_main_activity);
		
		initUI();
	}
	
	/**
	 * 界面初始化
	 */
	private void initUI() {
		// 设置标题
		this.txtTitle = (TextView) findViewById(R.id.title_textview);
		this.txtTitle.setText("Demo展示");
		// 初始化控件
		this.listView = (ListView) findViewById(R.id.demo_list_view);
		this.datas = new ArrayList<DemoBean>();
		initData();
		this.adapter = new DemoListAdapter(this.getApplicationContext(), datas);
		this.listView.setAdapter(adapter);
		this.listView.setOnItemClickListener(itemClickListener);
	}
	
	/**
	 * 初始化数据
	 */
	private void initData() {
		datas = new ArrayList<DemoBean>();
		intents = new ArrayList<Intent>();
		// 录音按钮demo
		DemoBean item1 = new DemoBean();
		item1.name = "RecordButton";
		item1.desc = "录音按钮demo";
		datas.add(item1);
		Intent recordButtonIntent = new Intent(getApplicationContext(), RecordButtonActivity.class);
		intents.add(recordButtonIntent);
		// 音乐播放器播放模式按钮
		DemoBean item2 = new DemoBean();
		item2.name = "PlayModeButton";
		item2.desc = "音乐播放器播放模式按钮";
		datas.add(item2);
		Intent playModeIntent = new Intent(getApplicationContext(), PlayModeButtonActivity.class);
		intents.add(playModeIntent);
		// 文字上下滚动轮播效果
		DemoBean item3 = new DemoBean();
		item3.name = "TextSwitcher";
		item3.desc = "文字上下滚动轮播效果";
		datas.add(item3);
		Intent switcherIntent = new Intent(getApplicationContext(), TextSwitcherActivity.class);
		intents.add(switcherIntent);
		// 标签滑动界面
		DemoBean item4 = new DemoBean();
		item4.name = "TabPager";
		item4.desc = "标签滑动界面";
		datas.add(item4);
		Intent pagerIntent = new Intent(getApplicationContext(), TabPagerActivity.class);
		intents.add(pagerIntent);
		// 百度地图SDK示例
		DemoBean item5 = new DemoBean();
		item5.name = "BaiDuMapSDK";
		item5.desc = "百度地图SDK示例";
		datas.add(item5);
		Intent mapIntent = new Intent(getApplicationContext(), BaiduMapActivity.class);
		intents.add(mapIntent);
		// 优惠券背景示例
		DemoBean item6 = new DemoBean();
		item6.name = "CouponBg";
		item6.desc = "优惠券背景示例";
		datas.add(item6);
		Intent couponIntent = new Intent(getApplicationContext(), CouponBgActivity.class);
		intents.add(couponIntent);
	}
	
	private OnItemClickListener itemClickListener = new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> arg0, View parent, int position,
				long id) {
			startActivity(intents.get(position));
		}
	};
	
}
