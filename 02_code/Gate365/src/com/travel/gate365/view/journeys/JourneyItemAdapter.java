package com.travel.gate365.view.journeys;

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
import com.travel.gate365.model.JourneyItemInfo;
import com.travel.gate365.model.Model;

public class JourneyItemAdapter extends BaseAdapter {

	private JourneyItemInfo[] list;  
    private Context context;  
	
	public JourneyItemAdapter(Context context, JourneyItemInfo[] list) {
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
		JourneyItemInfo info = (JourneyItemInfo)getItem(position);
		if(convertView == null) {				        
	        holder = new Holder();
	        LayoutInflater inflate = ((Activity) context).getLayoutInflater();
	        convertView = (View)inflate.inflate(R.layout.journey_item, null);  
			ImageView icon = (ImageView)convertView.findViewById(R.id.img_icon);			
			int maxHeight = Math.min(Model.getInstance().getScreenHeight() / 15, 128);
			icon.setLayoutParams(new RelativeLayout.LayoutParams(maxHeight, maxHeight));
			holder.icon = icon;
			
			//icon = (ImageView)convertView.findViewById(R.id.img_arrow);
			//icon.setLayoutParams(new RelativeLayout.LayoutParams(maxHeight, maxHeight));
			if(info.getDes() != null){				
				TextView text = (TextView)convertView.findViewById(R.id.txt_country);
				holder.txtCountry = text;
				
				text = (TextView)convertView.findViewById(R.id.txt_city);
				holder.txtCity = text;
				
				text = (TextView)convertView.findViewById(R.id.txt_datetime);
				holder.txtDate = text;
				
				text = (TextView)convertView.findViewById(R.id.txt_codetime);
				holder.txtCodeTime = text;
				
				text = (TextView)convertView.findViewById(R.id.txt_risktype);
				holder.txtRisktype = text;
				
				convertView.setTag(holder);
			}
		}else{
			holder = (Holder) convertView.getTag();
		}
		
		if(info.getDes() != null){
			String[] arrDateTime = DateTimeHelper.convertDateStringToWWW_MMMddyyyy(context, info.getDate(), info.getLeavingTime());
			holder.txtDate.setText(arrDateTime[0]);
			holder.txtCountry.setText(info.getDes().getCountryName().toUpperCase(Locale.US));
			holder.txtCity.setText(info.getDes().getLocationName().toUpperCase(Locale.US));
			holder.txtCodeTime.setText(arrDateTime[1] + ", PNR " + info.getpNRCode());
			int resId;
			int bgResId;
			int iconResId;
			if(info.getDes().getSecurityRisk().equalsIgnoreCase("LOW")){
				resId = R.string.LOW;
				bgResId = R.drawable.type_low;
				iconResId = R.drawable._take_off_plane;
			}else if(info.getDes().getSecurityRisk().equalsIgnoreCase("INSIGNIFICANT")){
				resId = R.string.INSIGNIFICANT;
				bgResId = R.drawable.type_insignificant;
				iconResId = R.drawable._take_off_plane;
			}else if(info.getDes().getSecurityRisk().equalsIgnoreCase("HIGH")){
				resId = R.string.HIGH;
				bgResId = R.drawable.type_high;
				iconResId = R.drawable._take_off_plane2;
			}else if(info.getDes().getSecurityRisk().equalsIgnoreCase("EXTREME")){
				resId = R.string.EXTREME;
				bgResId = R.drawable.type_extreme;
				iconResId = R.drawable._take_off_plane2;
			}else{
				resId = R.string.MEDIUM;
				bgResId = R.drawable.type_medium;
				iconResId = R.drawable._take_off_plane;
			}
			holder.icon.setImageResource(iconResId);
			holder.txtRisktype.setText(context.getString(resId));
			holder.txtRisktype.setBackgroundResource(bgResId);
		}
		
		return convertView;
	}

	private class Holder {
		ImageView icon;
		TextView txtCountry;
		TextView txtCity;
		TextView txtRisktype;
		TextView txtDate;
		TextView txtCodeTime;
	}
	
}
