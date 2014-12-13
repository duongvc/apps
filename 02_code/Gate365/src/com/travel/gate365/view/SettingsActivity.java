package com.travel.gate365.view;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.travel.gate365.R;
import com.travel.gate365.model.Model;

public class SettingsActivity extends BaseActivity {

	public SettingsActivity() {
		super(SettingsActivity.class.getSimpleName()); 
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_settings);
		
		init();
		
	}
	
	@Override
	protected void init() {
		super.init();

		load(false);
		
		CheckBox chkGpstracking = (CheckBox)findViewById(R.id.chk_gpstracking);
		chkGpstracking.setActivated(Model.getInstance().isLocationTrackingEnabled());
		TextView text = ((TextView)findViewById(R.id.txt_frequency));
		text.setText(getString(R.string.frequency)+ ": " + Model.getInstance().getLocationTrackingInterval() + " " + getString(R.string.seconds));
		text = ((TextView)findViewById(R.id.txt_last_time));
		text = ((TextView)findViewById(R.id.txt_last_latitude));
		text = ((TextView)findViewById(R.id.txt_last_longtitude));
		
		ImageView icRefresh = (ImageView)findViewById(R.id.img_refresh);
		icRefresh.setVisibility(View.GONE);
	}
	
	public void onLogoutButtonHandler(View view){
		setResult(RESULT_LOGOUT);
		finish();
	}
	
}
