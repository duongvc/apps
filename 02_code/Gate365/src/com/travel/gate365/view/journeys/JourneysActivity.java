package com.travel.gate365.view.journeys;

import java.lang.ref.WeakReference;

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
import com.travel.gate365.helper.DialogHelper;
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
		
		load(true);		
	}

	@Override
	protected void load(boolean checkDataExist) {		
		super.load(checkDataExist);
		
		if(!isNetworkAvailable()){
			DialogHelper.alert(this, R.string.no_internet, R.string.no_internet_avaialbe, null);
			return;
		}
		
		Log.i(getId(), "checkDataExist:" + checkDataExist);
		if(checkDataExist && Model.getInstance().getJourneys().length > 0){
			android.os.Message msg = new Message();
			msg.what = BaseActivity.NOTE_LOAD_JOURNEY_SUCCESSFULLY;
			notificationHandler.sendMessage(msg);	
			return;
		}
			if(loading == null || (loading != null && !loading.isShowing())){
				loading = ProgressDialog.show(this, "", getString(R.string.loading_pls_wait)); 
				loading.show();
			}
			
			Thread thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					try { 
						JSONObject res = ServiceManager.getJourneys(Model.getInstance().getUserInfo().getUsername(), Model.getInstance().getUserInfo().getPassword());					
						Model.getInstance().paserJourney(res);
						android.os.Message msg = new Message();
						msg.what = BaseActivity.NOTE_LOAD_JOURNEY_SUCCESSFULLY;
						notificationHandler.sendMessage(msg);						
					} catch (Exception e) {
						e.printStackTrace();
						android.os.Message msg = new Message();
						msg.what = BaseActivity.NOTE_COULD_NOT_CONNECT_SERVER;
						msg.obj = e.getMessage();
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

	protected final Handler notificationHandler = new MyHandler(this);

	private static final class MyHandler extends Handler {
		private final WeakReference<JourneysActivity> mActivity;

		public MyHandler(JourneysActivity activity) {
			mActivity = new WeakReference<JourneysActivity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			loading.dismiss();
			JourneysActivity activity = mActivity.get();
			if (activity != null) {
				switch (msg.what) {
				case NOTE_LOAD_JOURNEY_SUCCESSFULLY:
					if (Model.getInstance().getJourneys().length > 0) {
						activity.txtMessage.setVisibility(View.GONE);
						activity.adapter = new JourneyItemAdapter(activity, Model.getInstance().getJourneys());
						activity.lstMenu.setAdapter(activity.adapter);
						activity.lstMenu.setOnItemClickListener(activity);
					} else {
						activity.txtMessage.setVisibility(View.VISIBLE);
					}
					break;

				case NOTE_COULD_NOT_CONNECT_SERVER:
					DialogHelper.alert(activity, "Error", (String) msg.obj);
					break;

				default:
					break;
				}
			}
		}
	}
}