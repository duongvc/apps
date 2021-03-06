package com.googlecode.jeneratedata.core;

import java.util.HashSet;
import java.util.Set;

/**
 * Generates a {@link Set} of data items of type <code>T</code>.
 * 
 * @author Agustin Barto <abarto@gmail.com>
 *
 * @param <T> Type of data item contained in the generated {@link Set}.
 */
public class SetGenerator<T> extends CollectionGeneratorBase<T, Set<T>> {
	/**
	 * Constructor.
	 * 
	 * @param count The amount of elements to generate.
	 * @param generator The {@link Generator} used to create the elements of
	 * the collection.
	 */
	public SetGenerator(int count, Generator<T> generator) {
		super(count, generator);
	}

	/* (non-Javadoc)
	 * @see com.googlecode.jeneratedata.core.CollectionGeneratorBase#createCollection()
	 */
	@Override
	protected Set<T> createCollection() {
		return new HashSet<T>(count);
	}
}
