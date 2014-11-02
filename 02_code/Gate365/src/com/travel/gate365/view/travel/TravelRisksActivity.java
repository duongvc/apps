package com.travel.gate365.view.travel;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.travel.gate365.R;
import com.travel.gate365.model.AdviceItemInfo;
import com.travel.gate365.view.BaseActivity;
import com.travel.gate365.view.travel.AdviceItemAdapter;

public class TravelRisksActivity  extends BaseActivity implements OnItemClickListener {

	private AdviceItemAdapter adapter;
	
	public TravelRisksActivity() {
		super(TravelRisksActivity.class.getSimpleName());
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_risks);
		
		init();
		
	}
	
	@Override
	protected void init() {
		super.init();
		
		ListView lstMenu = (ListView)findViewById(R.id.lst_risks);
		
		TextView txtMessage = (TextView)findViewById(R.id.txt_message);
		txtMessage.setVisibility(View.GONE);
	}
	
	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int itemPos, long itemId) {
		Log.i(getId(), "::onItemSelected - pos:" + itemPos + ", id:" + itemId);
		//Intent intent = new Intent(this, JourneyDetailActivity.class);
		//startActivity(intent);
	}

}
