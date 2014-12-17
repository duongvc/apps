package com.travel.gate365.view.journeys;

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.travel.gate365.R;
import com.travel.gate365.helper.DateTimeHelper;
import com.travel.gate365.model.FlightRoutingInfo;

public class FlightRoutingItemAdapter extends BaseAdapter {

	private FlightRoutingInfo[] list;  
    private Context context;  
	
	public FlightRoutingItemAdapter(Context context, FlightRoutingInfo[] list) {
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
		FlightRoutingInfo info = (FlightRoutingInfo)getItem(position);
		if(convertView == null) {				        
	        holder = new Holder();
	        LayoutInflater inflate = ((Activity) context).getLayoutInflater();
	        convertView = (View)inflate.inflate(R.layout.flight_routing_item, null);  
			
			TextView text = (TextView)convertView.findViewById(R.id.txt_flight_code);
			holder.txtFlightCode = text;
			
			View layout = convertView.findViewById(R.id.layout_depart);
			text = (TextView)layout.findViewById(R.id.txt_city);
			holder.txtDepartCity = text;
			text = (TextView)layout.findViewById(R.id.txt_datetime);
			holder.txtDepartDateTime = text;
			
			layout = convertView.findViewById(R.id.layout_arrival);
			text = (TextView)layout.findViewById(R.id.txt_city);
			holder.txtArrivalCity = text;
			text = (TextView)layout.findViewById(R.id.txt_datetime);
			holder.txtArrivalDateTime = text;
			
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		
		holder.txtFlightCode.setText(info.getFlightNumber().toUpperCase(Locale.US));
		holder.txtDepartCity.setText(info.getDepartureAirport().toUpperCase(Locale.US));		
		holder.txtDepartDateTime.setText(DateTimeHelper.convertDateStringToddMMyyyy(info.getDepartureDateTime()));
		holder.txtArrivalCity.setText(info.getArrivalAirport().toUpperCase(Locale.US));		
		holder.txtArrivalDateTime.setText(DateTimeHelper.convertDateStringToddMMyyyy(info.getArrivalDateTime()));
		return convertView;
	}

	private class Holder {
		TextView txtArrivalDateTime;
		TextView txtArrivalCity;
		TextView txtFlightCode;
		TextView txtDepartCity;
		TextView txtDepartDateTime;
	}
	
}
