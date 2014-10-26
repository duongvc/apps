package com.travel.gate365.model;

public class ActivityInfo {

	private final String id;
	private final int titleResId;
	private final int iconResId;

	public ActivityInfo(final String id, final int iconResId, final int titleResId) {
		this.id = id;
		this.iconResId = iconResId;
		this.titleResId = titleResId;
		
	}

	public String getId() {
		return id;
	}

	public int getTitleResId() {
		return titleResId;
	}

	public int getIconResId() {
		return iconResId;
	}

}
