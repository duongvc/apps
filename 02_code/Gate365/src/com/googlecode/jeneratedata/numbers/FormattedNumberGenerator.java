package com.googlecode.jeneratedata.numbers;

import java.text.NumberFormat;

import com.googlecode.jeneratedata.core.Generator;
import com.googlecode.jeneratedata.core.GeneratorWrapperBase;

public class FormattedNumberGenerator<T extends Number> extends GeneratorWrapperBase<T> implements Generator<String> {
	private NumberFormat numberFormat;

	public FormattedNumberGenerator(Generator<T> generator, NumberFormat numberFormat) {
		super(generator);
		this.numberFormat = numberFormat;
	}

	@Override
	public String generate() {
		return numberFormat.format(generator.generate());
	}
}
