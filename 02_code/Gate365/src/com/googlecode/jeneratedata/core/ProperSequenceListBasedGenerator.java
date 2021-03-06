package com.googlecode.jeneratedata.core;

import java.util.Iterator;
import java.util.List;

/**
 * Generates data items of type <code>T</code> using the values stored in the
 * contained {@link List} extracting them in the proper sequence (as returned
 * by an {@link Iterator} instance. Once all the elements of the list were
 * returned as generated data items, the sequence is restarted.
 * 
 * @author Agustin Barto <abarto@gmail.com>
 *
 * @param <T> Type of data item to generate.
 */
public class ProperSequenceListBasedGenerator<T> extends
		ListBasedGeneratorBase<T> implements Generator<T> {
	/**
	 * Iterator of the {@link List} of values.
	 */
	private Iterator<T> iterator;

	/**
	 * Constructor.
	 * 
	 * @param values A {@link List} of values that will be used to generate
	 * data items.
	 */
	public ProperSequenceListBasedGenerator(List<T> values) {
		super(values);
		iterator = values.iterator();
	}

	/* (non-Javadoc)
	 * @see com.googlecode.jeneratedata.core.Generator#generate()
	 */
	@Override
	public T generate() {
		if (!iterator.hasNext()) {
			iterator = values.iterator();
		}
		
		return iterator.next();
	}	
}
