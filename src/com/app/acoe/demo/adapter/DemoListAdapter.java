package com.app.acoe.demo.adapter;

import java.util.ArrayList;

import com.app.acoe.demo.R;
import com.app.acoe.demo.bean.DemoBean;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DemoListAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<DemoBean> datas;

	public DemoListAdapter(Context context, ArrayList<DemoBean> datas) {
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
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.demo_activity_item_view, null);
			holder.txtName = (TextView) convertView.findViewById(R.id.demo_activity_item_name_textview);
			holder.txtDes = (TextView) convertView.findViewById(R.id.demo_activity_item_content_textview);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		DemoBean item = datas.get(position);
		if (item != null) {
			holder.txtName.setText(item.name);
			holder.txtDes.setText(item.desc);
		}
		return convertView;
	}

	class ViewHolder {
		TextView txtName, txtDes;
	}
}
