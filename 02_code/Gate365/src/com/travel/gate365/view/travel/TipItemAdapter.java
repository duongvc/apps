package com.travel.gate365.view.travel;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.travel.gate365.R;
import com.travel.gate365.helper.DateTimeHelper;
import com.travel.gate365.model.ArticleItemInfo;

public class TipItemAdapter extends BaseAdapter {

	private ArticleItemInfo[] list;  
    private Context context;  
	private int layoutResId; 
	
	public TipItemAdapter(Context context, ArticleItemInfo[] list, int layoutResId) {
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
		ArticleItemInfo info = (ArticleItemInfo)getItem(position);
		if(convertView == null) {				        
	        holder = new Holder();
	        LayoutInflater inflate = ((Activity) context).getLayoutInflater();
	        convertView = (View)inflate.inflate(layoutResId, null);  
			
			TextView text = (TextView)convertView.findViewById(R.id.txt_title);
			holder.txtTitle = text;
			
			text = (TextView)convertView.findViewById(R.id.txt_datetime);
			holder.txtDateTime = text;
			
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}

		holder.txtTitle.setText(info.getTitle());
		holder.txtDateTime.setText(DateTimeHelper.convertDateStringToddMMyyyy(info.getDateTime()));
		
		return convertView;
	}

	private class Holder {
		TextView txtTitle;
		TextView txtDateTime;
	}
	
}
