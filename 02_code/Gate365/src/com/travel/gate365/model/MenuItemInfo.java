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
	private final boolean isActive;

	public MenuItemInfo(final int id, final int iconResId, final int textResId) {
		this(id, iconResId, textResId, true);
	}

	public MenuItemInfo(final int id, final int iconResId, final int textResId, final boolean isActive) {
		this.id = id;
		this.iconResId = iconResId;
		this.textResId = textResId;
		this.isActive = isActive;
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

	public boolean isActive() {
		return isActive;
	}

}
