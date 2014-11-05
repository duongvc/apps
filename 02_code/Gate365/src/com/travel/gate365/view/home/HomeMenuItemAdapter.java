package com.travel.gate365.view.home;

import com.travel.gate365.R;
import com.travel.gate365.model.MenuItemInfo;
import com.travel.gate365.model.Model;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HomeMenuItemAdapter extends BaseAdapter {

	private final MenuItemInfo[] list;  
    private final Context context;
	
	public HomeMenuItemAdapter(final Context context, final MenuItemInfo[] list) {
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
		MenuItemInfo info = (MenuItemInfo)getItem(position);
		if(convertView == null) {				        
	        holder = new Holder();
	        LayoutInflater inflate = ((Activity) context).getLayoutInflater();
	        convertView = (View)inflate.inflate(R.layout.home_menu_item, null);  
			ImageView icon = (ImageView)convertView.findViewById(R.id.img_icon);
			int numRows; 
			if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
				numRows = 6;
			}else{
				numRows = 3; 
			}
			int maxHeight = (Model.getInstance().getScreenHeight() - context.getResources().getDimensionPixelSize(R.dimen.titlebar_height)) / numRows;
			icon.setLayoutParams(new RelativeLayout.LayoutParams(maxHeight, maxHeight));
			icon.setImageResource(info.getIconResId());
			TextView text = (TextView)convertView.findViewById(R.id.txt_left);
			text.setText(info.getTextResId());
						
			holder.icon = icon;
			holder.text = text;
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
			holder.icon.setImageResource(info.getIconResId());
			holder.text.setText(info.getTextResId());
		}
		
		return convertView;
	}

	private class Holder {
		ImageView icon;
		TextView text;
	}
	
}
