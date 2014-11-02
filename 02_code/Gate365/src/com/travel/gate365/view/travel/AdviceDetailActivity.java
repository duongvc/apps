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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.travel.gate365.R;
import com.travel.gate365.helper.ResourceHelper;
import com.travel.gate365.model.AdviceItemInfo;
import com.travel.gate365.model.Model;
import com.travel.gate365.model.PlaceInfo;
import com.travel.gate365.service.ServiceManager;
import com.travel.gate365.view.BaseActivity;

public class AdviceDetailActivity extends BaseActivity {

	public static final String ADVICE_ID = "adviceId";
	
	public AdviceDetailActivity() {
		super(AdviceDetailActivity.class.getSimpleName()); 
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_advice_details);
		
		init();
		
		
	}
	
	@Override
	protected void init() {
		super.init();

		long countryId = getIntent().getExtras().getLong(AdvicesActivity.COUNTRY_ID);
		PlaceInfo place = Model.getInstance().getPlace(String.valueOf(countryId));
		
		int adviceId = (int)getIntent().getExtras().getLong(ADVICE_ID);
		AdviceItemInfo info = Model.getInstance().getAdvice(adviceId);
		
		((TextView)findViewById(R.id.txt_country)).setText(place.getCountryName().toUpperCase(Locale.US));
		TextView txtRisktype = (TextView)findViewById(R.id.txt_risktype);
		
		int maxHeight = Math.min(Model.getInstance().getScreenHeight() / 15, 128);
		ImageView icon = (ImageView)findViewById(R.id.img_icon);
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
		
	}
	
	protected final Handler notificationHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				
			default: 
				break;
			}
		};		
	};
	
}
