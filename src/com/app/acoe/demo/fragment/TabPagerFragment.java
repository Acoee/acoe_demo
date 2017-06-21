package com.app.acoe.demo.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.acoe.demo.R;

/**
 * @author Acoe
 * @date 2016-3-24
 * @version V1.0.0
 */
public class TabPagerFragment extends Fragment {
	private int position;
	private View nowView;
	private TextView txtView;
	
	private TabPagerFragment() {
	}
	
	public static TabPagerFragment getInstance(int position) {
		TabPagerFragment fragment = new TabPagerFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("position", position);
		fragment.setArguments(bundle);
		return fragment;
	}
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		nowView = inflater.inflate(R.layout.pager_fragment, null);
		return nowView;
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initUI();
	}
	
	/**
	 * 初始化控件
	 */
	private void initUI() {
		this.position = getArguments().getInt("position");
		this.txtView = (TextView) nowView.findViewById(R.id.textview);
		this.txtView.setText("第" + (position+1) + "个Fragment");
	}

}
