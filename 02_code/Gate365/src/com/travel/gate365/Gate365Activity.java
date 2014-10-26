package com.travel.gate365;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.travel.gate365.model.Model;
import com.travel.gate365.view.BaseActivity;
import com.travel.gate365.view.HomeActivity;
import com.travel.gate365.view.LoginActivity;

public class Gate365Activity extends BaseActivity {

	public Gate365Activity() {
		super(Gate365Activity.class.getSimpleName());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!Model.getInstance().isLogin()){
			finish();
			//Intent intent = new Intent(Gate365Activity.this, LoginActivity.class);
			Intent intent = new Intent(Gate365Activity.this, HomeActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_gate365, menu);
		return true;
	}

}
