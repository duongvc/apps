package com.travel.gate365.view.travel;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.travel.gate365.R;
import com.travel.gate365.model.AdviceItemInfo;
import com.travel.gate365.model.AlertItemInfo;
import com.travel.gate365.model.Model;

public class CountryItemAdapter extends BaseAdapter {

	private AdviceItemInfo[] list;  
    private Context context;  
	private int layoutResId; 
	
	public CountryItemAdapter(Context context, AdviceItemInfo[] list, int layoutResId) {
		this.context = context;
		this.list = list;
		this.layoutResId = layoutResId;
	}

	@Override
	public int getCount() {
		return list.length;
	}

	@Override
	public Object getItem(int pos) {
		return list[pos];
	}

	@Override
	public long getItemId(int pos) {
		return list[pos].getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		AdviceItemInfo info = (AdviceItemInfo)getItem(position);
		if(convertView == null) {				        
	        holder = new Holder();
	        LayoutInflater inflate = ((Activity) context).getLayoutInflater();
	        convertView = (View)inflate.inflate(layoutResId, null);  
			ImageView icon = (ImageView)convertView.findViewById(R.id.img_icon);
			int maxHeight = Math.min(Model.getInstance().getScreenHeight() / 15, 128);
			icon.setLayoutParams(new RelativeLayout.LayoutParams(maxHeight, maxHeight));
			
			//icon = (ImageView)convertView.findViewById(R.id.img_arrow);
			//icon.setLayoutParams(new RelativeLayout.LayoutParams(maxHeight, maxHeight));
			//TextView text = (TextView)convertView.findViewById(R.id.txt_left);
			//ext.setText(info.getTextResId());
			
			//holder.icon = icon;
			//holder.text = text;
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
			//holder.icon.setImageResource(info.getIconResId());
			//holder.text.setText(info.getTextResId());
		}
		
		return convertView;
	}

	private class Holder {
		ImageView icon;
		TextView text;
	}
	
}
