package com.app.acoe.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.acoe.demo.R;

/**
 * @author Acoe
 * @date 2016-3-11
 * @version V1.0.0
 */
public class PlayModeButtonActivity extends Activity implements OnClickListener {
	/**控件**/
	private TextView txtTitle;
	private ImageView imgCenter, imgOrder, imgCycle, imgRepeat, imgRandom;
	/**数据**/
	private int playMode = 0; // 0 列表播放，1 列表循环，2 随机播放，3 单曲循环
	private int[] playModeRes;
	private boolean isExtend = false; // 按钮展开状态
	/**动画**/
	private Animation topToCenter, rightToCenter, bottomToCenter, leftToCenter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.play_mode_demo_activity);
		
		initUI();
	}

	/**
	 * 初始化界面
	 */
	private void initUI() {
		// 设置标题
		this.txtTitle = (TextView) findViewById(R.id.title_textview);
		this.txtTitle.setText("PlayModeButton");
		// 控件
		this.imgCenter = (ImageView) findViewById(R.id.play_mode_center_imageview);
		this.imgOrder = (ImageView) findViewById(R.id.play_order_top_imageview);
		this.imgCycle = (ImageView) findViewById(R.id.play_cycle_right_imageview);
		this.imgRandom = (ImageView) findViewById(R.id.play_random_bottom_imageview);
		this.imgRepeat = (ImageView) findViewById(R.id.play_repeat_left_imageview);
		// 数据
		this.playModeRes = new int[] { R.drawable.play_mode_order_icon, R.drawable.play_mode_cycle_icon,
				R.drawable.play_mode_random_icon, R.drawable.play_mode_repeat_icon };
		// 初始化动画
		initAnim();
		
		// 初始化控件
		this.imgCenter.setBackgroundResource(playModeRes[0]);
		this.imgCenter.setOnClickListener(this);
		this.imgOrder.setOnClickListener(this);
		this.imgCycle.setOnClickListener(this);
		this.imgRepeat.setOnClickListener(this);
		this.imgRandom.setOnClickListener(this);
		this.imgCenter.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					if (isExtend) {
						imgOrder.setVisibility(View.GONE);
						imgCycle.setVisibility(View.GONE);
						imgRepeat.setVisibility(View.GONE);
						imgRandom.setVisibility(View.GONE);
					}
				}
			}
		});
		
	}
	
	/**
	 * 初始化动画
	 */
	private void initAnim() {
		this.topToCenter = new TranslateAnimation( Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1.3f);
		this.topToCenter.setDuration(500);
		this.topToCenter.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				imgOrder.setVisibility(View.GONE);
				imgCycle.setVisibility(View.GONE);
				imgRepeat.setVisibility(View.GONE);
				imgRandom.setVisibility(View.GONE);
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				playMode = 0;
				imgCenter.setBackgroundResource(playModeRes[0]);
			}
		});
		this.rightToCenter = new TranslateAnimation( Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1.3f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
		this.rightToCenter.setDuration(500);
		this.rightToCenter.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				imgOrder.setVisibility(View.GONE);
				imgCycle.setVisibility(View.GONE);
				imgRepeat.setVisibility(View.GONE);
				imgRandom.setVisibility(View.GONE);
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				playMode = 1;
				imgCenter.setBackgroundResource(playModeRes[1]);
			}
		});
		this.bottomToCenter = new TranslateAnimation( Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1.3f);
		this.bottomToCenter.setDuration(500);
		this.bottomToCenter.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				imgOrder.setVisibility(View.GONE);
				imgCycle.setVisibility(View.GONE);
				imgRepeat.setVisibility(View.GONE);
				imgRandom.setVisibility(View.GONE);
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				playMode = 2;
				imgCenter.setBackgroundResource(playModeRes[2]);
			}
		});
		this.leftToCenter = new TranslateAnimation( Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1.3f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -0f);
		this.leftToCenter.setDuration(500);
		this.leftToCenter.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				imgOrder.setVisibility(View.GONE);
				imgCycle.setVisibility(View.GONE);
				imgRepeat.setVisibility(View.GONE);
				imgRandom.setVisibility(View.GONE);
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				playMode = 3;
				imgCenter.setBackgroundResource(playModeRes[3]);
			}
		});
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.play_mode_center_imageview: // 展开播放模式按钮
			if (!isExtend) {
				switch (playMode) {
				case 0:
					imgCycle.setVisibility(View.VISIBLE);
					imgRepeat.setVisibility(View.VISIBLE);
					imgRandom.setVisibility(View.VISIBLE);
					break;
				case 1:
					imgOrder.setVisibility(View.VISIBLE);
					imgRepeat.setVisibility(View.VISIBLE);
					imgRandom.setVisibility(View.VISIBLE);
					break;
				case 2:
					imgOrder.setVisibility(View.VISIBLE);
					imgCycle.setVisibility(View.VISIBLE);
					imgRepeat.setVisibility(View.VISIBLE);
					break;
				case 3:
					imgOrder.setVisibility(View.VISIBLE);
					imgCycle.setVisibility(View.VISIBLE);
					imgRandom.setVisibility(View.VISIBLE);
					break;
				}
				break;
			} else {
				imgOrder.setVisibility(View.GONE);
				imgCycle.setVisibility(View.GONE);
				imgRepeat.setVisibility(View.GONE);
				imgRandom.setVisibility(View.GONE);
			}
			isExtend = !isExtend;
		case R.id.play_order_top_imageview: // 切换为顺序播放
			playMode = 0;
			imgOrder.startAnimation(topToCenter);
			break;
		case R.id.play_cycle_right_imageview: // 切换为循环播放
			playMode = 1;
			imgCycle.startAnimation(rightToCenter);
			break;
		case R.id.play_random_bottom_imageview: // 切换为随机播放
			playMode = 2;
			imgRandom.startAnimation(bottomToCenter);
			break;
		case R.id.play_repeat_left_imageview: // 切换为重复播放
			playMode = 3;
			imgRepeat.startAnimation(leftToCenter);
			break;
		}
	}
	
}
