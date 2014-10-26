package com.travel.gate365.view;

import android.os.Bundle;

import com.travel.gate365.R;

public class LoginActivity extends BaseActivity {

	public LoginActivity() {
		super(LoginActivity.class.getSimpleName());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_login);
		
		init();
	}
	
}
