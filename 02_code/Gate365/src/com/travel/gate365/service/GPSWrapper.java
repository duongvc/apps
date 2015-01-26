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
	private static final int REQUIRED_GPS_ACCURACY = 20; //metres
	private static GPSWrapper sInstance;
	private LocationManager locationManager;
	private LocationListener locationListener;
	public Location currentBestLocation = null;
	//The minimum time between updates in milliseconds
	private int frequency = 160000; // 160 seconds by default

	private SettingsActivity settingsActivity;
	
	public GPSWrapper() {
		sInstance = this;
	}

	public static GPSWrapper getInstance(){
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
		frequency = pref.getInt(BaseActivity.GPS_FREQUENCY, 160000);
		Log.d(LOGTAG, "gpstracking:" + gpstracking);
		if (gpstracking) {
			init(frequency);
			startTracking();
		}
	}

	public void init(int frequency){
		this.frequency = frequency;
	}

	public void startTracking() {
		Log.d(LOGTAG, "startTracking()");
		if (locationManager == null) {
			try {
				locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		if (locationManager != null) {
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

			//getting GPS status
			boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
			//if GPS Enabled get lat/long using GPS Services
			if (isGPSEnabled) {
				locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, frequency, 0, locationListener);

				Log.d(LOGTAG, "GPS Enabled");

				currentBestLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
				if (currentBestLocation != null) {
					makeUseOfNewLocation(currentBestLocation);
				}
			} else {
				// only use network location provider if gps is not enabled
				boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
				if (isNetworkEnabled) {
					locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, frequency, 0, locationListener);

					Log.d(LOGTAG, "Network Location Provider enabled");

					currentBestLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					if (currentBestLocation != null) {
						makeUseOfNewLocation(currentBestLocation);
					}
				}
				// Provider not enabled, prompt user to enable it
				if (settingsActivity != null) {
					Toast.makeText(settingsActivity, "Please turn GPS on!", Toast.LENGTH_LONG).show();
					Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					settingsActivity.startActivity(myIntent);
				}
			}
		}
	}

	private void makeUseOfNewLocation(final Location location) {
		currentBestLocation = location;
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
		if(settingsActivity != null){
			Log.d(LOGTAG, "makeUseOfNewLocation:" + currentBestLocation.getLatitude() + "," + currentBestLocation.getLongitude());
			settingsActivity.onNewLocation(currentBestLocation);
		}
	}

	protected boolean isBetterLocation(Location location) {
		if (!location.hasAccuracy() || location.getAccuracy() > REQUIRED_GPS_ACCURACY) {
			return false;
		}

		if (currentBestLocation == null) {
			return true;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = location.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > 5000;
		boolean isSignificantlyOlder = timeDelta < -5000;
		boolean isNewer = timeDelta > 0;

		// If it's been more than five seconds since the last location, use the new location
		// because the user has likely moved
		if (isSignificantlyNewer) {
			return true;
			// If the new location is more than five seconds older, it must be worse
		} else if (isSignificantlyOlder) {
			return false;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 5;

		// Determine location quality using a combination of timeliness and accuracy
		if (isMoreAccurate) {
			return true;
		} else if (isNewer && !isLessAccurate) {
			return true;
		} else if (isNewer && !isSignificantlyLessAccurate) {
			return true;
		}
		return false;
	}

	public void stopTracking() {
		Log.d(LOGTAG, "stopTracking()");
		if (locationManager != null && locationListener != null) {
			locationManager.removeUpdates(locationListener);
		}
	}
	
	@Override
	public void onDestroy() {
		locationManager.removeUpdates(locationListener);
		super.onDestroy();
	}


	public void setSettingsActivity(SettingsActivity settingsActivity) {
		this.settingsActivity = settingsActivity;
	}
}
