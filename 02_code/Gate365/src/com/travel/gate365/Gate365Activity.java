package com.travel.gate365;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.travel.gate365.helper.DialogHelper;
import com.travel.gate365.model.MenuItemInfo;
import com.travel.gate365.model.Model;
import com.travel.gate365.service.GPSWrapper;
import com.travel.gate365.service.ServiceManager;
import com.travel.gate365.view.BaseActivity;
import com.travel.gate365.view.SettingsActivity;
import com.travel.gate365.view.alert.AlertActivity;
import com.travel.gate365.view.home.HomeMenuItemAdapter;
import com.travel.gate365.view.journeys.JourneysActivity;
import com.travel.gate365.view.travel.DesCountriesActivity;

public class Gate365Activity extends BaseActivity implements OnItemClickListener {

	private TextView edtUsername;
	private TextView edtPassword;
	private HomeMenuItemAdapter adapter;
	private boolean fakeMode = true;
	
	public Gate365Activity() {
		super(Gate365Activity.class.getSimpleName());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Model.getInstance().init(this);			
		
		SharedPreferences pref = getSharedPreferences(CONFIG_NAME, MODE_PRIVATE);
		boolean gpstracking = pref.getBoolean(IS_GPS_TRACKING, fakeMode || false); 
		String lastSent = pref.getString(LAST_SENT, getString(R.string.never_sent));
		String username = pref.getString(USERNAME, "");
		String password = pref.getString(PASSWORD, "");
		
		Model.getInstance().setLogin(pref.getBoolean(IS_LOGIN, false) && username.length() > 0 && password.length() > 0);
		Model.getInstance().setLocationTrackingEnabled(gpstracking);
		Model.getInstance().paserLoginInfo(username, password);
		Model.getInstance().setLastTimeSent(lastSent);
		if(Model.getInstance().isLogin()){
			setContentView(R.layout.activity_home);
		}else{
			setContentView(R.layout.activity_login);
		}		
		init();
		
		if(gpstracking){
			startService(new Intent(getApplicationContext(), GPSWrapper.class));
			GPSWrapper.getInstance().init(this, 160000);
			//GPSWrapper.getInstance().startTracking();
		}
	}

	@Override
	protected void init() {
		super.init();
		
		if(Model.getInstance().isLogin()){
			GridView grdMenu = (GridView)findViewById(R.id.layout_content);
			adapter = new HomeMenuItemAdapter(this, Model.MENU_LIST);
			if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
				grdMenu.setNumColumns(1);
			}else{
				grdMenu.setNumColumns(2);
			}
			grdMenu.setAdapter(adapter);
			grdMenu.setOnItemClickListener(this);
			grdMenu.setOnTouchListener(onTouchListener);			
		}else{
			edtUsername = (TextView)findViewById(R.id.edt_username);
			edtPassword = (TextView)findViewById(R.id.edt_password);	
			edtPassword.setImeActionLabel(getString(R.string.login_l), EditorInfo.IME_ACTION_GO);	
			edtPassword.setOnEditorActionListener(onEditorActionListener);	
			
			ImageView icRefresh = (ImageView)findViewById(R.id.img_refresh);
			icRefresh.setVisibility(View.GONE);
		}		
	}
	
	@Override
	public void onBackPressed() {
		DialogHelper.yesNoAlert(this, getString(R.string.exit_app), getString(R.string.are_you_sure_exit_app)
				, getString(android.R.string.yes), getString(android.R.string.no), exitPositiveHandler, exitNegativeHandler);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case FINISH_CODE:
			if(resultCode == RESULT_LOGOUT){
				Model.getInstance().setLogin(false);
				SharedPreferences pref = getSharedPreferences(CONFIG_NAME, MODE_PRIVATE);
				SharedPreferences.Editor editor = pref.edit();
				editor.putBoolean(IS_LOGIN, false);
				editor.putString(USERNAME, "");
				editor.putString(PASSWORD, "");
				editor.commit();	            				
				setContentView(R.layout.activity_login);
				init();			
			}
			break;

		default:
			super.onActivityResult(requestCode, resultCode, data);
			break;
		}
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_logout:
			Model.getInstance().setLogin(false);
			SharedPreferences pref = getSharedPreferences(CONFIG_NAME, MODE_PRIVATE);
			SharedPreferences.Editor editor = pref.edit();
			editor.putBoolean(IS_LOGIN, false);
			editor.putString(USERNAME, "");
			editor.putString(PASSWORD, "");
			editor.commit();	            				
			setContentView(R.layout.activity_login);
			init();			
			return true;

		default:
			return super.onMenuItemSelected(featureId, item);
		}		
		
	}
	
	public void onLoginButtonHandler(View view){
		final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(edtPassword.getWindowToken(), 0);
        View viewTmp = getCurrentFocus();
        if(viewTmp != null){ 
        	viewTmp.clearFocus();
        }
        
		if(!isNetworkAvailable()){
			DialogHelper.alert(this, R.string.no_internet, R.string.no_internet_avaialbe, null);
			return;
		}
		
		edtUsername.setHintTextColor(getResources().getColor(R.color.gray));
		edtPassword.setHintTextColor(getResources().getColor(R.color.gray));
		if(!fakeMode){
			if (edtUsername.getText().length() == 0) {
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
			}			
		}
		if(loading == null || (loading != null && !loading.isShowing())){
			loading = ProgressDialog.show(Gate365Activity.this, "", getString(R.string.logging_pls_wait)); 
			loading.show();
		}
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try { 
					JSONObject res;
					if(fakeMode){
						res = ServiceManager.login("ux00287", "1");
					}else{
						res = ServiceManager.login(edtUsername.getText().toString(), edtPassword.getText().toString());
					}
					if(res != null && res.getString("status").equalsIgnoreCase(ServiceManager.SUCCESS_STATUS)){
						Model.getInstance().setLogin(true);
						if(fakeMode){
							Model.getInstance().paserLoginInfo("ux00287", "1");
						}else{
							Model.getInstance().paserLoginInfo(edtUsername.getText().toString(), edtPassword.getText().toString());	
						}
						
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
					android.os.Message msg = new Message();
					msg.what = BaseActivity.NOTE_COULD_NOT_REQUEST_SERVER_DATA;
					notificationHandler.sendMessage(msg);	 											
				}
				if(Model.getInstance().isLogin()){
					try{
						JSONObject res = ServiceManager.getConfiguration(Model.getInstance().getUserInfo().getUsername(), Model.getInstance().getUserInfo().getPassword());
						if(res != null){
							Model.getInstance().parserConfiguration(res);
							android.os.Message msg = new Message();
							msg.what = BaseActivity.NOTE_LOAD_CONFIGURATION_SUCCESSFULLY;
							notificationHandler.sendMessage(msg);					
						}
					}catch(Exception e){
						e.printStackTrace();
					}
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
			intent = new Intent(this, DesCountriesActivity.class);
			intent.putExtra(DesCountriesActivity.TARGET_TYPE, DesCountriesActivity.TARGET_RISK);
			startActivityForResult(intent, FINISH_CODE);			
			break;

		case MenuItemInfo.MENU_ITEM_JOURNEYS:
			intent = new Intent(this, JourneysActivity.class);
			startActivityForResult(intent, FINISH_CODE);
			break;

		case MenuItemInfo.MENU_ITEM_SETTINGS:
			intent = new Intent(this, SettingsActivity.class);
			startActivityForResult(intent, FINISH_CODE);
			break;

		case MenuItemInfo.MENU_ITEM_TRAVEL_ADVICES:
			intent = new Intent(this, DesCountriesActivity.class);
			intent.putExtra(DesCountriesActivity.TARGET_TYPE, DesCountriesActivity.TARGET_ADVICE);
			startActivityForResult(intent, FINISH_CODE);
			break;

		case MenuItemInfo.MENU_ITEM_TRAVEL_ALERTS:
			intent = new Intent(this, AlertActivity.class);
			startActivityForResult(intent, FINISH_CODE);
			break;

		case MenuItemInfo.MENU_ITEM_TRAVEL_TIPS:
			intent = new Intent(this, DesCountriesActivity.class);
			intent.putExtra(DesCountriesActivity.TARGET_TYPE, DesCountriesActivity.TARGET_TIP);
			startActivityForResult(intent, FINISH_CODE);
			break;

		default:
			break;
		}
	}

	private OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() {
	    @Override
	    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
	        if (actionId == EditorInfo.IME_ACTION_GO) {
	        	onLoginButtonHandler(null);
	            return true;
	        }
	        return false;
	    }
	};
	
	private DialogInterface.OnClickListener exitPositiveHandler = new DialogInterface.OnClickListener(){

		@Override
		public void onClick(DialogInterface dialog, int which) {
			Gate365Activity.this.exitApp();
		}
		
	};
	private DialogInterface.OnClickListener exitNegativeHandler = new DialogInterface.OnClickListener(){

		@Override
		public void onClick(DialogInterface dialog, int which) {
			
		}
		
	};

	protected void exitApp() {
		super.onBackPressed();
		
	}
	
	OnTouchListener onTouchListener = new OnTouchListener(){
		 
	    @Override
	    public boolean onTouch(View v, MotionEvent event) {
	        if(event.getAction() == MotionEvent.ACTION_MOVE){
	            return true;
	        }
	        return false;
	    }
	 
	};

	protected final Handler notificationHandler = new MyHandler(this);

	private static final class MyHandler extends Handler {
		private final WeakReference<Gate365Activity> mActivity;

		public MyHandler(Gate365Activity activity) {
			mActivity = new WeakReference<Gate365Activity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			Log.i(Gate365Activity.class.getSimpleName(), "msg.what:" + msg.what);
			Gate365Activity activity = mActivity.get();
			if (activity != null) {
				if (loading != null) {
					loading.dismiss();
				}
				switch (msg.what) {
				case NOTE_LOGIN_SUCCESSFULLY:
					SharedPreferences pref = activity.getSharedPreferences(CONFIG_NAME, MODE_PRIVATE);
					SharedPreferences.Editor editor = pref.edit();
					editor.putBoolean(IS_LOGIN, true);
					editor.putString(USERNAME, Model.getInstance().getUserInfo().getUsername());
					editor.putString(PASSWORD, Model.getInstance().getUserInfo().getPassword());
					editor.commit();

					activity.setContentView(R.layout.activity_home);
					activity.init();
					break;

				case NOTE_LOGIN_FAILED:
					DialogHelper.alert(activity, R.string.login_failed, R.string.invalid_username_pass);
					break;

				case NOTE_COULD_NOT_REQUEST_SERVER_DATA:
					DialogHelper.alert(activity, R.string.load_failed, R.string.could_not_connect_server);
					break;
						
				case NOTE_LOAD_CONFIGURATION_SUCCESSFULLY:
					if (Model.getInstance().isLocationTrackingEnabled()) {
						GPSWrapper.getInstance().init(activity, Model.getInstance().getLocationTrackingInterval());
						//GPSWrapper.getInstance().startTracking();
					}
					break;
					
				default:
					break;
				}
			}
		}
	}
	
	public void onNewLocation(Location location){
		Model model = Model.getInstance();
		model.setLastLattitude(String.valueOf(location.getLatitude()));
		model.setLastLongtitude(String.valueOf(location.getLatitude()));
		try {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.getDefault());
			Model.getInstance().setLastTimeSent(dateFormat.format(cal.getTime()));
			ServiceManager.sendLocation(model.getUserInfo().getUsername(), model.getUserInfo().getPassword(), location.getLatitude(), location.getLongitude());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		android.os.Message msg = new Message();
		msg.what = BaseActivity.NOTE_LOCATION_CHANGED;
		notificationHandler.sendMessage(msg);							
	}
	
}
