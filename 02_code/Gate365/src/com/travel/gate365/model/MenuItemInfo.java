package com.travel.gate365.model;

public class MenuItemInfo {

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
