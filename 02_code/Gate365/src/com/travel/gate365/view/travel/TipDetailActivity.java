package com.travel.gate365.view.travel;

import java.util.Locale;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.travel.gate365.R;
import com.travel.gate365.helper.DateTimeHelper;
import com.travel.gate365.helper.ResourceHelper;
import com.travel.gate365.model.ArticleItemInfo;
import com.travel.gate365.model.Model;
import com.travel.gate365.model.PlaceInfo;
import com.travel.gate365.view.BaseActivity;

public class TipDetailActivity extends BaseActivity {

	public static final String TIP_ID = "tipId";
	
	public TipDetailActivity() {
		super(TipDetailActivity.class.getSimpleName()); 
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_tip_details);
		
		init();
		
		
	}
	
	@Override
	protected void init() {
		super.init();

		View layoutContent = findViewById(R.id.layout_content);
		long countryId = getIntent().getExtras().getLong(DesCountriesActivity.COUNTRY_ID);
		PlaceInfo place = Model.getInstance().getPlace(String.valueOf(countryId));
		
		int tipId = (int)getIntent().getExtras().getLong(TIP_ID);
		ArticleItemInfo info = Model.getInstance().getTip(tipId);
		
		((TextView)layoutContent.findViewById(R.id.txt_country)).setText(place.getCountryName().toUpperCase(Locale.US));		
		
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
		TextView txtRisktype = (TextView)layoutContent.findViewById(R.id.txt_risktype);
		txtRisktype.setText(getString(resId));
		txtRisktype.setBackgroundResource(bgResId);
		
		((TextView)findViewById(R.id.txt_datetime)).setText(DateTimeHelper.convertDateStringToddMMyyyy(info.getDateTime()));
		((TextView)findViewById(R.id.txt_title)).setText(info.getTitle());
		String htmlText = "<html><head>"
		          + "<style type=\"text/css\">body{color: #fff; background-color: #000;}"
		          + "</style></head>"
		          + "<body>"                          
		          + info.getDetail()
		          + "</body></html>";
		((WebView)findViewById(R.id.txt_message)).loadData(htmlText, "text/html", "utf-8");
	}
	
}
