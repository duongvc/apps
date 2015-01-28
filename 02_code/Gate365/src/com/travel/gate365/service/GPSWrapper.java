package com.travel.gate365.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.json.JSONObject;

import com.travel.gate365.Gate365Activity;
import com.travel.gate365.model.Model;
import com.travel.gate365.view.BaseActivity;
import com.travel.gate365.view.SettingsActivity;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

public class GPSWrapper extends Service {

	private static final String LOGTAG = GPSWrapper.class.getSimpleName();
	private static GPSWrapper sInstance;
	private LocationManager locationManager;
	private LocationListener locationListener;
	//The minimum time between updates in milliseconds
	private SettingsActivity settingsActivity;
	private static long frequency = 160000;
	private static long lastTime = 0;

	public GPSWrapper() {
		sInstance = this;
	}

	public static GPSWrapper getInstance() {
		return sInstance;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(LOGTAG, "onCreate()");
		SharedPreferences pref = getSharedPreferences(BaseActivity.CONFIG_NAME, MODE_PRIVATE);
		boolean gpstracking = pref.getBoolean(BaseActivity.IS_GPS_TRACKING, Gate365Activity.fakeMode || false);
		Log.d(LOGTAG, "gpstracking:" + gpstracking);
		if (gpstracking) {
			startTracking();
		}
	}

	public void startTracking() {
		stopTracking();

		Log.d(LOGTAG, "startTracking()");
		if (locationManager == null) {
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		}

		if (locationManager != null) {
			if (locationListener == null) {
				locationListener = new LocationListener() {
					public void onLocationChanged(Location location) {
						makeUseOfNewLocation(location);
					}

					public void onStatusChanged(String provider, int status, Bundle extras) {
					}

					public void onProviderEnabled(String provider) {
					}

					public void onProviderDisabled(String provider) {
					}
				};
			}

			SharedPreferences pref = getSharedPreferences(BaseActivity.CONFIG_NAME, MODE_PRIVATE);
			frequency = pref.getInt(BaseActivity.GPS_FREQUENCY, 160000);
			Log.d(LOGTAG, "GPS frequency " + frequency);

			//getting GPS status
			boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
			//if GPS Enabled get lat/long using GPS Services
			if (isGPSEnabled) {
				Log.d(LOGTAG, "GPS Enabled");
				locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, frequency, 0, locationListener);
				Location l = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				if (l != null) {
					makeUseOfNewLocation(l);
				}
			}

			boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			if (isNetworkEnabled) {
				Log.d(LOGTAG, "Network Location Provider enabled");
				locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, frequency, 0, locationListener);
				Location l = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				if (l != null) {
					makeUseOfNewLocation(l);
				}
			}

			if (!isGPSEnabled && !isNetworkEnabled) {
				// Provider not enabled, prompt user to enable it
				if (settingsActivity != null) {
					settingsActivity.gpsIsOff();
					Toast.makeText(settingsActivity, "Please turn GPS on first!", Toast.LENGTH_LONG).show();
					Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					settingsActivity.startActivity(myIntent);
				}
			}
		}
	}

	private void makeUseOfNewLocation(final Location location) {
		if ((System.currentTimeMillis() - lastTime ) < frequency ) {
			Log.d(LOGTAG, "makeUseOfNewLocation - not new");
			return;
		}
		lastTime = System.currentTimeMillis();
		Log.d(LOGTAG, "makeUseOfNewLocation - " + location.getLatitude() + "," + location.getLongitude());
		final Model model = Model.getInstance();
		model.setLastLattitude(String.valueOf(location.getLatitude()));
		model.setLastLongtitude(String.valueOf(location.getLongitude()));
		try {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.getDefault());
			Model.getInstance().setLastTimeSent(dateFormat.format(cal.getTime()));
			SharedPreferences pref;
			SharedPreferences.Editor editor;
			pref = getSharedPreferences(BaseActivity.CONFIG_NAME, MODE_PRIVATE);
			editor = pref.edit();
			editor.putString(BaseActivity.LAST_SENT, Model.getInstance().getLastTimeSent());
			editor.putString(BaseActivity.GPS_LAST_LATITUDE, Model.getInstance().getLastLattitude());
			editor.putString(BaseActivity.GPS_LAST_LONGTITUDE, Model.getInstance().getLastLongtitude());
			editor.commit();
			Log.d(LOGTAG, "makeUseOfNewLocation - committed:" + location.getLatitude() + "," + location.getLongitude());

			AsyncTask<Void, Void, JSONObject> task = new AsyncTask<Void, Void, JSONObject>() {

				protected JSONObject doInBackground(Void... urls) {
					try {
						return ServiceManager.sendLocation(model.getUserInfo().getUsername(), model.getUserInfo().getPassword(),
								location.getLatitude(), location.getLongitude());
					} catch (Exception e) {
						return null;
					}
				}

				protected void onPostExecute(JSONObject result) {
				}
			};
			task.execute();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(LOGTAG, "makeUseOfNewLocation - error:" + e.getMessage());
		}
		if (settingsActivity != null) {
			Log.d(LOGTAG, "makeUseOfNewLocation:" + location.getLatitude() + "," + location.getLongitude());
			settingsActivity.onNewLocation(location);
		}
	}

	public void stopTracking() {
		Log.d(LOGTAG, "stopTracking()");
		lastTime = 0;
		if (locationManager != null && locationListener != null) {
			locationManager.removeUpdates(locationListener);
		}
	}

	@Override
	public void onDestroy() {
		stopTracking();
		super.onDestroy();
	}

	public void setSettingsActivity(SettingsActivity settingsActivity) {
		this.settingsActivity = settingsActivity;
	}
}