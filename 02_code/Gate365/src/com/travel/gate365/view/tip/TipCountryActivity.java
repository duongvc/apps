package com.travel.gate365.view.tip;

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

public class TipCountryActivity  extends BaseActivity implements OnItemClickListener {

	private AdviceItemAdapter adapter;
	
	public TipCountryActivity() {
		super(TipCountryActivity.class.getSimpleName());
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_tips);
		
		init();
		
	}
	
	@Override
	protected void init() {
		super.init();
		
		ListView lstMenu = (ListView)findViewById(R.id.lst_tips);
		
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
