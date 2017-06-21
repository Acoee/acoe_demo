package com.app.acoe.demo.activity.baidumap.search;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.acoe.demo.R;
import com.app.acoe.demo.adapter.SearchInfoAdapter;
import com.app.acoe.demo.baidu.BDLocationFace;
import com.app.acoe.demo.bean.LocationInfo;
import com.app.acoe.demo.shared.SharedConstants;
import com.app.acoe.demo.shared.SharedUtil;
import com.app.acoe.demo.utils.BDLocationUtil;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;

/**
 * @author Acoe
 * @date 2016-4-1
 * @version V1.0.0
 */
public class SearchInfoActivity extends Activity implements OnClickListener, OnGetPoiSearchResultListener, OnGetRoutePlanResultListener {
	private TextView txtTitle; // 标题
	private Button btnBus, btnDriver, btnWalk; // 公交、驾车、步行按钮
	private EditText edtOrigin, edtEnd; // 出发点和目的地输入框
	private ProgressBar proOrigin, proEnd;
	private ListView lsv; // 用来显示兴趣点的列表
	private ProgressDialog dialog;
	
	private int currentId; // 当前输入框控件id（区分起点和终点的EditText）
	private LocationInfo mLocationInfo; // 我的位置信息
	
	private List<PoiInfo> datas;
	private SearchInfoAdapter adapter;
	// 使用经纬度查询路线
	private LatLng originLat;
	private LatLng endLat;
	
	private PoiSearch poiSearch; // 兴趣点检索类
	private RoutePlanSearch routeSearch; // 线路检索类
	
	private int getLocationCount = 0; // 定位发起的次数，超过三次就抛出网络异常
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_info_activity);
		
		initUI();
		initParams();
	}
	
	/**
	 * 初始化界面
	 */
	private void initUI() {
		// 设置标题
		txtTitle = (TextView) findViewById(R.id.title_textview);
		txtTitle.setText("路径搜索");
		// 控件
		btnBus = (Button) findViewById(R.id.bus_button);
		btnDriver = (Button) findViewById(R.id.driver_button);
		btnWalk = (Button) findViewById(R.id.walk_button);
		edtOrigin = (EditText) findViewById(R.id.origin_edittext);
		edtEnd = (EditText) findViewById(R.id.end_edittext);
		proOrigin = (ProgressBar) findViewById(R.id.origin_progressbar);
		proEnd = (ProgressBar) findViewById(R.id.end_progressbar);
		lsv = (ListView) findViewById(R.id.info_listview);
		// 绑定点击事件
		btnBus.setOnClickListener(this);
		btnDriver.setOnClickListener(this);
		btnWalk.setOnClickListener(this);
		lsv.setOnItemClickListener(itemClickListener);
	}
	
	/**
	 * 初始化数据
	 */
	private void initParams() {
		poiSearch = PoiSearch.newInstance();
		poiSearch.setOnGetPoiSearchResultListener(this);
		routeSearch = RoutePlanSearch.newInstance();
		routeSearch.setOnGetRoutePlanResultListener(this);
		mLocationInfo = (LocationInfo) getIntent().getSerializableExtra("locationInfo");
		datas = new ArrayList<PoiInfo>();
		adapter = new SearchInfoAdapter(this, datas);
		lsv.setAdapter(adapter);
	}
	
	@Override
	public void onClick(View v) {
		if (!TextUtils.isEmpty(edtOrigin.getText().toString()) &&
				!TextUtils.isEmpty(edtEnd.getText().toString())) {
			if (dialog == null) {
				dialog = new ProgressDialog(getApplicationContext());
				dialog.setTitle("线路查询");
				dialog.setMessage("正在查询所有线路");
			}
			dialog.show();
			switch (v.getId()) {
			case R.id.bus_button:
				startRouter(1, originLat, endLat);
				break;
			case R.id.driver_button:
				startRouter(2, originLat, endLat);
				break;
			case R.id.walk_button:
				startRouter(3, originLat, endLat);
				break;
			}
		} else {
			Toast.makeText(getApplicationContext(), "起始点或终点不能为空", Toast.LENGTH_SHORT).show();
		}
	}

	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			if (currentId == R.id.origin_edittext) {
				edtOrigin.setText(datas.get(position).address);
				edtOrigin.setSelection(edtOrigin.getText().toString().length());
				originLat = datas.get(position).location;
			} else {
				edtEnd.setText(datas.get(position).address);
				edtEnd.setSelection(edtEnd.getText().toString().length());
				endLat = datas.get(position).location;
			}
		}
		
	};
	
	/**
	 * 获取地点详情页检索结果
	 */
	@Override
	public void onGetPoiDetailResult(PoiDetailResult arg0) {
	}

	/**
	 * 获取Poi检索结果
	 */
	@Override
	public void onGetPoiResult(PoiResult result) {
		proOrigin.setVisibility(View.VISIBLE);
		proEnd.setVisibility(View.VISIBLE);
		// 得到检索结果的集合，有时候检索结果返回的是空数据
		if (result.getAllPoi() != null && result.getAllPoi().size() > 0) {
			datas.clear();
			datas.addAll(result.getAllPoi());
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onGetBikingRouteResult(BikingRouteResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetDrivingRouteResult(DrivingRouteResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetTransitRouteResult(TransitRouteResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetWalkingRouteResult(WalkingRouteResult arg0) {
		// TODO Auto-generated method stub
		
	}
	
	class MyTextWatcher implements TextWatcher {
		private int txtId; 
		
		public MyTextWatcher(int txtId) {
			super();
			this.txtId = txtId;
		}

		@Override
		public void afterTextChanged(Editable s) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			if (txtId == R.id.origin_edittext) {
				currentId = R.id.origin_edittext;
				proOrigin.setVisibility(View.VISIBLE);
				proEnd.setVisibility(View.INVISIBLE);
			} else {
				currentId = R.id.end_edittext;
				proOrigin.setVisibility(View.INVISIBLE);
				proEnd.setVisibility(View.VISIBLE);
			}
			// 发起检索，此处调用的呃是城市内检索（还有范围内检索/周边检索POI 详情检索三个方法，根据不同的需求来调用）
			if (!TextUtils.isEmpty(s.toString())) {
				PoiCitySearchOption pCityOption = new PoiCitySearchOption();
				pCityOption.city(new SharedUtil(getApplicationContext()).getValue(SharedConstants.CITY_NAME));
				pCityOption.keyword(s.toString());
				pCityOption.pageNum(10);
				pCityOption.pageCapacity(50);
				poiSearch.searchInCity(pCityOption);
			}
		}
		
	}
	
	/**
	 * 通过经纬度进行路线查询
	 * @param type
	 * @param originLat
	 * @param endLat
	 */
	private void startRouter(final int type, LatLng originLat, final LatLng endLat) {
		String beginAddr = edtOrigin.getText().toString();
		if (beginAddr.equals("我的位置")) {
			if (mLocationInfo != null) {
				originLat = new LatLng(mLocationInfo.latitude, mLocationInfo.longitude);
				startRouterResult(type, originLat, endLat);
			} else {
				getLocationCount++;
				new BDLocationUtil(this, new BDLocationFace() {
					@Override
					public void locationResult(BDLocation location) {
						if (location != null && location.hasAddr()) {
							startRouterResult(type, new LatLng(location.getLatitude(), location.getLongitude()), endLat);
						} else {
							if (getLocationCount > 2) {
								if (dialog != null) dialog.dismiss();
								Toast.makeText(getApplicationContext(), "获取地理位置失败，请检查网络", Toast.LENGTH_SHORT).show();
							} else {
								startRouter(type, null, endLat);
							}
						}
					}
				});
			}
		} else {
			startRouter(type, originLat, endLat);
		}
	}
	
	/***
	 * 此处应该判断传递过来的经纬度是不是空的，因为有可能不是在listInfo集合里面取出来的数据，如果为空，就要根据控件上的文字，进行坐标反查
	 * ，得到坐标，然后再调用这个方法 ||如果经纬度为空，则用地址信息来进行线路的查询，不过此时查询出来的结果可能为空
	 **/
	private void startRouterResult(final int type, LatLng originLat, LatLng endLat) {
		if (originLat != null && endLat != null) {
			String cityName = new SharedUtil(getApplicationContext()).getValue(SharedConstants.CITY_NAME);
			PlanNode originNode = PlanNode.withLocation(originLat);
			PlanNode endNode = PlanNode.withLocation(endLat);
			if (type == 1) {
				routeSearch.transitSearch(new TransitRoutePlanOption().from(originNode).to(endNode).city(cityName));
			} else if (type == 2) {
				routeSearch.drivingSearch(new DrivingRoutePlanOption().from(originNode).to(endNode));
			} else if (type == 3) {
				routeSearch.walkingSearch(new WalkingRoutePlanOption().from(originNode).to(endNode));
			}
		} else {
			startRouterResult(type, edtOrigin.getText().toString(), edtEnd.getText().toString());
		}
	}
	
	/**
	 * 此处查询是根据城市名称以及出行点名称查询，只在经纬度查询不可用时采用，毕竟名称查询容易有歧义
	 * @param type 1的时候表示是公交查询，2表示驾车查询，3表示走路查询
	 * @param originAddr 起点
	 * @param endAddr 终点
	 */
	private void startRouterResult(int type, String originAddr, String endAddr) {
		String cityName = new SharedUtil(getApplicationContext()).getValue(SharedConstants.CITY_NAME);
		PlanNode originNode = PlanNode.withCityNameAndPlaceName(cityName, originAddr);
		PlanNode endNode = PlanNode.withCityNameAndPlaceName(cityName, endAddr);
		if (type == 1) {
			routeSearch.transitSearch(new TransitRoutePlanOption().from(originNode).to(endNode).city(cityName));
		} else if (type == 2) {
			routeSearch.drivingSearch(new DrivingRoutePlanOption().from(originNode).to(endNode));
		} else if (type == 3) {
			routeSearch.walkingSearch(new WalkingRoutePlanOption().from(originNode).to(endNode));
		}
	}
}
