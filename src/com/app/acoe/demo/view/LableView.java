package com.app.acoe.demo.view;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.acoe.demo.R;

public class LableView extends LinearLayout {
	public final static int TYPE_0 = 0;
	public final static int TYPE_1 = 1;
	private ArrayList<String> lables;
	private Context context;
	private LayoutInflater inflater;
	private int lableCount; // 当前标签个数
	private int lableItemResourceId;
	private int width; // 控件宽度
	private int curLineWidth; // 当前行容器布局内控件总宽度
	private LinearLayout latestRowLayout;
	private Paint paint;
	private int type = 0; // 控件类型，0 默认类型，控件内标签单击显示可删除按钮；1 控件内标签单击直接删除标签

	public LableView(Context context) {
		super(context);
	}
	
	public LableView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public LableView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public LableView(Context context, ArrayList<String> lables, int width) {
		super(context);
		this.lables = lables;
		this.width = width;
		init(context);
	}
	
	public LableView(Context context, ArrayList<String> lables, int width, int type) {
		super(context);
		this.lables = lables;
		this.width = width;
		this.type = type;
		init(context);
	}
	
	private void init(Context context) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.setLayoutParams( new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT
		));
		this.setOrientation(VERTICAL);
		this.lableCount = 0;
		this.lableItemResourceId = R.layout.lable_item_view;
		this.curLineWidth = 0; 
		this.paint = new Paint();
		paint.setTextSize(getResources().getDimensionPixelSize(R.dimen.dimen_12)); // 设置画笔尺寸为textview的字体大小，方便后边计算文本宽度
		addLables(lables);
	}
	
	/**
	 * 重新添加所有标签
	 * @param lables
	 */
	private void reAddLables(ArrayList<String> lables) {
		this.removeAllViews();
		this.lableCount = 0;
		this.curLineWidth = 0;
		if (lables == null || lables.size() == 0) return;
		for (int i = 0; i < lables.size();i ++) {
			addLable(i);
		}
	}
	
	/**
	 * 批量添加标签
	 * @param lables
	 */
	private void addLables(ArrayList<String> lables) {
		if (lables == null || lables.size() == 0) return;
		for (int i = 0; i < lables.size();i ++) {
			addLable(i);
		}
	}
	
	/**
	 * 添加单个标签
	 * @param index
	 */
	private void addLable(final int index) {
		final String lable = lables.get(index);
		LinearLayout lableLayout = (LinearLayout) inflater.inflate(lableItemResourceId, null); // 单个标签的布局 
		int lableWidth = (int) paint.measureText(lable)  + getResources().getDimensionPixelSize(R.dimen.dimen_27); // 单个标签宽度为文本宽度、删除按钮宽度和textview内边距之和
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(lableWidth,
	    		LinearLayout.LayoutParams.WRAP_CONTENT);
	    params.setMargins(10, 10, 0, 10); // 设置标签的外边距
		if (lableCount == 0) { // 一个标签都没有时的情况
			LinearLayout colLayout = createSubLinearLayout(); // 装载标签的线性布局，横向铺满
			TextView txtLable = (TextView) lableLayout.findViewById(R.id.lable_item_textview);
			final ImageView imgDel = (ImageView) lableLayout.findViewById(R.id.lable_item_delete_imageview);
			txtLable.setText(lable);
			imgDel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					lables.remove(index);
					reAddLables(lables);
					if (lableChangedListener != null) lableChangedListener.lableRemoved(lable);
				}
			});
			lableLayout.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					if (type == TYPE_1) {
						lables.remove(index);
						reAddLables(lables);
						if (lableChangedListener != null) lableChangedListener.lableRemoved(lable);
					} else {
						if(imgDel.getVisibility() == View.GONE){
							imgDel.setVisibility(View.VISIBLE);
						}else{
							imgDel.setVisibility(View.GONE);
						}
					}
				}
			});
			lableLayout.setLayoutParams(params);
			colLayout.addView(lableLayout);
			curLineWidth += lableWidth + 10;
			this.addView(colLayout);
		} else {
			if (width - curLineWidth > lableWidth) { // 如果当前行有足够空间
				TextView txtLable = (TextView) lableLayout.findViewById(R.id.lable_item_textview);
				final ImageView imgDel = (ImageView) lableLayout.findViewById(R.id.lable_item_delete_imageview);
				txtLable.setText(lable);
				imgDel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						lables.remove(index);
						reAddLables(lables);
						if (lableChangedListener != null) lableChangedListener.lableRemoved(lable);
					}
				});
				lableLayout.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						if (type == TYPE_1) {
							lables.remove(index);
							reAddLables(lables);
							if (lableChangedListener != null) lableChangedListener.lableRemoved(lable);
						} else {
							if(imgDel.getVisibility() == View.GONE){
								imgDel.setVisibility(View.VISIBLE);
							}else{
								imgDel.setVisibility(View.GONE);
							}
						}
					}
				});
				curLineWidth += lableWidth + 10;
				lableLayout.setLayoutParams(params);
				latestRowLayout.addView(lableLayout);
			} else { // 当前行没有足够空间，需要换行
				LinearLayout colLayout = createSubLinearLayout(); // 装载标签的线性布局，横向铺满
				TextView txtLable = (TextView) lableLayout.findViewById(R.id.lable_item_textview);
				final ImageView imgDel = (ImageView) lableLayout.findViewById(R.id.lable_item_delete_imageview);
				txtLable.setText(lable);
				imgDel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						lables.remove(index);
						reAddLables(lables);
						if (lableChangedListener != null) lableChangedListener.lableRemoved(lable);
					}
				});
				lableLayout.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						if (type == TYPE_1) {
							lables.remove(index);
							reAddLables(lables);
							lableCount--;
							if (lableChangedListener != null) lableChangedListener.lableRemoved(lable);
						} else {
							if(imgDel.getVisibility() == View.GONE){
								imgDel.setVisibility(View.VISIBLE);
							}else{
								imgDel.setVisibility(View.GONE);
							}
						}
					}
				});
				lableLayout.setLayoutParams(params);
				colLayout.addView(lableLayout);
				curLineWidth += lableWidth + 10;
				this.addView(colLayout);
			}
		}
		lableCount++;
	}
	
	/**
	 * 新建一行:新建一个LinearLayout
	 * @return
	 */
	private LinearLayout createSubLinearLayout(){
		LinearLayout layout = new LinearLayout(context);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		layout.setLayoutParams(params);
		layout.setGravity(Gravity.CENTER_VERTICAL);
		latestRowLayout = layout;
		curLineWidth = 0;
		return layout;
	}
	
	/**
	 * 获取标签内容
	 * @return 返回列表
	 */
	public ArrayList<String> getLables() {
		return lables;
	}
	
	/**
	 * 添加标签
	 * @param lable
	 */
	public void addLable(String lable) {
		lables.add(lable);
		addLable(lables.size() - 1);
		Log.i("LableView", "addLable() -> lables.size(): " + lables.size());
	}
	
	/**
	 * 绑定标签移除监听事件
	 * @param lableChangedListener
	 */
	public void setLableChangedListener(LableChangedListener lableChangedListener) {
		this.lableChangedListener = lableChangedListener;
	}
	
	/**
	 * 标签移除监听事件
	 */
	private LableChangedListener lableChangedListener;
	public interface LableChangedListener{
		public void lableRemoved(String lable);
	}
	
}
