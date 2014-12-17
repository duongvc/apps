package com.travel.gate365.view.alert;

import java.util.Locale;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.travel.gate365.R;
import com.travel.gate365.helper.DateTimeHelper;
import com.travel.gate365.helper.ResourceHelper;
import com.travel.gate365.model.AlertItemInfo;
import com.travel.gate365.model.Model;
import com.travel.gate365.view.BaseActivity;

public class AlertDetailActivity extends BaseActivity {

	public static final String ALERT_ID = "alertId";
	
	public AlertDetailActivity() {
		super(AlertDetailActivity.class.getSimpleName());
		
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_alert_details);
		
		init();
	}
	
	@Override
	protected void init() {
		super.init();
		
		View view = (View)findViewById(R.id.header);
		ImageView icRefresh = (ImageView)view.findViewById(R.id.img_refresh);
		icRefresh.setVisibility(View.GONE);
		
		TextView txtMessage = (TextView)findViewById(R.id.txt_message);
		txtMessage.setMovementMethod(new ScrollingMovementMethod());
		
		int alertId = (int)getIntent().getExtras().getLong(ALERT_ID);
		AlertItemInfo info = Model.getInstance().getAlert(alertId);
		
		View layoutContent = findViewById(R.id.layout_content);
		int countryDrawableId = ResourceHelper.getDrawableId(info.getPlace().getCountryISOCode().toLowerCase(Locale.US));
		if(countryDrawableId != 0){
			((ImageView)layoutContent.findViewById(R.id.img_icon)).setImageResource(countryDrawableId);
		}
		
		((TextView)layoutContent.findViewById(R.id.txt_country)).setText(info.getPlace().getCountryName().toUpperCase(Locale.US));
		((TextView)layoutContent.findViewById(R.id.txt_datetime)).setText(DateTimeHelper.convertDateStringToddMMyyyy(info.getDatetime()));
		((TextView)layoutContent.findViewById(R.id.txt_title)).setText(info.getTitle());
		((TextView)findViewById(R.id.txt_message)).setText(info.getDetail());
	}
	

}
