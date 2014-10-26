package com.travel.gate365.view;

import android.app.Activity;

public abstract class BaseActivity extends Activity {

	private final String id;
	
	public BaseActivity(String id) {
		this.id = id;
	}

	protected void init() {
		
	}

	public String getId() {
		return id;
	}
}
