package com.travel.gate365;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.travel.gate365.model.MenuItemInfo;
import com.travel.gate365.model.Model;
import com.travel.gate365.service.ServiceManager;
import com.travel.gate365.view.BaseActivity;
import com.travel.gate365.view.HomeMenuItemAdapter;

public class Gate365Activity extends BaseActivity implements OnItemClickListener {

	private TextView txtUsername;
	private TextView txtPassword;
	private HomeMenuItemAdapter adapter;
	
	public Gate365Activity() {
		super(Gate365Activity.class.getSimpleName());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Model.getInstance().init(this);		
		if(Model.getInstance().isLogin()){
			setContentView(R.layout.activity_home);
		}else{
			setContentView(R.layout.activity_login);
		}		
		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_gate365, menu);
		return true;
	}

	@Override
	protected void init() {
		super.init();
		
		if(Model.getInstance().isLogin()){
			ListView lstMenu = (ListView)findViewById(R.id.lst_menu);
			final MenuItemInfo[] menuList = {new MenuItemInfo(0, R.drawable.ic_0, R.string.journeys)
				, new MenuItemInfo(1, R.drawable.ic_1, R.string.travel_alerts)
				, new MenuItemInfo(2, R.drawable.ic_2, R.string.travel_advices)
				, new MenuItemInfo(3, R.drawable.ic_3, R.string.country_risk)
				, new MenuItemInfo(4, R.drawable.ic_4, R.string.travel_tips)
				, new MenuItemInfo(5, R.drawable.ic_5, R.string.settings)};
			
			adapter = new HomeMenuItemAdapter(this, menuList);
			lstMenu.setAdapter(adapter);
			lstMenu.setOnItemClickListener(this);			
		}else{
			txtUsername = (TextView)findViewById(R.id.txt_username);
			txtPassword = (TextView)findViewById(R.id.txt_password);			
		}
	}
	
	public void onLoginButtonHandler(View view){
		/*if (txtUsername.getText().length() == 0) {
			Log.i(getId() + " - onLoginButtonHandler", "Please enter username");
			return;
		} else if (txtPassword.getText().length() == 0) {
			Log.i(getId() + " - onLoginButtonHandler", "Please enter password");
			return;
		}*/
		if(loading == null || (loading != null && !loading.isShowing())){
			loading = ProgressDialog.show(Gate365Activity.this, "", ""); 
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
						//setContentView(R.layout.activity_home);
						//init();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		thread.start();				
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int pos, long id) {
		Log.i(getId(), "::onItemClick - pos:" + pos + ", id:" + id);
	}

	
}
