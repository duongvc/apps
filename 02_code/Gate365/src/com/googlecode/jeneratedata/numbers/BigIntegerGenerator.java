package com.googlecode.jeneratedata.numbers;

import java.math.BigInteger;

import com.googlecode.jeneratedata.core.Generator;
import com.googlecode.jeneratedata.core.RandomBasedGeneratorBase;

/**
 * Generates {@link BigInteger} data items uniformly distributed over the range
 * 0 to (2^numBits - 1), inclusive using {@link BigInteger#BigInteger(int, java.util.Random)}.
 * 
 * @author Agustin Barto <abarto@gmail.com>
 * 
 * @see BigInteger
 */
public class BigIntegerGenerator extends RandomBasedGeneratorBase implements Generator<BigInteger> {
	/**
	 * Number of bits to be supplied to the {@link BigInteger} constructor.
	 */
	private int numBits;
	
	/**
	 * Constructor.
	 * 
	 * @param numBits Number of bits to be supplied to the {@link BigInteger} constructor.
	 * @see BigInteger#BigInteger(int, java.util.Random)
	 */
	public BigIntegerGenerator(int numBits) {
		this.numBits = numBits;
	}	

	/* (non-Javadoc)
	 * @see com.googlecode.jeneratedata.core.Generator#generate()
	 */
	@Override
	public BigInteger generate() {
		return new BigInteger(numBits, random);
	}
}
