package com.googlecode.jeneratedata.util;

/**
 * Converts the input to lower case.
 * 
 * @author Agustin Barto <abarto@gmail.com>
 */
public class ToLowerCaseGenerator implements Transformer<String, String> {
	/* (non-Javadoc)
	 * @see com.googlecode.jeneratedata.util.Transformer#transform(java.lang.Object)
	 */
	@Override
	public String transform(String input) {
		return input.toLowerCase();
	}
}
