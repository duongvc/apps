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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.travel.gate365.R;
import com.travel.gate365.model.AdviceItemInfo;
import com.travel.gate365.model.JourneyItemInfo;
import com.travel.gate365.model.Model;
import com.travel.gate365.service.ServiceManager;
import com.travel.gate365.view.BaseActivity;
import com.travel.gate365.view.alert.AlertActivity;
import com.travel.gate365.view.alert.AlertItemAdapter;
import com.travel.gate365.view.journeys.JourneyDetailActivity;
import com.travel.gate365.view.journeys.JourneyItemAdapter;

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

		txtMessage = (TextView)findViewById(R.id.txt_message);
		lstMenu = (ListView)findViewById(R.id.lst_advices);
		
		load();
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
					JSONObject res = ServiceManager.getAdvices(Model.getInstance().getUserInfo().getUsername(), Model.getInstance().getUserInfo().getPassword());					
					loading.dismiss();
					Model.getInstance().parserTravelAdvices(res);
					android.os.Message msg = new Message();
					msg.what = BaseActivity.NOTE_LOAD_ADVICE_SUCCESSFULLY;
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
		//Intent intent = new Intent(this, JourneyDetailActivity.class);
		//startActivity(intent);
	}
	
	protected final Handler notificationHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case BaseActivity.NOTE_LOAD_ADVICE_SUCCESSFULLY:
				if(Model.getInstance().getAdvices().length > 0){
					txtMessage.setVisibility(View.GONE);
					adapter = new AdviceItemAdapter(AdvicesActivity.this, Model.getInstance().getAdvices(), R.layout.advice_item);
					lstMenu.setAdapter(adapter);
					lstMenu.setOnItemClickListener(AdvicesActivity.this);								
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
