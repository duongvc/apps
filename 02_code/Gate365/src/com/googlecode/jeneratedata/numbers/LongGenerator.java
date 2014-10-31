package com.googlecode.jeneratedata.numbers;

import java.util.Random;

import com.googlecode.jeneratedata.core.Generator;
import com.googlecode.jeneratedata.core.RandomBasedGeneratorBase;

/**
 * Generate {@link Long} data items as returned by
 * {@link Random#nextLong()}. 
 * 
 * @author Agustin Barto <abarto@gmail.com> 
 */
public class LongGenerator extends RandomBasedGeneratorBase implements Generator<Long> {
	/* (non-Javadoc)
	 * @see com.googlecode.jeneratedata.core.Generator#generate()
	 */
	@Override
	public Long generate() {
		return random.nextLong();
	}
}
