package com.travel.gate365.view.travel;

import java.lang.ref.WeakReference;
import java.util.Locale;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.travel.gate365.R;
import com.travel.gate365.helper.DialogHelper;
import com.travel.gate365.helper.ResourceHelper;
import com.travel.gate365.model.ArticleItemInfo;
import com.travel.gate365.model.Model;
import com.travel.gate365.model.PlaceInfo;
import com.travel.gate365.service.ServiceManager;
import com.travel.gate365.view.BaseActivity;

public class RisksCountryActivity extends BaseActivity {

	private TextView txtMessage;
	private WebView webView;

	public RisksCountryActivity() {
		super(RisksCountryActivity.class.getSimpleName()); 
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_risks);
		
		init();
		
	}
	
	@Override
	protected void init() {
		super.init();

		long countryId = getIntent().getExtras().getLong(DesCountriesActivity.COUNTRY_ID);
		PlaceInfo place = Model.getInstance().getPlace(String.valueOf(countryId));
		
		View layoutContent = findViewById(R.id.layout_content);
		((TextView)layoutContent.findViewById(R.id.txt_country)).setText(place.getCountryName().toUpperCase(Locale.US));
		TextView txtRisktype = (TextView)layoutContent.findViewById(R.id.txt_risktype);
		
		int maxHeight = Math.min(Model.getInstance().getScreenHeight() / 15, 128);
		ImageView icon = (ImageView)layoutContent.findViewById(R.id.img_icon);
		icon.setLayoutParams(new RelativeLayout.LayoutParams(maxHeight, maxHeight));		
		int countryDrawableId = ResourceHelper.getDrawableId(place.getCountryISOCode().toLowerCase(Locale.US));
		if(countryDrawableId != 0){
			icon.setImageResource(countryDrawableId);
		}
		
		int resId;
		int bgResId;
		if(place.getSecurityRisk().equalsIgnoreCase("LOW")){
			resId = R.string.LOW;
			bgResId = R.drawable.type_low;
		}else if(place.getSecurityRisk().equalsIgnoreCase("INSIGNIFICANT")){
			resId = R.string.INSIGNIFICANT;
			bgResId = R.drawable.type_insignificant;
		}else if(place.getSecurityRisk().equalsIgnoreCase("HIGH")){
			resId = R.string.HIGH;
			bgResId = R.drawable.type_high;
		}else if(place.getSecurityRisk().equalsIgnoreCase("EXTREME")){
			resId = R.string.EXTREME;
			bgResId = R.drawable.type_extreme;
		}else{
			resId = R.string.MEDIUM;
			bgResId = R.drawable.type_medium;
		}
		txtRisktype.setText(getString(resId));
		txtRisktype.setBackgroundResource(bgResId);
		
		txtMessage = (TextView)findViewById(R.id.txt_message);
		webView = (WebView)findViewById(R.id.txt_details);
				
		load(true);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_refresh, menu);
		
		return true;
	}
	
	@Override
	protected void load(boolean checkDataExist) {
		super.load(checkDataExist);
		
		if(!isNetworkAvailable()){
			DialogHelper.alert(this, R.string.no_internet, R.string.no_internet_avaialbe, null);
			return;
		}
		
		if(loading == null || (loading != null && !loading.isShowing())){
			loading = ProgressDialog.show(this, "", getString(R.string.loading_pls_wait)); 
			loading.show();
		}
		
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try { 
					JSONObject res = ServiceManager.getRisks(Model.getInstance().getUserInfo().getUsername()
							, Model.getInstance().getUserInfo().getPassword()
							, String.valueOf(getIntent().getExtras().getLong(DesCountriesActivity.COUNTRY_ID)));
					
					android.os.Message msg = new Message();
					msg.what = BaseActivity.NOTE_LOAD_RISK_SUCCESSFULLY;
					msg.obj = Model.getInstance().parserCountryRisks(res);
					notificationHandler.sendMessage(msg);						
				} catch (Exception e) {
					e.printStackTrace();
					android.os.Message msg = new Message();
					msg.what = BaseActivity.NOTE_COULD_NOT_REQUEST_SERVER_DATA;
					notificationHandler.sendMessage(msg);												
				}
			}
		});
		
		thread.start();				
		
		
	}

	@Override
	public void onBackPressed() {
		if(webView.canGoBack()){
			webView.goBack();
		}else{
			super.onBackPressed();
		}
	}
	
	protected final Handler notificationHandler = new MyHandler(this);

	private static final class MyHandler extends Handler {
		private final WeakReference<RisksCountryActivity> mActivity;

		public MyHandler(RisksCountryActivity activity) {
			mActivity = new WeakReference<RisksCountryActivity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			RisksCountryActivity activity = mActivity.get();
			if (activity != null) {
				if (loading != null) {
					loading.dismiss();
				}
				switch (msg.what) {
				case BaseActivity.NOTE_LOAD_RISK_SUCCESSFULLY:
					if (msg.obj != null) {
						activity.txtMessage.setVisibility(View.GONE);

						ArticleItemInfo info = (ArticleItemInfo) msg.obj;
						String htmlText = "<html><head>" + "<style type=\"text/css\">body{color: #fff; background-color: #000;}" + "</style></head>"
								+ "<body>" + info.getDetail() + "</body></html>";
						activity.webView.loadData(htmlText, "text/html", "utf-8");

					} else {
						activity.txtMessage.setVisibility(View.VISIBLE);
					}
					break;

				case NOTE_COULD_NOT_REQUEST_SERVER_DATA:
					DialogHelper.alert(activity, R.string.load_failed, R.string.could_not_connect_server);
					break;
					
				default:
					break;
				}
			}
		}
	}
}
