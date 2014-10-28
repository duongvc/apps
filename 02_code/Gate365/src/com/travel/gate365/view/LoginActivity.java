package com.travel.gate365.view;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.travel.gate365.R;
import com.travel.gate365.model.Model;
import com.travel.gate365.service.ServiceManager;

public class LoginActivity extends BaseActivity {

	 private TextView txtUsername;
	 private TextView txtPassword;
	
	public LoginActivity() {
		super(LoginActivity.class.getSimpleName());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_login);
		
		init();
	}
	
	@Override
	protected void init() {
		super.init();
		
		txtUsername = (TextView)findViewById(R.id.txt_username);
		txtPassword = (TextView)findViewById(R.id.txt_password);
	}
	
	public void onLoginButtonHandler(View view){
		if (txtUsername.getText().length() == 0) {
			Log.i(getId() + " - onLoginButtonHandler", "Please enter username");
			return;
		} else if (txtPassword.getText().length() == 0) {
			Log.i(getId() + " - onLoginButtonHandler", "Please enter password");
			return;
		}
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
					if(res.getString("status").equalsIgnoreCase(ServiceManager.SUCCESS_STATUS)){
						Model.getInstance().setLogin(true);
						LoginActivity.this.finish();
						Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
						startActivity(intent);						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		thread.start();				
	}
	
}
