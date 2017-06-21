package com.app.acoe.demo.common;

import android.app.Activity;
import android.util.SparseArray;
import android.view.View;

/**
 * 公共的ViewHolder
 * @author songqy
 * @date 2015-12-1 上午9:18:30
 * @version V2.0.0
 */
@SuppressWarnings("unchecked")
public class ViewHolder {

	/**
	 * 获取子控件(ViewHolder，适用于列表适配器)
	 * @param convertView 父view
	 * @param id 子控件id
	 * @return
	 */
	public static <T extends View> T get(View convertView, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) convertView.getTag();
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			convertView.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = convertView.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (T) childView;
	}
	
	/**
	 * 根据父布局获取子控件
	 * @param parentView 父页面
	 * @param id 子控件id
	 * @return
	 */
	public static <T extends View> T findViewById(View parentView, int id){
		T childView = (T) parentView.findViewById(id);
		return childView;
	}
	
	/**
	 * 根据Activity获取子控件
	 * @param activity 当前页面的Activity
	 * @param id 子控件id
	 * @return
	 */
	public static <T extends View> T findViewById(Activity context, int id){
		T childView = (T) context.findViewById(id);
		return childView;
	}

}
