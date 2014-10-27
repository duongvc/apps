package com.travel.gate365.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.travel.gate365.R;
import com.travel.gate365.model.ActivityInfo;
import com.travel.gate365.model.Model;

public abstract class BaseActivity extends Activity {

	private final String id;
	protected static ProgressDialog loading;	
	
	public BaseActivity(String id) {
		this.id = id;
	}

	protected void init() {
		ActivityInfo info = Model.getInstance().retrieveActivityInfo(id);
		if(info != null){
			ImageView img = (ImageView)findViewById(R.id.img_icon);
			int maxHeight = Model.getInstance().getScreenHeight() / 8;
			img.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, maxHeight));
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
