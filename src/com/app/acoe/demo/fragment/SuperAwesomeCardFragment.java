/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.app.acoe.demo.fragment;

import java.util.ArrayList;
import java.util.Date;

import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.CallLog.Calls;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.Telephony.Sms;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.app.acoe.demo.R;
import com.app.acoe.demo.view.LableView;
import com.app.acoe.demo.view.LableView.LableChangedListener;

public class SuperAwesomeCardFragment extends Fragment {

	private static final String ARG_POSITION = "position";
	private LableView lableView1, lableView;
	private ArrayList<String> lables, lables1;
	private int position;

	public static SuperAwesomeCardFragment newInstance(int position) {
		SuperAwesomeCardFragment f = new SuperAwesomeCardFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		position = getArguments().getInt(ARG_POSITION);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		LinearLayout ll = new LinearLayout(getActivity());
		ll.setLayoutParams(params);
		ll.setOrientation(LinearLayout.VERTICAL);
		ListView list = new ListView(this.getActivity());
		ArrayList<String> datas = new ArrayList<String>();
		String[] columns;
		Cursor cur;
		ArrayAdapter adapter;
		switch (position) {
		case 0:
			columns = new String[] { Phone.CONTACT_ID, Phone.DISPLAY_NAME, Phone.NUMBER,  
	                Phone.PHOTO_ID };
		    Uri mContacts = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		    cur = this.getActivity().getContentResolver().query(
		    		mContacts,
		    		columns,  // 要返回的数据字段
		    		null,          // WHERE子句
		    		null,         // WHERE 子句的参数
		    		null         // Order-by子句
		     );
		    String contactId;
		    String name = null;
	    	String phoneNo = null;
		    while (cur.moveToNext()) {
		    	contactId = cur.getString(cur.getColumnIndex(Phone.CONTACT_ID));
		    	name = cur.getString(cur.getColumnIndex(Phone.DISPLAY_NAME));
		    	phoneNo = cur.getString(cur.getColumnIndex(Phone.NUMBER));
		    	datas.add("Name: " + name + "    phoneNo: " + phoneNo);
		    }
		    cur.close();
			adapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_list_item_1, datas);
			list.setAdapter(adapter);
			ll.addView(list);
			break;
		case 1:
			columns = new String[] { CallLog.Calls.CACHED_NAME, CallLog.Calls.NUMBER,
					CallLog.Calls.TYPE, CallLog.Calls.DATE, CallLog.Calls.DURATION };
		    Uri callLog = CallLog.Calls.CONTENT_URI;
		    cur = this.getActivity().getContentResolver().query(
		    		callLog,
		    		columns,  // 要返回的数据字段
		    		null,          // WHERE子句
		    		null,         // WHERE 子句的参数
		    		null         // Order-by子句
		     );
		    String contactsName = null;
		    String contactsNum = null;
	    	String callType = null;
	    	String callDate = null;
	    	String callDuration = null;
		    while (cur.moveToNext()) {
		    	contactsName = cur.getString(cur.getColumnIndex(Calls.CACHED_NAME));
		    	contactsNum = cur.getString(cur.getColumnIndex(Calls.NUMBER));
		    	callType = cur.getString(cur.getColumnIndex(Calls.TYPE));
		    	callDate = cur.getString(cur.getColumnIndex(Calls.DATE));
		    	callDuration = cur.getString(cur.getColumnIndex(Calls.DURATION));
		    	datas.add((contactsName == null ? "": contactsName) + "  " + contactsNum + "  " + ("1".equals(callType) ? "来电":"拨出") +
		    			"  " + DateFormat.format("yyyy-MM-dd hh:mm:ss", new Date(Long.parseLong(callDate))) + "  " + callDuration);
		    }
		    cur.close();
			adapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_list_item_1, datas);
			list.setAdapter(adapter);
			ll.addView(list);
			break;
		case 2:
			columns = new String[] { Sms.ADDRESS, Sms.PERSON, Sms.DATE, Sms.BODY };
			Uri sms = Uri.parse("content://sms/");
			cur = this.getActivity().getContentResolver().query(
					sms,
		    		columns,  // 要返回的数据字段
		    		null,          // WHERE子句
		    		null,         // WHERE 子句的参数
		    		null         // Order-by子句
		     );
			String address = null;
			String person = null;
			String date = null;
//			String count = null;
//			String snippet = null;
			String body = null;
			while (cur.moveToNext()) {
				address = cur.getString(cur.getColumnIndex(Sms.ADDRESS));
				person = cur.getString(cur.getColumnIndex(Sms.PERSON));
				date = cur.getString(cur.getColumnIndex(Sms.DATE));
//				count = cur.getString(cur.getColumnIndex("msg_count"));
//				snippet = cur.getString(cur.getColumnIndex("snippet"));
				body = cur.getString(cur.getColumnIndex(Sms.BODY));
				datas.add(address + "  " + person + "  " + DateFormat.format("yyyy-MM-dd hh:mm:ss", new Date(Long.parseLong(date))) +
						"  " + body);
			}
			cur.close();
			adapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_list_item_1, datas);
			list.setAdapter(adapter);
			ll.addView(list);
			break;
		case 3:
			LinearLayout lableLl = new LinearLayout(this.getActivity());
			lableLl.setLayoutParams(params);
			lableLl.setOrientation(LinearLayout.VERTICAL);
			lableLl.setBackgroundColor(Color.WHITE);
			TextView txtMyLables = new TextView(this.getActivity());
			LayoutParams txtParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			txtParams.setMargins(10, 10, 0, 10);
			txtMyLables.setLayoutParams(txtParams);
			txtMyLables.setText("我的标签");
			lableLl.addView(txtMyLables);
			lables = new ArrayList<String>();
			lables.add("PHPT");
			lables.add("INUY");
			lables.add("浙江石化");
			lables.add("光环石化");
			lables.add("那能要化工");
			lables.add("ANYUO");
			lables.add("BGHRS");
			lables.add("华能石油工业");
			lables.add("建海精工长业实发集团");
			lables.add("阿比都司石化");
			lables.add("毗邻亚化工");
			lables.add("BVBFRSA");
			lables.add("澄海精工长业实发集团");
			lableView = new LableView(this.getActivity(), lables, getActivity().getWindowManager().getDefaultDisplay().getWidth());
			lableView.setLableChangedListener(new LableChangedListener() {
				@Override
				public void lableRemoved(String lable) {
					lableView1.addLable(lable);
					Log.i("Super", "lableView1 -> lable: " + lable);
				}
			});
			lableLl.addView(lableView);
			TextView txtAddLables = new TextView(this.getActivity());
			LayoutParams txtAddParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			txtAddParams.setMargins(10, 30, 0, 10);
			txtAddLables.setLayoutParams(txtAddParams);
			txtAddLables.setText("点击添加标签");
			lableLl.addView(txtAddLables);
			lables1 = new ArrayList<String>();
			lables1.add("QWER");
			lables1.add("SGDS");
			lables1.add("GSDG");
			lables1.add("BSFG");
			lables1.add("KNFKSJBJI");
			lables1.add("SGGSSAF");
			lables1.add("GSGSGSG");
			lables1.add("石油工业");
			lables1.add("SGSGG");
			lables1.add("SGSG");
			lables1.add("SGSGGS");
			lables1.add("GBHSF");
			lables1.add("POUHNNMKS");
			ArrayList<String> listStr1 = new ArrayList<String>();
			listStr1.addAll(lables1);
			lableView1 = new LableView(this.getActivity(), listStr1, getActivity().getWindowManager().getDefaultDisplay().getWidth(), LableView.TYPE_1);
			lableView1.setLableChangedListener(new LableChangedListener() {
				@Override
				public void lableRemoved(String lable) {
					lableView.addLable(lable);
					Log.i("Super", "lableView -> lable: " + lable);
				}
			});
			lableLl.addView(lableView1);
			ll.addView(lableLl);
			break;
		case 4:
		case 5:
		case 6:
		case 7:
			final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources()
					.getDisplayMetrics());

			TextView v = new TextView(getActivity());
			params.setMargins(margin, margin, margin, margin);
			v.setLayoutParams(params);
			v.setLayoutParams(params);
			v.setGravity(Gravity.CENTER);
			v.setBackgroundResource(R.drawable.background_card);
			v.setText("CARD " + (position + 1));

			ll.addView(v);
			break;
		}
		return ll;
	}

}