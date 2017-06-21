package com.app.acoe.demo.activity;

import com.app.acoe.demo.R;
import com.app.acoe.demo.fragment.SuperAwesomeCardFragment;
import com.app.acoe.demo.view.PagerSlidingTabStrip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {
	private Button btnAdd;
	private TextView txtTitle;
	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private MyPagerAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		initUI();
	}
	
	private void initUI() {
		btnAdd = (Button) findViewById(R.id.title_right_button);
		txtTitle = (TextView) findViewById(R.id.title_textview);
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		pager = (ViewPager) findViewById(R.id.pager);
		adapter = new MyPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);
		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
				.getDisplayMetrics());
		pager.setPageMargin(pageMargin);
		txtTitle.setText("Demo display");
		tabs.setViewPager(pager);
		btnAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, UpdateContactsActivity.class);
				startActivity(intent);
			}
		});
	}
	
	public class MyPagerAdapter extends FragmentPagerAdapter {

		private final String[] TITLES = { "Contacts", "Call Log", "SMS", "LableView", "Top Grossing", "Top New Paid",
				"Top New Free", "Trending" };

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}

		@Override
		public Fragment getItem(int position) {
			Log.i(this.getClass().getName(), position + "");
			if (position == 0) {
				btnAdd.setVisibility(View.VISIBLE);
				
			} else {
				btnAdd.setVisibility(View.GONE);
			}
			return SuperAwesomeCardFragment.newInstance(position);
		}
	}
}
