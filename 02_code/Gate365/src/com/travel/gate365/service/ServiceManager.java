package com.travel.gate365.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.travel.gate365.model.Model;

import android.annotation.SuppressLint;
import android.util.Base64;
import android.util.Log;

public class ServiceManager {

	private static final String TAG = ServiceManager.class.getSimpleName();
	
	private static final int TIMEOUT_CONNECTION = 80000;
	private static final int TIMEOUT_SOCKET = 60000; // 1 minute

	private static final boolean IS_TEST_EVR = false;//testing username = ux00287, pass = 1
	//public static final boolean USER_AUTH_REQUIRED = true;

	private static final String SERVER_URL = IS_TEST_EVR ? "http://archivist.it/gate365/BB/"	: "https://gate365.internal.unicredit.eu/BB/";
	private static final String URL_LOGIN = SERVER_URL + "Login.aspx";
	private static final String URL_JOURNEYS = SERVER_URL + "GetTravelDestinations.aspx?type=detail";
	private static final String URL_ALERTS = SERVER_URL + "GetTravelAlerts.aspx";
	private static final String URL_PLACES = SERVER_URL + "GetTravelDestinations.aspx?type=group";
	private static final String URL_ADVICES = SERVER_URL + "GetTravelAdvices.aspx";
	private static final String URL_RISKS = SERVER_URL + "GetCountryRisk.aspx";
	private static final String URL_TIPS = SERVER_URL + "GetTravelTips.aspx";
	private static final String URL_GET_CONFIGURATIONS = SERVER_URL + "GetConfiguration.aspx";
	private static final String URL_GET_SEND_LOCATION = SERVER_URL + "UpdatePaxPosition.aspx";
	
	public static final String SUCCESS_STATUS = "success";
	
	public ServiceManager() {
	}

	public static JSONObject login(String username, String password) throws Exception {
		String pax = username.replace('\\', '_');
		String url = URL_LOGIN;	
		if (url.indexOf("=") != -1) {
			url += "&";
		} else {
			url += "?";
		}
		url += "pax=" + pax;
		
		return connect(url, null, TIMEOUT_SOCKET, TIMEOUT_CONNECTION);
	}
	
	public static JSONObject getJourneys(String username, String password) throws Exception{
		String pax = username.replace('\\', '_');
		String url = URL_JOURNEYS;
		if (url.indexOf("=") != -1) {
			url += "&";
		} else {
			url += "?";
		}
		url += "pax=" + pax;
		return connect(url, null, TIMEOUT_SOCKET, TIMEOUT_CONNECTION);
	}
	
	public static JSONObject getAlerts(String username, String password) throws Exception {
		String pax = username.replace('\\', '_');
		String url = URL_ALERTS;
		if (url.indexOf("=") != -1) {
			url += "&";
		} else {
			url += "?";
		}
		url += "pax=" + pax;
		return connect(url, null, TIMEOUT_SOCKET, TIMEOUT_CONNECTION);
	}
	
	public static JSONObject getAdvices(String username, String password, String countryId) throws Exception {
		String pax = username.replace('\\', '_');
		String url = URL_ADVICES + "?cid=" + countryId;
		if (url.indexOf("=") != -1) {
			url += "&";
		} else {
			url += "?";
		}
		url += "pax=" + pax;
		return connect(url, null, TIMEOUT_SOCKET, TIMEOUT_CONNECTION);
	}
	
	public static JSONObject getDetinationCountriesGrouped(String username, String password) throws Exception {
		String pax = username.replace('\\', '_');
		String url = URL_PLACES;
		if (url.indexOf("=") != -1) {
			url += "&";
		} else {
			url += "?";
		}
		url += "pax=" + pax;
		return connect(url, null, TIMEOUT_SOCKET, TIMEOUT_CONNECTION);
	}
	
	public static JSONObject getRisks(String username, String password, String countryId) throws Exception {
		String pax = username.replace('\\', '_');
		String url = URL_RISKS + "?cid=" + countryId;
		if (url.indexOf("=") != -1) {
			url += "&";
		} else {
			url += "?";
		}
		url += "pax=" + pax;
		return connect(url, null, TIMEOUT_SOCKET, TIMEOUT_CONNECTION);
	}

	public static JSONObject getTips(String username, String password, String countryId) throws Exception {
		String pax = username.replace('\\', '_');
		String url = URL_TIPS + "?cid=" + countryId;
		if (url.indexOf("=") != -1) {
			url += "&";
		} else {
			url += "?";
		}
		url += "pax=" + pax;
		return connect(url, null, TIMEOUT_SOCKET, TIMEOUT_CONNECTION);
	}
	
	public static JSONObject getConfiguration(String username, String password) throws Exception {
		String pax = username.replace('\\', '_');
		String url = URL_GET_CONFIGURATIONS;
		if (url.indexOf("=") != -1) {
			url += "&";
		} else {
			url += "?";
		}
		url += "pax=" + pax;
		return connect(url, null, TIMEOUT_SOCKET, TIMEOUT_CONNECTION);
	}

	public static JSONObject sendLocation(String username, String password, final double latitude, final double longitude) throws Exception {
		String pax = username.replace('\\', '_');
		String url = URL_GET_SEND_LOCATION + "?lat=" + latitude + "&long=" + longitude + "&pax=" + pax;
		return connect(url, null, TIMEOUT_SOCKET, TIMEOUT_CONNECTION);
	}	
	
	/**
	 * Use this method to perform a HTTP GET request and retrieve a JSON object out of an URL url, and its parameters
	 * params.<br/>
	 * Timeout socket and connection are respectively set to TIMEOUT_SOCKET and TIMEOUT_CONNECTION
	 * @param url The URL
	 * @param params The parameters to use along with the URL
	 * @return The requested JSON object, null if an exception was raised.
	 * @throws Exception 
	 */
	public static JSONObject connect(String url, ArrayList<String> params) throws Exception {
		return connect(url, params, TIMEOUT_SOCKET, TIMEOUT_CONNECTION);
	}

	/**
	 * Use this method to perform a HTTP GET request and retrieve a JSON object out of an URL url, and its parameters
	 * params, given specific socket and connection timeouts. <br/>
	 * @param url The URL
	 * @param params The parameters to use along with the URL
	 * @param timeoutSocket The timeout socket
	 * @param timeoutConnection The timeout connection
	 * @return The requested JSON object
	 * @throws Exception 
	 */
	private static JSONObject connect(String url, ArrayList<String> params, int timeoutSocket, int timeoutConnection) throws Exception {
		// prepare the params to append it with url
		String combinedParams = "";
		if ((params != null) && !params.isEmpty()) {
			while (!params.isEmpty()) {
				try {
					combinedParams += "/" + params.remove(0) + ":" + URLEncoder.encode(params.remove(0), "UTF-8");
				} catch (Exception e) {
				}
			}
		} 

		Log.d(TAG + "-connect", url + combinedParams);

		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		// Set the default socket timeout (SO_TIMEOUT)
		// in milliseconds which is the timeout for waiting for data.
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

		final Model model = Model.getInstance();
		String pax = model.getUserInfo().getUsername();
		String password = model.getUserInfo().getPassword();
		String login = pax + ":" + password;

		String base64EncodedCredentials = Base64.encodeToString(login.getBytes(), Base64.NO_WRAP);
		HttpGet request = new HttpGet(url + combinedParams);
		request.addHeader("Authorization", "Basic " + base64EncodedCredentials);

		DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
		HttpResponse response = httpClient.execute(request);

		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			String text = EntityUtils.toString(response.getEntity());
			Log.d(TAG + "-connect", "response - " + text);
			return new JSONObject(text);
		} else {
			throw new Exception("Server Response Code: " + response.getStatusLine().getStatusCode() + " - "
					+ response.getStatusLine().getReasonPhrase());
		}
	}

	/**
	 * Use this method to post a HTTP request.
	 * @param url A URL to post the request to.
	 * @param params Parameters to inject into the URL
	 * @return An associated JSON encoded string response
	 * @throws JSONException 
	 */
	public static JSONObject HTTPPost(String url, String params) throws JSONException {
		String jsonData = null;
		HttpPost post = new HttpPost(url);
		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		int timeoutConnection = TIMEOUT_CONNECTION;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		// Set the default socket timeout (SO_TIMEOUT)
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = TIMEOUT_SOCKET;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		DefaultHttpClient client = new MyHttpClient(httpParameters);
		// Execute the POST call and obtain the response
		HttpResponse getResponse = null;
		post.setHeader("content-type", "application/json; charset=utf-8");
		HttpEntity responseEntity = null;
		try {
			post.setEntity(new StringEntity(params));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		try {
			getResponse = client.execute(post);
			responseEntity = getResponse.getEntity();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			jsonData = EntityUtils.toString(responseEntity, "utf-8");
			Log.i(TAG, "HTTPPost - jsonData:" + jsonData);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new JSONObject(jsonData);
	}
	
	/**
	 * A custom DefaultHttpClient using CustomSSLSocketFactory and to be used when posting a HTTP request.
	 *
	 */
	private static class MyHttpClient extends DefaultHttpClient {

		public MyHttpClient(HttpParams params) {
			super(params);
		}

		@SuppressLint("TrulyRandom")
		@Override
		protected ClientConnectionManager createClientConnectionManager() {

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			// Register for port 443 our SSLSocketFactory with our keystore
			// to the ConnectionManager
			// Create a trust manager that does not validate certificate chains
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return new java.security.cert.X509Certificate[] {};
				}

				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}
			} };

			// Install the all-trusting trust manager
			try {
				KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
				trustStore.load(null, null);

				SSLSocketFactory sf = new CustomSSLSocketFactory(trustStore);
				sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				SSLContext sc = SSLContext.getInstance("TLS");
				sc.init(null, trustAllCerts, new java.security.SecureRandom());
				registry.register(new Scheme("https", sf, 443));
			} catch (Exception w) {
				w.printStackTrace();
			}
			return new SingleClientConnManager(getParams(), registry);
		}
	}

	/**
	 * A custom SSLSocketFactory. SSLSocketFactory can be used to validate the identity of the HTTPS server against a list of trusted certificates
	 * and to authenticate to the HTTPS server using a private key. 
	 *
	 */
	private static class CustomSSLSocketFactory extends SSLSocketFactory {
		SSLContext sslContext = SSLContext.getInstance("TLS");

		public CustomSSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException,
				UnrecoverableKeyException {
			super(truststore);

			TrustManager tm = new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};

			sslContext.init(null, new TrustManager[] { tm }, null);
		}

		@Override
		public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
			return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
		}

		@Override
		public Socket createSocket() throws IOException {
			return sslContext.getSocketFactory().createSocket();
		}
	}
	
}
