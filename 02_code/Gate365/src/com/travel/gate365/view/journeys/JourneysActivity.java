package com.travel.gate365.view.journeys;

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
import android.widget.ListView;
import android.widget.TextView;

import com.travel.gate365.R;
import com.travel.gate365.model.Model;
import com.travel.gate365.service.ServiceManager;
import com.travel.gate365.view.BaseActivity;

public class JourneysActivity extends BaseActivity implements OnItemClickListener {

	private JourneyItemAdapter adapter;

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
	protected void init() {
		super.init();
		
		TextView txtMessage = (TextView)findViewById(R.id.txt_message);
		txtMessage.setVisibility(View.GONE);
		
		if(loading == null || (loading != null && !loading.isShowing())){
			loading = ProgressDialog.show(this, "", ""); 
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
				ListView lstMenu = (ListView)findViewById(R.id.lst_journeys);
				adapter = new JourneyItemAdapter(JourneysActivity.this, Model.getInstance().getJourneys());
				lstMenu.setAdapter(adapter);
				lstMenu.setOnItemClickListener(JourneysActivity.this);			
				break;
				
			default: 
				break;
			}
		};		
	};
	
}
