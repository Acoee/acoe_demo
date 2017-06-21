package com.app.acoe.demo.activity;

import com.app.acoe.demo.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

public class UpdateContactsActivity extends FragmentActivity{
	private TextView txtTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_contacts_activity);
		
		initUI();
	}
	
	private void initUI() {
		txtTitle = (TextView) findViewById(R.id.title_textview);
		txtTitle.setText("添加联系人");
	}
}
