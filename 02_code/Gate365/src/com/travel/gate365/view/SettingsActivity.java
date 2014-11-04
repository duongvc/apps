package com.travel.gate365.view;

import android.os.Bundle;

import com.travel.gate365.R;

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

		load();
	}
	
}
