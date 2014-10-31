package com.travel.gate365;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.travel.gate365.helper.DialogHelper;
import com.travel.gate365.model.MenuItemInfo;
import com.travel.gate365.model.Model;
import com.travel.gate365.service.ServiceManager;
import com.travel.gate365.view.BaseActivity;
import com.travel.gate365.view.alert.AlertActivity;
import com.travel.gate365.view.home.HomeMenuItemAdapter;
import com.travel.gate365.view.journeys.JourneyDetailActivity;
import com.travel.gate365.view.journeys.JourneysActivity;
import com.travel.gate365.view.tip.TipCountryActivity;
import com.travel.gate365.view.travel.AdvicesActivity;
import com.travel.gate365.view.travel.TravelRisksActivity;

public class Gate365Activity extends BaseActivity implements OnItemClickListener {

	private TextView edtUsername;
	private TextView edtPassword;
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
	protected void init() {
		super.init();
		
		if(Model.getInstance().isLogin()){
			ListView lstMenu = (ListView)findViewById(R.id.lst_menu);
			final MenuItemInfo[] menuList = {new MenuItemInfo(MenuItemInfo.MENU_ITEM_JOURNEYS, R.drawable.journeys_menuitem_selector, R.string.journeys)
				, new MenuItemInfo(MenuItemInfo.MENU_ITEM_TRAVEL_ALERTS, R.drawable.tvalerts_menuitem_selector, R.string.travel_alerts)
				, new MenuItemInfo(MenuItemInfo.MENU_ITEM_TRAVEL_ADVICES, R.drawable.tvadvices_menuitem_selector, R.string.travel_advices)
				, new MenuItemInfo(MenuItemInfo.MENU_ITEM_COUNTRY_RISK, R.drawable.countryrisk_menuitem_selector, R.string.country_risk)
				, new MenuItemInfo(MenuItemInfo.MENU_ITEM_TRAVEL_TIPS, R.drawable.tvtips_menuitem_selector, R.string.travel_tips)
				, new MenuItemInfo(MenuItemInfo.MENU_ITEM_SETTINGS, R.drawable.settings_menuitem_selector, R.string.settings)};
			
			adapter = new HomeMenuItemAdapter(this, menuList);
			lstMenu.setAdapter(adapter);
			lstMenu.setOnItemClickListener(this);			
		}else{
			edtUsername = (TextView)findViewById(R.id.edt_username);
			edtPassword = (TextView)findViewById(R.id.edt_password);			
		}
	}
	
	public void onLoginButtonHandler(View view){
		if(!isNetworkAvailable()){
			DialogHelper.alert(this, R.string.no_internet, R.string.no_internet_avaialbe, null);
			return;
		}
		
		edtUsername.setHintTextColor(getResources().getColor(R.color.gray));
		edtPassword.setHintTextColor(getResources().getColor(R.color.gray));
		/*if (edtUsername.getText().length() == 0) {
			Log.i(getId() + " - onLoginButtonHandler", "Please enter username");
			edtUsername.setHint(R.string.pls_enter_username);
			edtUsername.setHintTextColor(getResources().getColor(R.color.red));
			return;
		} 
		if (edtPassword.getText().length() == 0) {
			Log.i(getId() + " - onLoginButtonHandler", "Please enter password");
			edtPassword.setHint(R.string.pls_enter_password);
			edtPassword.setHintTextColor(getResources().getColor(R.color.red));
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
					//JSONObject res = ServiceManager.login(edtUsername.getText().toString(), edtPassword.getText().toString());
					loading.dismiss();
					if(res != null && res.getString("status").equalsIgnoreCase(ServiceManager.SUCCESS_STATUS)){
						Model.getInstance().setLogin(true);
						//Model.getInstance().paserLoginInfo(edtUsername.getText().toString(), edtPassword.getText().toString());
						Model.getInstance().paserLoginInfo("ux00287", "1");
						android.os.Message msg = new Message();
						msg.what = BaseActivity.NOTE_LOGIN_SUCCESSFULLY;
						notificationHandler.sendMessage(msg);						
					}else{
						android.os.Message msg = new Message();
						msg.what = BaseActivity.NOTE_LOGIN_FAILED;
						notificationHandler.sendMessage(msg);												
					}
				} catch (Exception e) {
					e.printStackTrace();
					loading.dismiss();
					android.os.Message msg = new Message();
					msg.what = BaseActivity.NOTE_COULD_NOT_CONNECT_SERVER;
					notificationHandler.sendMessage(msg);												
				}
			}
		});
		
		thread.start();				
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int pos, long id) {
		Log.i(getId(), "::onItemClick - pos:" + pos + ", id:" + id);
		Intent intent;
		switch ((int)id) {
		case MenuItemInfo.MENU_ITEM_COUNTRY_RISK:
			intent = new Intent(this, TravelRisksActivity.class);
			startActivity(intent);			
			break;

		case MenuItemInfo.MENU_ITEM_JOURNEYS:
			intent = new Intent(this, JourneysActivity.class);
			startActivity(intent);
			break;

		case MenuItemInfo.MENU_ITEM_SETTINGS:
			
			break;

		case MenuItemInfo.MENU_ITEM_TRAVEL_ADVICES:
			intent = new Intent(this, AdvicesActivity.class);
			startActivity(intent);
			break;

		case MenuItemInfo.MENU_ITEM_TRAVEL_ALERTS:
			intent = new Intent(this, AlertActivity.class);
			startActivity(intent);
			break;

		case MenuItemInfo.MENU_ITEM_TRAVEL_TIPS:
			intent = new Intent(this, TipCountryActivity.class);
			startActivity(intent);			
			break;

		default:
			break;
		}
	}

	
}
