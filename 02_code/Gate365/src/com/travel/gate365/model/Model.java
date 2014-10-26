package com.travel.gate365.model;

import com.travel.gate365.R;
import com.travel.gate365.view.LoginActivity;

public class Model {

	private static Model sInstance;
	private final ActivityInfo[] arrActivityInfo;
	
	private Model() {
		arrActivityInfo = new ActivityInfo[1];
		arrActivityInfo[0] = new ActivityInfo(LoginActivity.class.getSimpleName(), R.drawable.ic_0, R.string.login_U);
		
	}

	public static Model getInstance() {
		if(sInstance == null){
			sInstance = new Model();
		}
		return sInstance;
	}
	
}
