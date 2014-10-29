package com.travel.gate365.view.journeys;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.travel.gate365.R;
import com.travel.gate365.model.FlightRoutingInfo;
import com.travel.gate365.view.BaseActivity;

public class JourneyDetailActivity extends BaseActivity implements OnItemClickListener {

	private FlightRoutingItemAdapter adapter;
	
	public JourneyDetailActivity() {
		super(JourneyDetailActivity.class.getSimpleName());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_journey_details);
		
		init();
		
	}
	
	@Override
	protected void init() {
		super.init();
		
		ListView lstMenu = (ListView)findViewById(R.id.lst_flight_routing);
		final FlightRoutingInfo[] menuList = {new FlightRoutingInfo(0)
			, new FlightRoutingInfo(1)
			, new FlightRoutingInfo(2)
			, new FlightRoutingInfo(3)
			, new FlightRoutingInfo(4)
			, new FlightRoutingInfo(5)};
		
		adapter = new FlightRoutingItemAdapter(this, menuList);
		lstMenu.setAdapter(adapter);
		lstMenu.setOnItemClickListener(this);			
		
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int itemPos, long itemId) {
	}

}
