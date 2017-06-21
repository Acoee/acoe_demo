package com.app.acoe.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

import com.app.acoe.demo.R;

/**
 * @author Acoe
 * @date 2016-3-23
 * @version V1.0.0
 */
public class TextSwitcherActivity extends Activity {
	private TextView txtTitle;
	private TextSwitcher txtSwitcher;
	
	private String[] items;
	private int i = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.text_switcher_demo_activity);
		
		initUI();
	}

	/**
	 * 
	 */
	private void initUI() {
		// 设置标题
		this.txtTitle = (TextView) findViewById(R.id.title_textview);
		this.txtTitle.setText("TextSwitcher");
		// 控件
		txtSwitcher = (TextSwitcher) findViewById(R.id.textswitcher);
		txtSwitcher.setFactory(new ViewFactory() {
			@Override
			public View makeView() {
				TextView tv = new TextView(getApplication());
				tv.setTextSize(getResources().getDimension(R.dimen.dimen_16));
				tv.setTextColor(getResources().getColor(R.color.normal_text_color));
				return tv;
			}
		});
		txtSwitcher.setInAnimation(getApplicationContext(), R.anim.slide_in_bottom);
		txtSwitcher.setOutAnimation(getApplicationContext(), R.anim.slide_out_top);
		items = new String[] { "新春特别活动，楚舆狂歌套限时出售！", "三周年红发效果图放出！", "冬至趣味活动开启，一起来吃冬至宴席。" };
		Message msg = mHandler.obtainMessage(1);
		msg.what = i;
		mHandler.sendMessage(msg);
	}
	
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			txtSwitcher.setText(items[i % items.length]);
			i++;
			Message msgg = mHandler.obtainMessage(1);
			msgg.what = i;
			mHandler.sendMessageDelayed(msgg, 3000);
		}
	};
}
