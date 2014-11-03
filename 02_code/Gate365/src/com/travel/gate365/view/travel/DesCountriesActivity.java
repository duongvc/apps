package com.travel.gate365.view.travel;

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
import android.widget.TextView;

import com.travel.gate365.R;
import com.travel.gate365.model.Model;
import com.travel.gate365.service.ServiceManager;
import com.travel.gate365.view.BaseActivity;

public class DesCountriesActivity extends BaseActivity implements OnItemClickListener {

	public static final String TARGET_TYPE = "target_type";
	public static final String COUNTRY_ID = "countryId";
	
	public static final byte TARGET_ADVICE = 0;
	public static final byte TARGET_RISK = 1;
	public static final byte TARGET_TIP = 2;
	
	private PlaceItemAdapter adapter;
	private TextView txtMessage;
	private ListView lstMenu;

	public DesCountriesActivity() {
		super(DesCountriesActivity.class.getSimpleName());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_countries);
		
		init();
		
	}
	
	@Override
	protected void init() {
		super.init();

		int resId;
		int headerIconResId;
		switch (getIntent().getExtras().getByte(TARGET_TYPE)) {
			case TARGET_ADVICE: resId = R.string.travel_advices; headerIconResId = R.drawable.tvadvices_menuitem_selector; break;
			case TARGET_RISK: resId = R.string.country_risk; headerIconResId = R.drawable.countryrisk_menuitem_selector; break;
			default:  resId = R.string.travel_tips; headerIconResId = R.drawable.tvtips_menuitem_selector; break;
		}
		
		View view = (View)findViewById(R.id.header);
		TextView txtLeft = (TextView)view.findViewById(R.id.txt_left);
		txtLeft.setText(resId);
		ImageView img = (ImageView)view.findViewById(R.id.img_icon);
		img.setImageResource(headerIconResId);
		
		txtMessage = (TextView)findViewById(R.id.txt_message);
		lstMenu = (ListView)findViewById(R.id.lst_country);
		
		if(Model.getInstance().getPlaces().length > 0){
			android.os.Message msg = new Message();
			msg.what = BaseActivity.NOTE_LOAD_PLACE_SUCCESSFULLY;
			notificationHandler.sendMessage(msg);									
		}else{
			load();
		}
	}
	
	@Override
	protected void load() {
		super.load();
		
		if(loading == null || (loading != null && !loading.isShowing())){
			loading = ProgressDialog.show(this, "", getString(R.string.loading_pls_wait)); 
			loading.show();
		}
		
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try { 
					JSONObject res = ServiceManager.getDetinationCountriesGrouped(Model.getInstance().getUserInfo().getUsername(), Model.getInstance().getUserInfo().getPassword());					
					loading.dismiss();
					Model.getInstance().parserPlaces(res);
					android.os.Message msg = new Message();
					msg.what = BaseActivity.NOTE_LOAD_PLACE_SUCCESSFULLY;
					notificationHandler.sendMessage(msg);						
				} catch (Exception e) {
					loading.dismiss();
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
		Intent intent;
		switch (getIntent().getExtras().getByte(TARGET_TYPE)) {
			case TARGET_ADVICE: intent = new Intent(this, AdvicesActivity.class); break;
			case TARGET_RISK: intent = new Intent(this, RisksCountryActivity.class); break;
			case TARGET_TIP: intent = new Intent(this, TipCountryActivity.class); break;
			default:return;
		}
		intent.putExtra(DesCountriesActivity.COUNTRY_ID, itemId);
		startActivity(intent);
	}
	
	protected final Handler notificationHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case BaseActivity.NOTE_LOAD_PLACE_SUCCESSFULLY:
				if(Model.getInstance().getPlaces().length > 0){
					txtMessage.setVisibility(View.GONE);
					adapter = new PlaceItemAdapter(DesCountriesActivity.this, Model.getInstance().getPlaces(), R.layout.place_item);
					lstMenu.setAdapter(adapter);
					lstMenu.setOnItemClickListener(DesCountriesActivity.this);								
				}else{
					txtMessage.setVisibility(View.VISIBLE);
				}
				break;
				
			default: 
				break;
			}
		};		
	};

	
}
