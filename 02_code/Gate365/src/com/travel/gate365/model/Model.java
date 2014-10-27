package com.travel.gate365.model;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;

import com.travel.gate365.R;
import com.travel.gate365.view.LoginActivity;

public class Model {

	private static Model sInstance;
	private final ActivityInfo[] arrActivityInfo = {
		new ActivityInfo(LoginActivity.class.getSimpleName(), R.drawable.ic_0, R.string.login_U, 0)
	};
	private boolean isLogin;
	
	private DisplayMetrics metrics;
	
	private Model() {
	}

	public static Model getInstance() {
		if(sInstance == null){
			sInstance = new Model();
		}
		return sInstance;
	}
	
	public void init(Activity context){
    	Display display = context.getWindowManager().getDefaultDisplay();
        metrics = new DisplayMetrics(); 
        display.getMetrics(metrics); 		
	}

	public ActivityInfo retrieveActivityInfo(String id) {
		for(int i = 0; i < arrActivityInfo.length; i++){
			ActivityInfo info = arrActivityInfo[i]; 
			if(info.getId().equalsIgnoreCase(id)){
				return info;
			}
		}
		return null;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}
	
	public int getScreenWidth(){
		float density = metrics.density;
		return Float.valueOf(metrics.widthPixels / density).intValue(); 		
	}
	
	public int getScreenHeight(){
		float density = metrics.density;
		return Float.valueOf(metrics.heightPixels / density).intValue(); 
	}
	
}
