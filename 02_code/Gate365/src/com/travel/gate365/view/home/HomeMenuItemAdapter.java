package com.travel.gate365.view.home;

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

import com.travel.gate365.R;
import com.travel.gate365.model.MenuItemInfo;
import com.travel.gate365.model.Model;

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
			int maxHeight = Math.round((Model.getInstance().getScreenHeight() - context.getResources().getDimensionPixelSize(R.dimen.titlebar_height)) / numRows);
			icon.setLayoutParams(new RelativeLayout.LayoutParams(maxHeight, maxHeight));
			TextView text = (TextView)convertView.findViewById(R.id.txt_left);
						
			holder.icon = icon;
			holder.text = text;
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		setEnabled(convertView, info.isActive());
		
		holder.icon.setImageResource(info.getIconResId());
		holder.text.setText(info.getTextResId());
		
		return convertView;
	}

	@Override
	public boolean isEnabled(int position) {
		MenuItemInfo info = (MenuItemInfo)getItem(position);
		return info.isActive();
	}
	
	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}
	
	public void setEnabled(View view, boolean enabled) {
		if(view.isEnabled() == enabled)
	        return;

	    /*float from = enabled ? .8f : 1.0f;
	    float to = enabled ? 1.0f : .8f;

	    AlphaAnimation a = new AlphaAnimation(from, to);

	    a.setDuration(500);
	    a.setFillAfter(true);*/

	    view.setEnabled(enabled);
	    //view.startAnimation(a);		
	    view.setClickable(enabled);
	}     	
	private class Holder {
		ImageView icon;
		TextView text;
	}
	
}
