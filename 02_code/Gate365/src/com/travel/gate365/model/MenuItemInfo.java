package com.travel.gate365.model;

public class MenuItemInfo {

	public static final byte MENU_ITEM_JOURNEYS 		= 0;
	public static final byte MENU_ITEM_TRAVEL_ALERTS 	= 1;
	public static final byte MENU_ITEM_TRAVEL_ADVICES 	= 2;
	public static final byte MENU_ITEM_COUNTRY_RISK 	= 3;
	public static final byte MENU_ITEM_TRAVEL_TIPS 		= 4;
	public static final byte MENU_ITEM_SETTINGS 		= 5;
	
	
	private final int iconResId;
	private final int textResId;
	private final int id;

	public MenuItemInfo(final int id, final int iconResId, final int textResId) {
		this.id = id;
		this.iconResId = iconResId;
		this.textResId = textResId;
		
	}

	public int getIconResId() {
		return iconResId;
	}

	public int getTextResId() {
		return textResId;
	}

	public int getId() {
		return id;
	}

}
