package com.travel.gate365.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.travel.gate365.R;
import com.travel.gate365.helper.DateTimeHelper;
import com.travel.gate365.helper.DialogHelper;
import com.travel.gate365.model.ActivityInfo;
import com.travel.gate365.model.Model;

public abstract class BaseActivity extends Activity {

	protected static final int NOTE_LOGIN_SUCCESSFULLY = 1;
	protected static final int NOTE_LOGIN_FAILED = 2;
	protected static final int NOTE_COULD_NOT_CONNECT_SERVER = 3;
	protected static final int NOTE_LOAD_JOURNEY_SUCCESSFULLY = 4;
	protected static final int NOTE_LOAD_JOURNEY_FAILED = 5;
	protected static final int NOTE_LOAD_ALERT_SUCCESSFULLY = 6;
	protected static final int NOTE_LOAD_ADVICE_SUCCESSFULLY = 7;
	protected static final int NOTE_LOAD_PLACE_SUCCESSFULLY = 8;
	protected static final int NOTE_LOAD_RISK_SUCCESSFULLY = 9;
	protected static final int NOTE_LOAD_TIP_SUCCESSFULLY = 10;
	protected static final String CONFIG_NAME = "config";
	protected static final String IS_LOGIN = "isLogin";
	protected static final String IS_GPS_TRACKING = "isGpsTracking";
	protected static final String USERNAME = "username";
	protected static final String PASSWORD = "password";
	
	protected static final int RESULT_LOGOUT = 99;
	
	private String id;
	protected static ProgressDialog loading;	
	
	public BaseActivity(String id) {
		this.id = id;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		if(Model.getInstance().isLogin()){
			getMenuInflater().inflate(R.menu.activity_gate365_logged, menu);
		}else{
			getMenuInflater().inflate(R.menu.activity_gate365, menu);
		}
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_logout:
			setResult(RESULT_LOGOUT);
			finish();
			break;

		case R.id.menu_close:
			moveTaskToBack(true);
			break;

		case R.id.menu_refresh:
			load();
			break;
		
		default:
			break;
		}		
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
	    if(ev.getAction() == MotionEvent.ACTION_UP) {
	        final View view = getCurrentFocus();

	        if(view != null) {
	            final boolean consumed = super.dispatchTouchEvent(ev);

	            final View viewTmp = getCurrentFocus();
	            final View viewNew = viewTmp != null ? viewTmp : view;

	            if(viewNew.equals(view)) {
	                final Rect rect = new Rect();
	                final int[] coordinates = new int[2];

	                view.getLocationOnScreen(coordinates);

	                rect.set(coordinates[0], coordinates[1], coordinates[0] + view.getWidth(), coordinates[1] + view.getHeight());

	                final int x = (int) ev.getX();
	                final int y = (int) ev.getY();

	                if(rect.contains(x, y)) {
	                    return consumed;
	                }
	            }
	            else if(viewNew instanceof EditText) {
	                return consumed;
	            }

	            final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

	            inputMethodManager.hideSoftInputFromWindow(viewNew.getWindowToken(), 0);
	            viewNew.clearFocus();

	            return consumed;
	        }
	    }       

	    return super.dispatchTouchEvent(ev);
	}	
	
	protected void init() {
		ActivityInfo info = Model.getInstance().retrieveActivityInfo(id);
		if(info != null){
			View view = (View)findViewById(R.id.titlebar);
			ImageView img = (ImageView)view.findViewById(R.id.img_icon);
			int maxHeight = Math.min(Model.getInstance().getScreenHeight() / 20, 128);
			img.setLayoutParams(new RelativeLayout.LayoutParams(maxHeight, maxHeight));
			TextView txtRight = (TextView)view.findViewById(R.id.txt_right);
			txtRight.setText(DateTimeHelper.convertTimeToString(this, System.currentTimeMillis()));
			
			view = (View)findViewById(R.id.header);
			if(view != null){
				img = (ImageView)view.findViewById(R.id.img_icon);
				maxHeight = Math.min(Model.getInstance().getScreenHeight() / 15, 128);
				img.setLayoutParams(new RelativeLayout.LayoutParams(maxHeight, maxHeight));
				img.setImageResource(info.getIconResId());
				
				TextView txtLeft = (TextView)view.findViewById(R.id.txt_left);
				txtLeft.setText(info.getTitleResId());
				if(info.getRightTextResId() != 0){
					txtRight = (TextView)view.findViewById(R.id.txt_right);
					txtRight.setText(info.getRightTextResId());			
				}							
			}
		}
	}

	protected void load(){
	}
	
	public void onBackIconClickHandler(View view){
		onBackPressed();
	}
	
	public void onRefreshIconClickHandler(View view){
		load();
	}
	
	protected String getId() {
		return id;
	}
	
	protected void setId(String id){
		this.id = id;
	}
	
	protected final Handler notificationHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case NOTE_LOGIN_SUCCESSFULLY:
				SharedPreferences pref = getSharedPreferences(CONFIG_NAME, MODE_PRIVATE);
				SharedPreferences.Editor editor = pref.edit();
				editor.putBoolean(IS_LOGIN, true);
				editor.putString(USERNAME, Model.getInstance().getUserInfo().getUsername());
				editor.putString(PASSWORD, Model.getInstance().getUserInfo().getPassword());
				editor.commit();	            				
				
				BaseActivity.this.setContentView(R.layout.activity_home);
				BaseActivity.this.init();
				break;
				
			case NOTE_LOGIN_FAILED:
				DialogHelper.alert(BaseActivity.this, R.string.login_failed, R.string.invalid_username_pass, null);
				break;

			case NOTE_COULD_NOT_CONNECT_SERVER:
				DialogHelper.alert(BaseActivity.this, R.string.login_failed, R.string.could_not_connect_server, null);
				BaseActivity.this.setContentView(R.layout.activity_home);
				BaseActivity.this.init();
				break;				
				
			case NOTE_LOAD_JOURNEY_SUCCESSFULLY:
				break;
				
			case NOTE_LOAD_JOURNEY_FAILED:
				break;
				
			default: 
				break;
			}
		};		
	};

	/**
	 * Determines if network is available or not. 
	 * @return
	 */
	public boolean isNetworkAvailable() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if(cm == null){
			return false;
		}
		NetworkInfo info = cm.getActiveNetworkInfo();
		
		return (info != null && info.isAvailable() && info.isConnectedOrConnecting());
	}
    
}
