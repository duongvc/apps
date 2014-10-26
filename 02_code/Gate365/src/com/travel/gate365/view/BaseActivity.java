package com.travel.gate365.view;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import com.travel.gate365.R;
import com.travel.gate365.model.ActivityInfo;
import com.travel.gate365.model.Model;

public abstract class BaseActivity extends Activity {

	private final String id;
	
	public BaseActivity(String id) {
		this.id = id;
	}

	protected void init() {
		ActivityInfo info = Model.getInstance().retrieveActivityInfo(id);
		if(info != null){
			ImageView img = (ImageView)findViewById(R.id.img_icon);
			img.setImageResource(info.getIconResId());
			TextView txtLeft = (TextView)findViewById(R.id.txt_left);
			txtLeft.setText(info.getTitleResId());
			if(info.getRightTextResId() != 0){
				TextView txtRight = (TextView)findViewById(R.id.txt_right);
				txtRight.setText(info.getRightTextResId());			
			}			
		}
	}

	public String getId() {
		return id;
	}
}
