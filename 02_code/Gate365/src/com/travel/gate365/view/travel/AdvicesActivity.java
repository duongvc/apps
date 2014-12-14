package com.travel.gate365.view.travel;

import java.lang.ref.WeakReference;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.travel.gate365.R;
import com.travel.gate365.helper.ResourceHelper;
import com.travel.gate365.model.Model;
import com.travel.gate365.model.PlaceInfo;
import com.travel.gate365.service.ServiceManager;
import com.travel.gate365.view.BaseActivity;

public class AdvicesActivity extends BaseActivity implements OnItemClickListener {

	private AdviceItemAdapter adapter;
	private TextView txtMessage;
	private ListView lstMenu;

	public AdvicesActivity() {
		super(AdvicesActivity.class.getSimpleName()); 
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_advices);
		
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
		lstMenu = (ListView)findViewById(R.id.lst_advices);
		
		load(true);
	}
	
	@Override
	protected void load(boolean checkDataExist) {
		super.load(checkDataExist);
		
		/*if(checkDataExist && Model.getInstance().getAdvices().length > 0){
			android.os.Message msg = new Message();
			msg.what = BaseActivity.NOTE_LOAD_ADVICE_SUCCESSFULLY;
			notificationHandler.sendMessage(msg);	
			return;
		}*/
		
		if(loading == null || (loading != null && !loading.isShowing())){
			loading = ProgressDialog.show(this, "", getString(R.string.loading_pls_wait)); 
			loading.show();
		}
		
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try { 
					JSONObject res = ServiceManager.getAdvices(Model.getInstance().getUserInfo().getUsername()
							, Model.getInstance().getUserInfo().getPassword()
							, String.valueOf(getIntent().getExtras().getLong(DesCountriesActivity.COUNTRY_ID)));
					
					Model.getInstance().parserTravelAdvices(res);
					android.os.Message msg = new Message();
					msg.what = BaseActivity.NOTE_LOAD_ADVICE_SUCCESSFULLY;
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

	protected final Handler notificationHandler = new MyHandler(this);

	private static final class MyHandler extends Handler {
		private final WeakReference<AdvicesActivity> mActivity;

		public MyHandler(AdvicesActivity activity) {
			mActivity = new WeakReference<AdvicesActivity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			AdvicesActivity activity = mActivity.get();
			if (activity != null) {
				if (loading != null) {
					loading.dismiss();
				}
				switch (msg.what) {
				case BaseActivity.NOTE_LOAD_ADVICE_SUCCESSFULLY:
					if (Model.getInstance().getAdvices().length > 0) {
						activity.txtMessage.setVisibility(View.GONE);
						activity.adapter = new AdviceItemAdapter(activity, Model.getInstance().getAdvices(), R.layout.advice_item);
						activity.lstMenu.setAdapter(activity.adapter);
						activity.lstMenu.setOnItemClickListener(activity);
					} else {
						activity.txtMessage.setVisibility(View.VISIBLE);
					}
					break;

				default:
					break;
				}
			}
		}
	}
}
