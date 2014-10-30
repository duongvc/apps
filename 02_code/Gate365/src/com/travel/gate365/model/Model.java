package com.travel.gate365.model;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;

import com.travel.gate365.Gate365Activity;
import com.travel.gate365.R;
import com.travel.gate365.view.alert.AlertActivity;
import com.travel.gate365.view.alert.AlertDetailActivity;
import com.travel.gate365.view.journeys.JourneyDetailActivity;
import com.travel.gate365.view.journeys.JourneysActivity;
import com.travel.gate365.view.tip.TipCountryActivity;
import com.travel.gate365.view.travel.AdvicesActivity;

public class Model {

	private static Model sInstance;
	private final ActivityInfo[] arrActivityInfo = {
		new ActivityInfo(Gate365Activity.class.getSimpleName(), R.drawable.ic_0, R.string.login_U, 0)
		, new ActivityInfo(JourneysActivity.class.getSimpleName(), R.drawable.ic_0, R.string.journeys, R.string.destinations)
		, new ActivityInfo(JourneyDetailActivity.class.getSimpleName(), R.drawable.ic_0, R.string.journey_details, 0)
		, new ActivityInfo(AlertActivity.class.getSimpleName(), R.drawable.ic_1, R.string.travel_alerts, 0)
		, new ActivityInfo(AlertDetailActivity.class.getSimpleName(), R.drawable.ic_1, R.string.alert_details, 0)
		, new ActivityInfo(AdvicesActivity.class.getSimpleName(), R.drawable.ic_2, R.string.travel_advices, 0)
		, new ActivityInfo(TipCountryActivity.class.getSimpleName(), R.drawable.ic_5, R.string.travel_tips, 0)
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
