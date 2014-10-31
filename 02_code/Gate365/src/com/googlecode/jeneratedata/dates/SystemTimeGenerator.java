package com.googlecode.jeneratedata.dates;

import com.googlecode.jeneratedata.core.Generator;

/**
 * Generates {@link Long} calling {@link System#currentTimeMillis()}.
 * 
 * @author Agustin Barto <abarto@gmail.com>
 */
public class SystemTimeGenerator implements Generator<Long> {
	/* (non-Javadoc)
	 * @see com.googlecode.jeneratedata.core.Generator#generate()
	 */
	@Override
	public Long generate() {
		return System.currentTimeMillis();
	}

}
