package com.travel.gate365.view.alert;

import java.lang.reflect.Field;
import java.util.Locale;

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
import com.travel.gate365.helper.DateTimeHelper;
import com.travel.gate365.helper.ResourceHelper;
import com.travel.gate365.model.AlertItemInfo;
import com.travel.gate365.model.Model;

public class AlertItemAdapter extends BaseAdapter {

	private AlertItemInfo[] list;  
    private Context context;  
	
	public AlertItemAdapter(Context context, AlertItemInfo[] list) {
		this.context = context;
		this.list = list;
		
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
		AlertItemInfo info = (AlertItemInfo)getItem(position);
		if(convertView == null) {				        
	        holder = new Holder();
	        LayoutInflater inflate = ((Activity) context).getLayoutInflater();
	        convertView = (View)inflate.inflate(R.layout.alert_item, null);  
			ImageView icon = (ImageView)convertView.findViewById(R.id.img_icon);
			
			holder.icon = icon;
			
			TextView text = (TextView)convertView.findViewById(R.id.txt_country);
			holder.txtCountry = text;
			
			text = (TextView)convertView.findViewById(R.id.txt_datetime);
			holder.txtDatetime = text;
			
			text = (TextView)convertView.findViewById(R.id.txt_title);
			holder.txtTitle = text;
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		holder.txtCountry.setText(info.getPlace().getCountryName().toUpperCase(Locale.US));
		holder.txtDatetime.setText(DateTimeHelper.convertDateStringToddMMyyyy(info.getDatetime()));
		holder.txtTitle.setText(info.getTitle());
				
		int maxHeight = Math.min(Model.getInstance().getScreenHeight() / 15, 128);
		holder.icon.setLayoutParams(new RelativeLayout.LayoutParams(maxHeight, maxHeight));		
		int countryDrawableId = ResourceHelper.getDrawableId(info.getPlace().getCountryISOCode().toLowerCase(Locale.US));
		if(countryDrawableId != 0){
			holder.icon.setImageResource(countryDrawableId);
		}
		return convertView;
	}

	private class Holder {
		ImageView icon;
		TextView txtCountry;
		TextView txtDatetime;
		TextView txtTitle;
	}
	
}
