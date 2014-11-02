package com.travel.gate365.view.journeys;

import java.util.Locale;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.travel.gate365.R;
import com.travel.gate365.helper.DateTimeHelper;
import com.travel.gate365.model.FlightRoutingInfo;
import com.travel.gate365.model.JourneyItemInfo;
import com.travel.gate365.model.Model;
import com.travel.gate365.view.BaseActivity;

public class JourneyDetailActivity extends BaseActivity implements OnItemClickListener {

	public static final String JOURNEY_ID = "journeyId";
	
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
				
		int journeyId = (int)getIntent().getExtras().getLong(JOURNEY_ID);
		JourneyItemInfo info = Model.getInstance().getJourney(journeyId);
		
		View view = (View)findViewById(R.id.header);
		TextView txtRight = (TextView)view.findViewById(R.id.txt_right);
		txtRight.setText("PRN " + info.getpNRCode());
		
		View layout = findViewById(R.id.layout_depart);
		ImageView icon = (ImageView)layout.findViewById(R.id.img_icon);
		int maxHeight = Math.min(Model.getInstance().getScreenHeight() / 15, 128);
		icon.setLayoutParams(new RelativeLayout.LayoutParams(maxHeight, maxHeight));
		updateIcon(info, icon);
		TextView textView = (TextView)layout.findViewById(R.id.txt_country);
		textView.setText(info.getSrc().getCountryName().toUpperCase(Locale.US));
		textView = (TextView)layout.findViewById(R.id.txt_city);
		textView.setText("- " + info.getSrc().getLocationName().toUpperCase(Locale.US));
		textView = (TextView)layout.findViewById(R.id.txt_codetime);
		String dateTime = DateTimeHelper.convertDateStringToWWW_ddMMMyyyy(this, info.getFlightRoutings()[0].getDepartureDateTime());
		textView.setText(getString(R.string.departs) + ":" + dateTime);
		
		layout = findViewById(R.id.layout_arrival);
		icon = (ImageView)layout.findViewById(R.id.img_icon);
		icon.setLayoutParams(new RelativeLayout.LayoutParams(maxHeight, maxHeight));
		icon.setImageResource(R.drawable._landing_plane);
		textView = (TextView)layout.findViewById(R.id.txt_country);
		textView.setText(info.getDes().getCountryName().toUpperCase(Locale.US));
		textView = (TextView)layout.findViewById(R.id.txt_city);
		textView.setText("- " + info.getDes().getLocationName().toUpperCase(Locale.US));
		textView = (TextView)layout.findViewById(R.id.txt_codetime);
		dateTime = DateTimeHelper.convertDateStringToWWW_ddMMMyyyy(this, info.getFlightRoutings()[info.getFlightRoutings().length - 1].getArrivalDateTime());
		textView.setText(getString(R.string.arrives) + ":" + dateTime);
		
		ListView lstMenu = (ListView)findViewById(R.id.lst_flight_routing);
		final FlightRoutingInfo[] menuList = info.getFlightRoutings();
		
		adapter = new FlightRoutingItemAdapter(this, menuList);
		lstMenu.setAdapter(adapter);
		lstMenu.setOnItemClickListener(this);			
		
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int itemPos, long itemId) {
	}

	private void updateIcon(JourneyItemInfo journey, ImageView icon){
		int iconResId;
		if(journey.getDes().getSecurityRisk().equalsIgnoreCase("HIGH") || journey.getDes().getSecurityRisk().equalsIgnoreCase("EXTREME")){
			iconResId = R.drawable._take_off_plane2;
		}else{
			iconResId = R.drawable._take_off_plane;
		}
		
		icon.setImageResource(iconResId);
	}
}
