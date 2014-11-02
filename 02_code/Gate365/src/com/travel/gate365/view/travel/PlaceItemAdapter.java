package com.travel.gate365.view.travel;

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
import com.travel.gate365.helper.ResourceHelper;
import com.travel.gate365.model.Model;
import com.travel.gate365.model.PlaceInfo;

public class PlaceItemAdapter extends BaseAdapter {

	private PlaceInfo[] list;  
    private Context context;  
	private int layoutResId; 
	
	public PlaceItemAdapter(Context context, PlaceInfo[] list, int layoutResId) {
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
		return Long.parseLong(list[pos].getCountryId());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		PlaceInfo info = (PlaceInfo)getItem(position);
		if(convertView == null) {				        
	        holder = new Holder();
	        LayoutInflater inflate = ((Activity) context).getLayoutInflater();
	        convertView = (View)inflate.inflate(layoutResId, null);  
			ImageView icon = (ImageView)convertView.findViewById(R.id.img_icon);
			int maxHeight = Math.min(Model.getInstance().getScreenHeight() / 15, 128);
			icon.setLayoutParams(new RelativeLayout.LayoutParams(maxHeight, maxHeight));
			
			holder.icon = icon;
			
			TextView text = (TextView)convertView.findViewById(R.id.txt_country);
			holder.txtCountry = text;
			
			text = (TextView)convertView.findViewById(R.id.txt_risktype);
			holder.txtRisktype = text;
			
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}

		holder.txtCountry.setText(info.getCountryName().toUpperCase(Locale.US));
				
		int maxHeight = Math.min(Model.getInstance().getScreenHeight() / 15, 128);
		holder.icon.setLayoutParams(new RelativeLayout.LayoutParams(maxHeight, maxHeight));		
		int countryDrawableId = ResourceHelper.getDrawableId(info.getCountryISOCode().toLowerCase(Locale.US));
		if(countryDrawableId != 0){
			holder.icon.setImageResource(countryDrawableId);
		}
		
		int resId;
		int bgResId;
		if(info.getSecurityRisk().equalsIgnoreCase("LOW")){
			resId = R.string.LOW;
			bgResId = R.drawable.type_low;
		}else if(info.getSecurityRisk().equalsIgnoreCase("INSIGNIFICANT")){
			resId = R.string.INSIGNIFICANT;
			bgResId = R.drawable.type_insignificant;
		}else if(info.getSecurityRisk().equalsIgnoreCase("HIGH")){
			resId = R.string.HIGH;
			bgResId = R.drawable.type_high;
		}else if(info.getSecurityRisk().equalsIgnoreCase("EXTREME")){
			resId = R.string.EXTREME;
			bgResId = R.drawable.type_extreme;
		}else{
			resId = R.string.MEDIUM;
			bgResId = R.drawable.type_medium;
		}
		holder.txtRisktype.setText(context.getString(resId));
		holder.txtRisktype.setBackgroundResource(bgResId);
		
		return convertView;
	}

	private class Holder {
		ImageView icon;
		TextView txtCountry;
		TextView txtRisktype;
	}
	
}
