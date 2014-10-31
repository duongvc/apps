package com.googlecode.jeneratedata.numbers;

import java.util.Random;

import com.googlecode.jeneratedata.core.Generator;
import com.googlecode.jeneratedata.core.RandomBasedGeneratorBase;

/**
 * Generate {@link Double} data items as returned by
 * {@link Random#nextDouble()}. 
 * 
 * @author Agustin Barto <abarto@gmail.com> 
 */
public class DoubleGenerator extends RandomBasedGeneratorBase implements
		Generator<Double> {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.googlecode.jeneratedata.core.Generator#generate()
	 */
	@Override
	public Double generate() {
		return random.nextDouble();
	}
}
