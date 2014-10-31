package com.googlecode.jeneratedata.util;

/**
 * Transforms an object.
 * 
 * @author Agustin Barto <abarto@gmail.com>
 *
 * @param <S> Input object type.
 * @param <T> Output object type.
 */
public interface Transformer<S, T> {
	/**
	 * Transforms the input.
	 * 
	 * @param input
	 * @return The input transformed.
	 */
	T transform(S input);
}
