package com.app.acoe.demo.activity;

import com.app.acoe.demo.R;
import com.app.acoe.demo.view.RecordButton;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 录音按钮Demo
 * @author Acoe
 * @date 2015-11-27
 * @version V1.0.0
 */
public class RecordButtonActivity extends Activity {
	/**控件**/
	private TextView txtTitle;
	private RecordButton btnRecord;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record_button_demo_activity);
		
		initUI();
	}

	/**
	 * 初始化界面
	 */
	private void initUI() {
		// 设置标题
		this.txtTitle = (TextView) findViewById(R.id.title_textview);
		this.txtTitle.setText("RecordButton");
		// 控件
		this.btnRecord = (RecordButton) findViewById(R.id.record_button);
		//设置语音输入监听
		this.btnRecord.setOnFinishedRecordListener(new RecordButton.OnFinishedRecordListener() {
			@Override
			public void onFinishedRecord(String audioPath, int time) {
				Toast.makeText(getApplicationContext(), "录音路径：" + audioPath + "  duration：" + time, Toast.LENGTH_SHORT).show();
			}
		});
	}

}
