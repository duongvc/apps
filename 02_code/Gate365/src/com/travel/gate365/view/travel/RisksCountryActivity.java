package com.travel.gate365.view.travel;

import java.util.Locale;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.travel.gate365.R;
import com.travel.gate365.helper.DateTimeHelper;
import com.travel.gate365.helper.ResourceHelper;
import com.travel.gate365.model.ArticleItemInfo;
import com.travel.gate365.model.Model;
import com.travel.gate365.model.PlaceInfo;
import com.travel.gate365.service.ServiceManager;
import com.travel.gate365.view.BaseActivity;

public class RisksCountryActivity  extends BaseActivity implements OnItemClickListener {

	private TextView txtMessage;

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
		
		load(true);
	}
	
	@Override
	protected void load(boolean checkDataExist) {
		super.load(checkDataExist);
		
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
					msg.what = BaseActivity.NOTE_COULD_NOT_CONNECT_SERVER;
					notificationHandler.sendMessage(msg);												
				}
			}
		});
		
		thread.start();				
		
		
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int itemPos, long itemId) {
		Log.i(getId(), "::onItemSelected - pos:" + itemPos + ", id:" + itemId);
		Intent intent = new Intent(this, AdviceDetailActivity.class);
		intent.putExtra(AdviceDetailActivity.ADVICE_ID, itemId);
		intent.putExtra(DesCountriesActivity.COUNTRY_ID, getIntent().getExtras().getLong(DesCountriesActivity.COUNTRY_ID));
		startActivity(intent);
	}
	
	protected final Handler notificationHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case BaseActivity.NOTE_LOAD_RISK_SUCCESSFULLY:
				if(msg.obj != null){
					txtMessage.setVisibility(View.GONE);
					
					ArticleItemInfo info = (ArticleItemInfo)msg.obj;
					String htmlText = "<html><head>"
					          + "<style type=\"text/css\">body{color: #fff; background-color: #000;}"
					          + "</style></head>"
					          + "<body>"                          
					          + info.getDetail()
					          + "</body></html>";
					((WebView)findViewById(R.id.txt_details)).loadData(htmlText, "text/html", "utf-8");
					
				}else{					
					txtMessage.setVisibility(View.VISIBLE);
				}
				loading.dismiss();					
				break;
				
			default: 
				break;
			}
		};		
	};

}
