package com.travel.gate365.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.travel.gate365.R;
import com.travel.gate365.model.ActivityInfo;
import com.travel.gate365.model.Model;
import com.travel.gate365.util.DialogManager;

public abstract class BaseActivity extends Activity {

	protected static final int NOTE_LOGIN_SUCCESSFULLY = 1;
	protected static final int NOTE_LOGIN_FAILED = 2;
	protected static final int NOTE_COULD_NOT_CONNECT_SERVER = 3;
	
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
			Model.getInstance().setLogin(false);
			BaseActivity.this.setContentView(R.layout.activity_login);
			BaseActivity.this.init();			
			break;

		case R.id.menu_close:
			finish();
			break;

		default:
			break;
		}		
		return super.onMenuItemSelected(featureId, item);
	}

	protected void init() {
		ActivityInfo info = Model.getInstance().retrieveActivityInfo(id);
		if(info != null){
			View view = (View)findViewById(R.id.titlebar);
			ImageView img = (ImageView)view.findViewById(R.id.img_icon);
			int maxHeight = Math.min(Model.getInstance().getScreenHeight() / 20, 128);
			img.setLayoutParams(new RelativeLayout.LayoutParams(maxHeight, maxHeight));
			TextView txtRight = (TextView)view.findViewById(R.id.txt_right);
			txtRight.setText(convertToString(System.currentTimeMillis()));
			
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
				BaseActivity.this.setContentView(R.layout.activity_home);
				BaseActivity.this.init();
				break;
				
			case NOTE_LOGIN_FAILED:
				DialogManager.alert(BaseActivity.this, R.string.login_failed, R.string.invalid_username_pass, null);
				break;

			case NOTE_COULD_NOT_CONNECT_SERVER:
				DialogManager.alert(BaseActivity.this, R.string.login_failed, R.string.could_not_connect_server, null);
				BaseActivity.this.setContentView(R.layout.activity_home);
				BaseActivity.this.init();
				break;				
				
			default: 
				break;
			}
		};		
	};

    public synchronized String convertToString(long pDateTime){
    	Date date = new Date(pDateTime);
    	SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd - hh:mm a");
		StringBuilder stDate = new StringBuilder().append(pad(date.getDate()))
				.append("-").append(pad(date.getYear() + 1900))
				.append("-").append(date.getMonth() + 1)
				.append(" - ").append(pad(date.getHours()))
				.append(":").append(pad(date.getMinutes()));
				//.append(":").append(pad(date.getSeconds()));
		String [] dayOfWeeks = getResources().getStringArray(R.array.daysOfWeek);
				
		//return stDate.toString();
		return dayOfWeeks[date.getDay()].toString() + ", " + dateFormat.format(date);
    }

	/**
	 * Add padding to numbers less than ten
	 * @param pC
	 * @return
	 */
    public static synchronized String pad(int pC) {
        if (pC >= 10)
            return String.valueOf(pC);
        else
            return "0" + String.valueOf(pC);
    }	
    
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
