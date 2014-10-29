package com.travel.gate365.view.alert;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.travel.gate365.R;
import com.travel.gate365.model.AlertItemInfo;
import com.travel.gate365.view.BaseActivity;

public class AlertActivity extends BaseActivity implements OnItemClickListener{

	private AlertItemAdapter adapter;

	public AlertActivity() {
		super(AlertActivity.class.getSimpleName());
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
			};
		
		adapter = new AlertItemAdapter(this, menuList);
		lstMenu.setAdapter(adapter);
		lstMenu.setOnItemClickListener(this);			
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
	}
	
}
