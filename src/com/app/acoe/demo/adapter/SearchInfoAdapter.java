package com.app.acoe.demo.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.baidu.mapapi.search.core.PoiInfo;

/**
 * @author Acoe
 * @date 2016-4-1
 * @version V1.0.0
 */
public class SearchInfoAdapter extends BaseAdapter {
	private Context context;
	private List<PoiInfo> datas;
	
	public SearchInfoAdapter(Context context, List<PoiInfo> datas) {
		super();
		this.context = context;
		this.datas = datas;
	}

	@Override
	public int getCount() {
		return datas == null ? 0 : datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
