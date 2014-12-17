package com.travel.gate365.helper;

import java.lang.reflect.Field;
import com.travel.gate365.R;

public final class ResourceHelper {

	public static int getDrawableId(String id) {
		try {
			@SuppressWarnings("rawtypes")
			Class cls = Class.forName(R.drawable.class.getName());
			Field fieldlist[] = cls.getDeclaredFields();
			for (Field aFieldlist : fieldlist) {
				if (aFieldlist.getName().equalsIgnoreCase(id)) {
					return aFieldlist.getInt(null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}
}