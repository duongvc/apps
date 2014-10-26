package com.travel.gate365.model;

public class ActivityInfo {

	private final String id;
	private final int titleResId;
	private final int iconResId;
	private final int rightTextResId;

	public ActivityInfo(final String id, final int iconResId, final int titleResId, final int rightTextResId) {
		this.id = id;
		this.iconResId = iconResId;
		this.titleResId = titleResId;
		this.rightTextResId = rightTextResId;
		
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

	public int getRightTextResId() {
		return rightTextResId;
	}

}
