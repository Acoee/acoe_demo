package com.app.acoe.demo.adapter;

import com.app.acoe.demo.fragment.TabPagerFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * @author Acoe
 * @date 2016-3-24
 * @version V1.0.0
 */
public class TabPagerAdapter extends FragmentPagerAdapter {
	private String lables[];

	/**
	 * @param fm
	 */
	public TabPagerAdapter(FragmentManager fm) {
		super(fm);
		this.lables = new String[] { "第一页", "第二页", "第三页" };
	}

	@Override
	public Fragment getItem(int position) {
		return TabPagerFragment.getInstance(position);
	}

	@Override
	public int getCount() {
		return lables == null ? 0 : lables.length;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		return lables[position];
	}

}
