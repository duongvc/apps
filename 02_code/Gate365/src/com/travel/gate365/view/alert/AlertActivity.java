package com.travel.gate365.view.alert;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.travel.gate365.R;
import com.travel.gate365.model.AdviceItemInfo;
import com.travel.gate365.model.AlertItemInfo;
import com.travel.gate365.view.BaseActivity;

public class AlertActivity extends BaseActivity implements OnItemClickListener{

	private AlertItemAdapter adapter;

	public AlertActivity() {
		super(AlertActivity.class.getSimpleName());
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_alerts);
		
		init();
	}

	@Override
	protected void init() {
		super.init();

		
		TextView txtMessage = (TextView)findViewById(R.id.txt_message);
		txtMessage.setVisibility(View.GONE);
		
		ListView lstMenu = (ListView)findViewById(R.id.lst_alerts);
		final AlertItemInfo[] menuList = {new AlertItemInfo(0)
			, new AlertItemInfo(1)
			, new AlertItemInfo(2)
			, new AlertItemInfo(3)
			, new AlertItemInfo(4)
			, new AlertItemInfo(5)
			, new AlertItemInfo(6)
			, new AlertItemInfo(7)
			, new AlertItemInfo(8)
			, new AlertItemInfo(9)
			, new AlertItemInfo(10)
			, new AlertItemInfo(11)
			, new AlertItemInfo(12)
			, new AlertItemInfo(13)
			, new AlertItemInfo(14)
			, new AlertItemInfo(15)
			, new AlertItemInfo(16)
			, new AlertItemInfo(17)
			, new AlertItemInfo(18)
			, new AlertItemInfo(19)
			, new AlertItemInfo(20)
			};
		
		adapter = new AlertItemAdapter(this, menuList);
		lstMenu.setAdapter(adapter);
		lstMenu.setOnItemClickListener(this);			
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int itemPos, long itemId) {
		Log.i(getId(), "::onItemClick - pos:" + itemPos + ", id:" + itemId);
		Intent intent = new Intent(this, AlertDetailActivity.class);
		startActivity(intent);
	}
	
}
