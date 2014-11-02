package com.travel.gate365.helper;

import java.lang.reflect.Field;
import com.travel.gate365.R;

public class ResourceHelper {

	public ResourceHelper() {
		
	}
	
	public static int getDrawableId(String id){
		int result = 0;
		try {
			Class cls = Class.forName(R.drawable.class.getName());
			Field fieldlist[] = cls.getDeclaredFields();
			 for (Field aFieldlist : fieldlist) {
				 if(aFieldlist.getName().equalsIgnoreCase(id)){
					 result = aFieldlist.getInt(null);
					 break;
				 }				 
			 }			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} 
		
		return result;
	}

}
