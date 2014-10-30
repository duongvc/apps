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
import com.travel.gate365.view.travel.CountryItemAdapter;

public class TravelRisksActivity  extends BaseActivity implements OnItemClickListener {

	private CountryItemAdapter adapter;
	
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
		final AdviceItemInfo[] menuList = {new AdviceItemInfo(0)
			, new AdviceItemInfo(1)
			, new AdviceItemInfo(2)
			, new AdviceItemInfo(3)
			, new AdviceItemInfo(4)
			, new AdviceItemInfo(5)
			, new AdviceItemInfo(6)
			, new AdviceItemInfo(7)
			, new AdviceItemInfo(8)
			, new AdviceItemInfo(9)
			, new AdviceItemInfo(10)
			, new AdviceItemInfo(11)
			, new AdviceItemInfo(12)
			, new AdviceItemInfo(13)
			, new AdviceItemInfo(14)
			, new AdviceItemInfo(15)
			, new AdviceItemInfo(16)
			, new AdviceItemInfo(17)
			, new AdviceItemInfo(18)
			, new AdviceItemInfo(19)
			, new AdviceItemInfo(20)
		
			};
		
		adapter = new CountryItemAdapter(this, menuList, R.layout.risk_item);
		lstMenu.setAdapter(adapter);
		lstMenu.setOnItemClickListener(this);			
		
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
