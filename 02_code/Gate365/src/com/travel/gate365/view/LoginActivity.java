package com.travel.gate365.view;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;

import com.travel.gate365.R;
import com.travel.gate365.service.ServiceManager;

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
	
	public void onLoginButtonHandler(View view){
		if(loading == null || (loading != null && !loading.isShowing())){
			loading = ProgressDialog.show(LoginActivity.this, "", ""); 
			loading.show();
		}
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try { 
					JSONObject res = ServiceManager.login("ux00287", "1");
					loading.dismiss();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		thread.start();				
	}
	
}
