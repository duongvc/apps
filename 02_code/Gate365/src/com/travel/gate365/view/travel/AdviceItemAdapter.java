package com.travel.gate365.view.travel;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.travel.gate365.R;
import com.travel.gate365.model.AdviceItemInfo;

public class AdviceItemAdapter extends BaseAdapter {

	private AdviceItemInfo[] list;  
    private Context context;  
	private int layoutResId; 
	
	public AdviceItemAdapter(Context context, AdviceItemInfo[] list, int layoutResId) {
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
		return (long)list[pos].getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		AdviceItemInfo info = (AdviceItemInfo)getItem(position);
		if(convertView == null) {				        
	        holder = new Holder();
	        LayoutInflater inflate = ((Activity) context).getLayoutInflater();
	        convertView = (View)inflate.inflate(layoutResId, null);  
			
			TextView text = (TextView)convertView.findViewById(R.id.txt_title);
			holder.txtTitle = text;
			
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}

		holder.txtTitle.setText(info.getTitle());
		
		return convertView;
	}

	private class Holder {
		TextView txtTitle;
	}
	
}
