package com.travel.gate365.view.journeys;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.travel.gate365.R;
import com.travel.gate365.model.Model;
import com.travel.gate365.service.ServiceManager;
import com.travel.gate365.view.BaseActivity;

public class JourneysActivity extends BaseActivity implements OnItemClickListener {

	private JourneyItemAdapter adapter;
	private TextView txtMessage;
	private ListView lstMenu;
	
	public JourneysActivity() {
		super(JourneysActivity.class.getSimpleName());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_journeys);
		
		init();
		
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_refresh, menu);
		
		return true;
	}
	
	@Override
	protected void init() {
		super.init();
		
		txtMessage = (TextView)findViewById(R.id.txt_message);
		lstMenu = (ListView)findViewById(R.id.lst_journeys);
		
		load();		
	}

	@Override
	protected void load() {
		super.load();
		
		if(Model.getInstance().getJourneys().length == 0){
			if(loading == null || (loading != null && !loading.isShowing())){
				loading = ProgressDialog.show(this, "", getString(R.string.loading_pls_wait)); 
				loading.show();
			}
			
			Thread thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					try { 
						JSONObject res = ServiceManager.getJourneys(Model.getInstance().getUserInfo().getUsername(), Model.getInstance().getUserInfo().getPassword());					
						loading.dismiss();
						Model.getInstance().paserJourney(res);
						android.os.Message msg = new Message();
						msg.what = BaseActivity.NOTE_LOAD_JOURNEY_SUCCESSFULLY;
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
		}else{
			android.os.Message msg = new Message();
			msg.what = BaseActivity.NOTE_LOAD_JOURNEY_SUCCESSFULLY;
			notificationHandler.sendMessage(msg);									
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int itemPos, long itemId) {
		Log.i(getId(), "::onItemSelected - pos:" + itemPos + ", id:" + itemId);
		Intent intent = new Intent(this, JourneyDetailActivity.class);
		intent.putExtra(JourneyDetailActivity.JOURNEY_ID, itemId);
		startActivity(intent);
	}
	
	protected final Handler notificationHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case NOTE_LOAD_JOURNEY_SUCCESSFULLY:
				if(Model.getInstance().getJourneys().length > 0){
					txtMessage.setVisibility(View.GONE);
					adapter = new JourneyItemAdapter(JourneysActivity.this, Model.getInstance().getJourneys());
					lstMenu.setAdapter(adapter);
					lstMenu.setOnItemClickListener(JourneysActivity.this);								
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
