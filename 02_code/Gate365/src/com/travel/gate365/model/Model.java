package com.travel.gate365.model;
import com.travel.gate365.R;
import com.travel.gate365.view.LoginActivity;

public class Model {

	private static Model sInstance;
	private final ActivityInfo[] arrActivityInfo = {
		new ActivityInfo(LoginActivity.class.getSimpleName(), R.drawable.ic_0, R.string.login_U, 0)
	};
	private boolean isLogin;
	
	private Model() {
	}

	public static Model getInstance() {
		if(sInstance == null){
			sInstance = new Model();
		}
		return sInstance;
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
	
}
