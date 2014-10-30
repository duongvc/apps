package com.travel.gate365.view.alert;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.travel.gate365.R;
import com.travel.gate365.view.BaseActivity;

public class AlertDetailActivity extends BaseActivity {

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
		
		TextView txtMessage = (TextView)findViewById(R.id.txt_message);
		txtMessage.setMovementMethod(new ScrollingMovementMethod());
	}
	

}
