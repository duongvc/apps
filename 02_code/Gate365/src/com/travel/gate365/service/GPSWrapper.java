package com.travel.gate365.service;

import com.travel.gate365.Gate365Activity;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class GPSWrapper extends Service {

	private static final String LOGTAG = GPSWrapper.class.getSimpleName();
	private static final int REQUIRED_GPS_ACCURACY = 20; //metres
	private static GPSWrapper sInstance;
	private LocationManager locationManager;
	private LocationListener locationListener;
	public Location currentBestLocation = null;
	private int frequency = 5000;
	private Gate365Activity activity;
	
	public GPSWrapper() {
		sInstance = this;
	}

	public static GPSWrapper getInstance(){
		if(sInstance == null){
			sInstance = new GPSWrapper();
		}
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
		startTracking();
	}

	public void init(Gate365Activity activity, int frequency){
		this.frequency = frequency;
	}
	
	public void startTracking() {
		Log.d(LOGTAG, "startTracking()");
		if(locationManager == null){
			try{
				locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);			
			}catch(Exception ex){
				
			}
			if(locationManager != null){
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

				locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);			
			}			
		}
	}

	private void makeUseOfNewLocation(Location location) {
		if (isBetterLocation(location)) {
			currentBestLocation = location; 
			if(activity != null){
				activity.onNewLocation(currentBestLocation);
			}
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
		boolean isSignificantlyNewer = timeDelta > frequency;
		boolean isSignificantlyOlder = timeDelta < -frequency;
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
		locationManager.removeUpdates(locationListener);
	}
	
	@Override
	public void onDestroy() {
		locationManager.removeUpdates(locationListener);
		super.onDestroy();
	}

}
