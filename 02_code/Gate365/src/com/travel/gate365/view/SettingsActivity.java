package com.travel.gate365.view;

import java.lang.ref.WeakReference;

import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.travel.gate365.R;
import com.travel.gate365.model.Model;
import com.travel.gate365.service.GPSWrapper;

public class SettingsActivity extends BaseActivity {

	private TextView txtFrequency, txtLastTime, txtLastLatitude, txtLastLongtitude;	
	private CheckBox chkGpstracking;
	
	public SettingsActivity() {
		super(SettingsActivity.class.getSimpleName()); 
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_settings);
		
		init();
		
		if (GPSWrapper.getInstance() != null) {
			GPSWrapper.getInstance().setSettingsActivity(this);
		}
	}
	
	@Override
	protected void init() {
		super.init();

		load(false);
		
		chkGpstracking = (CheckBox)findViewById(R.id.chk_gpstracking);
		chkGpstracking.setChecked(Model.getInstance().isLocationTrackingEnabled());
		chkGpstracking.setOnCheckedChangeListener(onCheckedChangeListener);
		
		txtFrequency = ((TextView)findViewById(R.id.txt_frequency));
		txtFrequency.setText(getString(R.string.frequency)+ ": " + Model.getInstance().getLocationTrackingInterval() + " " + getString(R.string.seconds));
		txtLastTime = ((TextView)findViewById(R.id.txt_last_time));
		txtLastTime.setText(getString(R.string.last_time)+ ": " + Model.getInstance().getLastTimeSent());
		txtLastLatitude = ((TextView)findViewById(R.id.txt_last_latitude));
		txtLastLatitude.setText(getString(R.string.last_lattitude)+ ": " + Model.getInstance().getLastLattitude());
		txtLastLongtitude = ((TextView)findViewById(R.id.txt_last_longtitude));
		txtLastLongtitude.setText(getString(R.string.last_longtitude)+ ": " + Model.getInstance().getLastLongtitude());
		
		ImageView icRefresh = (ImageView)findViewById(R.id.img_refresh);
		icRefresh.setVisibility(View.GONE);
	}
	
	public void onLogoutButtonHandler(View view){
		setResult(RESULT_LOGOUT);
		finish();
	}
	
	private OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			Log.d(getId(), "isChecked:" + isChecked);
			Model.getInstance().setLocationTrackingEnabled(isChecked);
			SharedPreferences pref;
			SharedPreferences.Editor editor;
			pref = getSharedPreferences(CONFIG_NAME, MODE_PRIVATE);
			editor = pref.edit();
			editor.putBoolean(IS_GPS_TRACKING, Model.getInstance().isLocationTrackingEnabled());
			editor.commit();
			if(isChecked){
				GPSWrapper.getInstance().startTracking();
			}else{
				GPSWrapper.getInstance().stopTracking();
			}
		}
	};
	
	protected final Handler notificationHandler = new MyHandler(this);

	private static final class MyHandler extends Handler {
		private final WeakReference<SettingsActivity> mActivity;

		public MyHandler(SettingsActivity activity) {
			mActivity = new WeakReference<SettingsActivity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			Log.i(SettingsActivity.class.getSimpleName(), "msg.what:" + msg.what);
			SettingsActivity activity = mActivity.get();
			if (activity != null) {
				if (loading != null) {
					loading.dismiss();
				}
				switch (msg.what) {
				case NOTE_LOAD_CONFIGURATION_SUCCESSFULLY:
					activity.txtFrequency.setText(activity.getString(R.string.frequency)+ ": " + Model.getInstance().getLocationTrackingInterval() + " " + activity.getString(R.string.seconds));
					break;

				case NOTE_LOCATION_CHANGED:
					activity.txtLastTime.setText(activity.getString(R.string.last_time)+ ": " + Model.getInstance().getLastTimeSent());
					activity.txtLastLatitude.setText(activity.getString(R.string.last_lattitude)+ ": " + Model.getInstance().getLastLattitude());
					activity.txtLastLongtitude.setText(activity.getString(R.string.last_longtitude)+ ": " + Model.getInstance().getLastLongtitude());
					break;
					
				default:
					break;
				}
			}
		}
	}

	public void onNewLocation(Location location){
		Log.d(getId(), "onNewLocation - " + location.getLatitude() + "," + location.getLongitude());
		android.os.Message msg = new Message();
		msg.what = BaseActivity.NOTE_LOCATION_CHANGED;
		notificationHandler.sendMessage(msg);				
	}
}
