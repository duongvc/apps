package com.travel.gate365.view;

import com.travel.gate365.R;
import com.travel.gate365.model.MenuItemInfo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class HomeActivity extends BaseActivity implements OnItemClickListener {

	private HomeMenuItemAdapter adapter;
	
	public HomeActivity() {
		super(HomeActivity.class.getSimpleName());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_home);
		init();		
	}
	
	@Override
	protected void init() {
		super.init();
		
		ListView lstMenu = (ListView)findViewById(R.id.lst_menu);
		final MenuItemInfo[] menuList = {new MenuItemInfo(0, R.drawable.ic_0, R.string.journeys)
			, new MenuItemInfo(1, R.drawable.ic_1, R.string.travel_alerts)
			, new MenuItemInfo(2, R.drawable.ic_2, R.string.travel_advices)
			, new MenuItemInfo(3, R.drawable.ic_3, R.string.country_risk)
			, new MenuItemInfo(4, R.drawable.ic_4, R.string.travel_tips)
			, new MenuItemInfo(5, R.drawable.ic_5, R.string.settings)};
		
		adapter = new HomeMenuItemAdapter(this, menuList);
		lstMenu.setAdapter(adapter);
		lstMenu.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int pos, long id) {
		Log.i(getId(), "::onItemClick - pos:" + pos + ", id:" + id);
	}

}
