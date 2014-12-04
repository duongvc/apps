package com.travel.gate365.view.alert;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.travel.gate365.view.journeys.JourneyDetailActivity;

public class AlertActivity extends BaseActivity implements OnItemClickListener{

	private AlertItemAdapter adapter;
	private ListView lstMenu;
	private TextView txtMessage;

	public AlertActivity() {
		super(AlertActivity.class.getSimpleName());
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_alerts);
		
		init();
		
	}

	@Override
	protected void init() {
		super.init();

		txtMessage = (TextView)findViewById(R.id.txt_message);
		lstMenu = (ListView)findViewById(R.id.lst_alerts);
		
		load();
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_refresh, menu);
		
		return true;
	}
		
	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int itemPos, long itemId) {
		Log.i(getId(), "::onItemClick - pos:" + itemPos + ", id:" + itemId);
		Intent intent = new Intent(this, AlertDetailActivity.class);
		intent.putExtra(AlertDetailActivity.ALERT_ID, itemId);
		startActivity(intent);
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
					JSONObject res = ServiceManager.getAlerts(Model.getInstance().getUserInfo().getUsername(), Model.getInstance().getUserInfo().getPassword());					
					Model.getInstance().parserTravelAlerts(res);
					android.os.Message msg = new Message();
					msg.what = BaseActivity.NOTE_LOAD_ALERT_SUCCESSFULLY;
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
	
	protected final Handler notificationHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case BaseActivity.NOTE_LOAD_ALERT_SUCCESSFULLY:
				loading.dismiss();					
				if(Model.getInstance().getAlerts().length > 0){
					txtMessage.setVisibility(View.GONE);
					adapter = new AlertItemAdapter(AlertActivity.this, Model.getInstance().getAlerts());
					lstMenu.setAdapter(adapter);
					lstMenu.setOnItemClickListener(AlertActivity.this);								
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
