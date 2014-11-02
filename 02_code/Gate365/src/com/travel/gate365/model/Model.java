package com.travel.gate365.model;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;

import com.googlecode.jeneratedata.numbers.IntegerGenerator;
import com.travel.gate365.Gate365Activity;
import com.travel.gate365.R;
import com.travel.gate365.view.alert.AlertActivity;
import com.travel.gate365.view.alert.AlertDetailActivity;
import com.travel.gate365.view.journeys.JourneyDetailActivity;
import com.travel.gate365.view.journeys.JourneysActivity;
import com.travel.gate365.view.tip.TipCountryActivity;
import com.travel.gate365.view.travel.AdvicesActivity;

public class Model {

	private static Model sInstance;
	private final ActivityInfo[] arrActivityInfo = {
		new ActivityInfo(Gate365Activity.class.getSimpleName(), R.drawable.ic_0, R.string.login_U, 0)
		, new ActivityInfo(JourneysActivity.class.getSimpleName(), R.drawable.journeys_menuitem_selector, R.string.journeys, R.string.destinations)
		, new ActivityInfo(JourneyDetailActivity.class.getSimpleName(), R.drawable.ic_0, R.string.journey_details, 0)
		, new ActivityInfo(AlertActivity.class.getSimpleName(), R.drawable.ic_1, R.string.travel_alerts, 0)
		, new ActivityInfo(AlertDetailActivity.class.getSimpleName(), R.drawable.ic_1, R.string.alert_details, 0)
		, new ActivityInfo(AdvicesActivity.class.getSimpleName(), R.drawable.ic_2, R.string.travel_advices, 0)
		, new ActivityInfo(TipCountryActivity.class.getSimpleName(), R.drawable.ic_5, R.string.travel_tips, 0)
	};
	private boolean isLogin;
	private UserInfo userInfo;
	
	private DisplayMetrics metrics;
	private JourneyItemInfo[] journeys;
	
	private Model() {
	}

	public static Model getInstance() {
		if(sInstance == null){
			sInstance = new Model();
		}
		return sInstance;
	}
	
	public void init(Activity context){
    	Display display = context.getWindowManager().getDefaultDisplay();
        metrics = new DisplayMetrics(); 
        display.getMetrics(metrics); 		
	}

	public ActivityInfo retrieveActivityInfo(String id) {
		for(int i = 0; i < arrActivityInfo.length; i++){
			ActivityInfo info = arrActivityInfo[i]; 
			if(info.getId().equalsIgnoreCase(id)){
				return info;
			}
		}
		return null;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
		if(isLogin()){
			userInfo = new UserInfo("", "");
		}
	}
	
	public void paserLoginInfo(String username, String password){
		if(isLogin()){
			userInfo = new UserInfo(username, password);
		}		
	}
	
	/**
	 * obj - {"Status":"200","ResultSet":[{"Source":{"CountryId":null,"CountryName":"GAMBIA","CountryISOCode":null,"LocationName":"BANJUL","SecurityRisk":""},"TravelCode":"7001252924","FlightRoutings":[{"FlightNumber":"HDE234","CarrierName":"PPdkjf","DepartureAirport":"Treviso Centrale","DepartureDateTime":"31/10/2014 10:42:00","ArrivalAirport":"Montebelluna","ArrivalDateTime":"03/04/2015 16:10:00"},{"FlightNumber":"AZH031","CarrierName":"ATL","DepartureAirport":"Montebelluna","DepartureDateTime":"31/10/2014 10:42:00","ArrivalAirport":"Treviso Centrale","ArrivalDateTime":"03/04/2015 12:55:00"},{"FlightNumber":"AZH031","CarrierName":"ATL","DepartureAirport":"Montebelluna","DepartureDateTime":"31/10/2014 10:42:00","ArrivalAirport":"Treviso Centrale","ArrivalDateTime":"28/03/2015 21:15:00"},{"FlightNumber":"HDE234","CarrierName":"PPdkjf","DepartureAirport":"Treviso Centrale","DepartureDateTime":"31/10/2014 10:42:00","ArrivalAirport":"Montebelluna","ArrivalDateTime":"31/03/2015 15:40:00"}],"Date":"31/10/2014 10:42:00","LeavingTime":"10:42:00","Destination":{"CountryId":null,"CountryName":"BURKINA FASO","CountryISOCode":null,"LocationName":"OUAGADOUGOU","SecurityRisk":"MEDIUM"},"PNRCode":"3IXVIB"},{"Source":{"CountryId":null,"CountryName":"Senegal","CountryISOCode":null,"LocationName":"DAKAR","SecurityRisk":""},"TravelCode":"7001252924","FlightRoutings":[{"FlightNumber":"HDE234","CarrierName":"PPdkjf","DepartureAirport":"Treviso Centrale","DepartureDateTime":"31/10/2014 10:42:00","ArrivalAirport":"Montebelluna","ArrivalDateTime":"03/04/2015 16:10:00"},{"FlightNumber":"AZH031","CarrierName":"ATL","DepartureAirport":"Montebelluna","DepartureDateTime":"31/10/2014 10:42:00","ArrivalAirport":"Treviso Centrale","ArrivalDateTime":"03/04/2015 12:55:00"},{"FlightNumber":"AZH031","CarrierName":"ATL","DepartureAirport":"Montebelluna","DepartureDateTime":"31/10/2014 10:42:00","ArrivalAirport":"Treviso Centrale","ArrivalDateTime":"28/03/2015 21:15:00"},{"FlightNumber":"HDE234","CarrierName":"PPdkjf","DepartureAirport":"Treviso Centrale","DepartureDateTime":"31/10/2014 10:42:00","ArrivalAirport":"Montebelluna","ArrivalDateTime":"31/03/2015 15:40:00"}],"Date":"31/10/2014 10:42:00","LeavingTime":"10:42:00","Destination":{"CountryId":null,"CountryName":"Burkina Faso","CountryISOCode":null,"LocationName":"OUAGADOUGOU","SecurityRisk":"MEDIUM"},"PNRCode":"3IXVIB"},{"Source":{"CountryId":null,"CountryName":"Congo","CountryISOCode":null,"LocationName":"KINSHASA","SecurityRisk":""},"TravelCode":"7001252924","FlightRoutings":[{"FlightNumber":"HDE234","CarrierName":"PPdkjf","DepartureAirport":"Treviso Centrale","DepartureDateTime":"31/10/2014 10:42:00","ArrivalAirport":"Montebelluna","ArrivalDateTime":"03/04/2015 16:10:00"},{"FlightNumber":"AZH031","CarrierName":"ATL","DepartureAirport":"Montebelluna","DepartureDateTime":"31/10/2014 10:42:00","ArrivalAirport":"Treviso Centrale","ArrivalDateTime":"03/04/2015 12:55:00"},{"FlightNumber":"AZH031","CarrierName":"ATL","DepartureAirport":"Montebelluna","DepartureDateTime":"31/10/2014 10:42:00","ArrivalAirport":"Treviso Centrale","ArrivalDateTime":"28/03/2015 21:15:00"},{"FlightNumber":"HDE234","CarrierName":"PPdkjf","DepartureAirport":"Treviso Centrale","DepartureDateTime":"31/10/2014 10:42:00","ArrivalAirport":"Montebelluna","ArrivalDateTime":"31/03/2015 15:40:00"}],"Date":"31/10/2014 10:42:00","LeavingTime":"10:42:00","Destination":{"CountryId":null,"CountryName":"Cameroon","CountryISOCode":null,"LocationName":"DOUALA","SecurityRisk":"MEDIUM"},"PNRCode":"X22ASG"},{"Source":{"CountryId":null,"CountryName":"Kenya","CountryISOCode":null,"LocationName":"NAIROBI JOMO KENYATTA","SecurityRisk":""},"TravelCode":"7001252924","FlightRoutings":[{"FlightNumber":"HDE234","CarrierName":"PPdkjf","DepartureAirport":"Treviso Centrale","DepartureDateTime":"31/10/2014 10:42:00","ArrivalAirport":"Montebelluna","ArrivalDateTime":"03/04/2015 16:10:00"},{"FlightNumber":"AZH031","CarrierName":"ATL","DepartureAirport":"Montebelluna","DepartureDateTime":"31/10/2014 10:42:00","ArrivalAirpo
	 * @param obj
	 * @throws JSONException
	 */
	public void paserJourney(JSONObject obj) throws JSONException{
		JSONArray arr = obj.getJSONArray("ResultSet");
		JourneyItemInfo[] journeys = null;
		if (arr != null) {
			IntegerGenerator intGenerator = new IntegerGenerator();
			Log.i(Model.class.getSimpleName(), "-----getJourneys Array lenght: " + arr.length());
			journeys = new JourneyItemInfo[arr.length()];
			for (int a = 0; a < arr.length(); a++) {
				JSONObject travlDetail = arr.getJSONObject(a);
				//Log.i(Model.class.getSimpleName(), "travlDetail["+ a +"]:" + travlDetail.toString());
				JSONObject jsSource = travlDetail.getJSONObject("Source");
				JSONObject jsDestination = travlDetail.getJSONObject("Destination");
				JSONArray jsRoutings = travlDetail.getJSONArray("FlightRoutings");

				PlaceInfo s = new PlaceInfo(jsSource.getString("CountryName"), jsSource.getString("LocationName"), jsSource.getString("SecurityRisk"));

				PlaceInfo d = new PlaceInfo(jsDestination.getString("CountryName"), jsDestination.getString("LocationName"), jsDestination.getString("SecurityRisk"));

				JourneyItemInfo j = new JourneyItemInfo(intGenerator.generate(), travlDetail.getString("Date"), travlDetail.getString("LeavingTime"), travlDetail.getString("PNRCode"), s, d);

				if (jsRoutings != null) {
					FlightRoutingInfo[] flightRoutings = new FlightRoutingInfo[jsRoutings.length()];
					for (int route = 0; route < jsRoutings.length(); route++) {
						JSONObject routeObj = jsRoutings.getJSONObject(route);
						flightRoutings[route] = new FlightRoutingInfo(intGenerator.generate(), routeObj.getString("FlightNumber"),
								routeObj.getString("CarrierName"), routeObj.getString("DepartureAirport"),
								routeObj.getString("DepartureDateTime"), routeObj.getString("ArrivalAirport"),
								routeObj.getString("ArrivalDateTime"));
					}
					j.setFlightRoutings(flightRoutings);
				}
				journeys[a] = j;
			}//for loop
		}//arr != null
		
		this.journeys = journeys;
	}
	
	public UserInfo getUserInfo(){
		return userInfo;
	}
		
	public int getScreenWidth(){
		float density = metrics.density;
		return Float.valueOf(metrics.widthPixels / density).intValue(); 		
	}
	
	public int getScreenHeight(){
		float density = metrics.density;
		return Float.valueOf(metrics.heightPixels / density).intValue(); 
	}

	public JourneyItemInfo[] getJourneys() {
		return journeys;
	}

	public JourneyItemInfo getJourney(int journeyId) {
		for (int i = 0; i < journeys.length; i++) {
			if(journeys[i].getId() == journeyId){
				return journeys[i];
			}
		}
		return null;
	}
	
}
