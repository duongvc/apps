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
import com.travel.gate365.view.SettingsActivity;
import com.travel.gate365.view.alert.AlertActivity;
import com.travel.gate365.view.alert.AlertDetailActivity;
import com.travel.gate365.view.journeys.JourneyDetailActivity;
import com.travel.gate365.view.journeys.JourneysActivity;
import com.travel.gate365.view.travel.AdviceDetailActivity;
import com.travel.gate365.view.travel.AdvicesActivity;
import com.travel.gate365.view.travel.DesCountriesActivity;
import com.travel.gate365.view.travel.RisksCountryActivity;
import com.travel.gate365.view.travel.TipCountryActivity;
import com.travel.gate365.view.travel.TipDetailActivity;

public class Model {

	private static Model sInstance;
	private final ActivityInfo[] arrActivityInfo = {
		new ActivityInfo(Gate365Activity.class.getSimpleName(), R.drawable.ic_0, R.string.login_U, 0)
		, new ActivityInfo(JourneysActivity.class.getSimpleName(), R.drawable.journeys_menuitem_selector, R.string.journeys, R.string.destinations)
		, new ActivityInfo(JourneyDetailActivity.class.getSimpleName(), R.drawable.journeys_menuitem_selector, R.string.journey_details, 0)
		, new ActivityInfo(AlertActivity.class.getSimpleName(), R.drawable.tvalerts_menuitem_selector, R.string.travel_alerts, 0)
		, new ActivityInfo(AlertDetailActivity.class.getSimpleName(), R.drawable.tvalerts_menuitem_selector, R.string.alert_details, 0)
		, new ActivityInfo(AdvicesActivity.class.getSimpleName(), R.drawable.tvadvices_menuitem_selector, R.string.travel_advices, 0)
		, new ActivityInfo(DesCountriesActivity.class.getSimpleName(), R.drawable.tvadvices_menuitem_selector, R.string.travel_advices, 0)		
		, new ActivityInfo(AdviceDetailActivity.class.getSimpleName(), R.drawable.tvadvices_menuitem_selector, R.string.travel_advices, 0)
		, new ActivityInfo(TipCountryActivity.class.getSimpleName(), R.drawable.tvtips_menuitem_selector, R.string.travel_tips, 0)
		, new ActivityInfo(TipDetailActivity.class.getSimpleName(), R.drawable.tvtips_menuitem_selector, R.string.travel_tips, 0)
		, new ActivityInfo(RisksCountryActivity.class.getSimpleName(), R.drawable.countryrisk_menuitem_selector, R.string.country_risk, 0)
		, new ActivityInfo(SettingsActivity.class.getSimpleName(), R.drawable.settings_menuitem_selector, R.string.settings, 0)
	};
	
	public final static MenuItemInfo[] MENU_LIST = {new MenuItemInfo(MenuItemInfo.MENU_ITEM_JOURNEYS, R.drawable.journeys_menuitem_selector, R.string.journeys, true)
		, new MenuItemInfo(MenuItemInfo.MENU_ITEM_TRAVEL_ALERTS, R.drawable.tvalerts_menuitem_selector, R.string.travel_alerts, true)
		, new MenuItemInfo(MenuItemInfo.MENU_ITEM_TRAVEL_ADVICES, R.drawable.tvadvices_menuitem_selector, R.string.travel_advices, false)
		, new MenuItemInfo(MenuItemInfo.MENU_ITEM_COUNTRY_RISK, R.drawable.countryrisk_menuitem_selector, R.string.country_risk, false)
		, new MenuItemInfo(MenuItemInfo.MENU_ITEM_TRAVEL_TIPS, R.drawable.tvtips_menuitem_selector, R.string.travel_tips, false)
		, new MenuItemInfo(MenuItemInfo.MENU_ITEM_SETTINGS, R.drawable.settings_menuitem_selector, R.string.settings, false)};
	
	private boolean isLogin;
	private UserInfo userInfo;
	
	private DisplayMetrics metrics;
	private JourneyItemInfo[] journeys;
	private AlertItemInfo[] alerts;
	private ArticleItemInfo[] advices;
	private PlaceInfo[] places;
	private IntegerGenerator intGenerator;
	private ArticleItemInfo[] tips;
	private int locationTrackingInterval;
	private String lastTimeSent;
	private String lastLattitude;
	private String lastLongtitude;
	private boolean isLocationTrackingEnabled;
	
	private Model() {
		journeys = new JourneyItemInfo[0];
		alerts = new AlertItemInfo[0];
		places = new PlaceInfo[0]; 
		advices = new ArticleItemInfo[0];
		intGenerator = new IntegerGenerator();
		tips = new ArticleItemInfo[0];
		
		userInfo = new UserInfo("", "");
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
	public JourneyItemInfo[] paserJourney(JSONObject obj) throws JSONException{
		JSONArray arr = obj.getJSONArray("ResultSet");
		JourneyItemInfo[] journeys;
		if (arr != null) {
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
			this.journeys = journeys;
		}//arr != null
		
		
		return this.journeys;
	}
	
	public AlertItemInfo[] parserTravelAlerts(JSONObject obj) throws JSONException{
		Log.i(Model.class.getSimpleName(), "---------getAlerts status: " + obj.getString("Status"));
		JSONArray arr = obj.getJSONArray("ResultSet");
		AlertItemInfo[] alerts;
		if (arr != null) {
			Log.i(Model.class.getSimpleName(), "-----getAlerts Array lenght: " + arr.length());
			alerts = new AlertItemInfo[arr.length()];
			for (int a = 0; a < arr.length(); a++) {
				JSONObject jsAlert = arr.getJSONObject(a);
				JSONObject jsSource = jsAlert.getJSONObject("Location");
				PlaceInfo ldo = new PlaceInfo(jsSource.getString("CountryId"), jsSource.getString("CountryISOCode"),
						jsSource.getString("CountryName"), jsSource.getString("LocationName"), jsSource.getString("SecurityRisk"));
				alerts[a] = new AlertItemInfo(intGenerator.generate(), jsAlert.getString("DateTime"), jsAlert.getString("Title"), jsAlert.getString("Detail"), ldo);
			}
			this.alerts = alerts;
		}
		
		return this.alerts;		
	}
	
	public ArticleItemInfo[] parserTravelAdvices(JSONObject obj) throws JSONException {
		Log.i(Model.class.getSimpleName(), "---------getAdvices status: " + obj.getString("Status"));
		JSONArray arr = obj.getJSONArray("ResultSet");
		ArticleItemInfo[] advices;
		if (arr != null) {
			Log.i(Model.class.getSimpleName(), "-----getAdvices Array lenght: " + arr.length());
			advices = new ArticleItemInfo[arr.length()];
			for (int a = 0; a < arr.length(); a++) {
				JSONObject jsAdvice = arr.getJSONObject(a);
				advices[a] = new ArticleItemInfo(intGenerator.generate(), jsAdvice.getString("DateTime"), jsAdvice.getString("Title"), jsAdvice.getString("Detail"));
			}
			Log.i(Model.class.getSimpleName(), "-----this.advices.lenght: " + this.advices.length);
			this.advices = advices;
		}
		
		return this.advices;		
	}
	
	public PlaceInfo[] parserPlaces(JSONObject obj) throws JSONException {
		JSONArray arr = obj.getJSONArray("ResultSet");
		PlaceInfo[] places;
		if (arr != null) {
			Log.i(Model.class.getSimpleName(), "-----getDetinationCountriesGrouped Array lenght: " + arr.length());
			places = new PlaceInfo[arr.length()];
			for (int a = 0; a < arr.length(); a++) {
				JSONObject jsSource = arr.getJSONObject(a);
				places[a] = new PlaceInfo(jsSource.getString("CountryId"), jsSource.getString("CountryISOCode"),
						jsSource.getString("CountryName"), jsSource.getString("LocationName"), jsSource.getString("SecurityRisk"));
			}
			this.places = places;
		}
		return this.places;
	}
	
	public ArticleItemInfo parserCountryRisks(JSONObject obj) throws JSONException {
		Log.i(Model.class.getSimpleName(), "---------getRisks status: " + obj.getString("Status"));
		JSONObject jsArticle = obj.getJSONObject("ResultSet");
		if (jsArticle != null) {
			return new ArticleItemInfo(intGenerator.generate(), jsArticle.getString("DateTime"), jsArticle.getString("Title"), jsArticle.getString("Detail"));
		}
		return null;
	}
	
	public ArticleItemInfo[] parserCountryTips(JSONObject obj) throws JSONException {
		Log.i(Model.class.getSimpleName(), "---------getTips status: " + obj.getString("Status"));
		JSONArray arr = obj.getJSONArray("ResultSet");
		ArticleItemInfo[] tips;
		if (arr != null) {
			Log.i(Model.class.getSimpleName(), "-----getTips Array lenght: " + arr.length());
			tips = new ArticleItemInfo[arr.length()];
			for (int a = 0; a < arr.length(); a++) {
				JSONObject jsAdvice = arr.getJSONObject(a);
				tips[a] = new ArticleItemInfo(intGenerator.generate(), jsAdvice.getString("DateTime"), jsAdvice.getString("Title"), jsAdvice.getString("Detail"));
			}
			this.tips = tips;
		}
		return this.tips;
	}

	public void paserConfiguration(JSONObject obj) throws JSONException {
		final int frequency = Integer.parseInt(obj.getString("gps_duration")) / 1000;
		if (frequency != locationTrackingInterval) {
			locationTrackingInterval = frequency;
			if (isLocationTrackingEnabled()) {
				//post a global event to restart the tracking
			}
		}
	}
	
	public boolean isLocationTrackingEnabled() {
		return true;
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
	
	public AlertItemInfo[] getAlerts(){
		return alerts;
	}
	
	public AlertItemInfo getAlert(int alertId) {
		for (int i = 0; i < alerts.length; i++) {
			if(alerts[i].getId() == alertId){
				return alerts[i];
			}
		}
		return null;
	}

	public ArticleItemInfo[] getAdvices() {
		return advices;
	}

	public ArticleItemInfo getAdvice(int adviceId) {
		for (int i = 0; i < advices.length; i++) {
			if(advices[i].getId() == adviceId){
				return advices[i];
			}
		}
		return null;
	}

	public PlaceInfo[] getPlaces() {
		return places;
	}

	public PlaceInfo getPlace(String placeId) {
		for (int i = 0; i < places.length; i++) {
			if(places[i].getCountryId().equalsIgnoreCase(placeId)){
				return places[i];
			}
		}
		return null;
	}

	public ArticleItemInfo getTip(int tipId){
		for (int i = 0; i < tips.length; i++) {
			if(tips[i].getId() == tipId){
				return tips[i];
			}
		}
		return null;
	}

	public int getLocationTrackingInterval() {
		return locationTrackingInterval;
	}

	public String getLastTimeSent() {
		return lastTimeSent;
	}

	public void setLastTimeSent(String lastTimeSent) {
		this.lastTimeSent = lastTimeSent;
	}

	public String getLastLattitude() {
		return lastLattitude;
	}

	public void setLastLattitude(String lastLattitude) {
		this.lastLattitude = lastLattitude;
	}

	public String getLastLongtitude() {
		return lastLongtitude;
	}

	public void setLastLongtitude(String lastLongtitude) {
		this.lastLongtitude = lastLongtitude;
	}

	public void setLocationTrackingEnabled(boolean isLocationTrackingEnabled) {
		this.isLocationTrackingEnabled = isLocationTrackingEnabled;
	}

}
