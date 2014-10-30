package com.travel.gate365.view.journeys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.travel.gate365.R;
import com.travel.gate365.model.JourneyItemInfo;
import com.travel.gate365.view.BaseActivity;

public class JourneysActivity extends BaseActivity implements OnItemClickListener {

	private JourneyItemAdapter adapter;

	public JourneysActivity() {
		super(JourneysActivity.class.getSimpleName());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_journeys);
		
		init();
		
	}
	
	@Override
	protected void init() {
		super.init();
		
		ListView lstMenu = (ListView)findViewById(R.id.lst_journeys);
		final JourneyItemInfo[] menuList = {new JourneyItemInfo(0)
			, new JourneyItemInfo(1)
			, new JourneyItemInfo(2)
			, new JourneyItemInfo(3)
			, new JourneyItemInfo(4)
			, new JourneyItemInfo(5)
			, new JourneyItemInfo(6)
			, new JourneyItemInfo(7)
			, new JourneyItemInfo(8)
			, new JourneyItemInfo(9)
			, new JourneyItemInfo(10)
			};
		
		adapter = new JourneyItemAdapter(this, menuList);
		lstMenu.setAdapter(adapter);
		lstMenu.setOnItemClickListener(this);			
		
		TextView txtMessage = (TextView)findViewById(R.id.txt_message);
		txtMessage.setVisibility(View.GONE);
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int itemPos, long itemId) {
		Log.i(getId(), "::onItemSelected - pos:" + itemPos + ", id:" + itemId);
		Intent intent = new Intent(this, JourneyDetailActivity.class);
		startActivity(intent);
	}
}
