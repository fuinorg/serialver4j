/**
 * Copyright (C) 2009 Future Invent Informationsmanagement GmbH. All rights
 * reserved. <http://www.fuin.org/>
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library. If not, see <http://www.gnu.org/licenses/>.
 */
package org.fuin.serialver4j.base;

/**
 * Converter factory that creates instances by using
 * {@link Class#forName(String)} and {@link Class#newInstance()}.
 */
public final class SimpleConverterFactory implements ConverterFactory {

	@Override
	public final Converter<Object, Object> createConverter(
			final String converterClassName) {
		final Class<?> converterClass = classForName(converterClassName);
		return createConverter(converterClass);
	}

	private Class<?> classForName(final String name) {
		try {
			return Class.forName(name);
		} catch (final ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
	}

	@SuppressWarnings("unchecked")
	private Converter<Object, Object> createConverter(
			final Class<?> converterClass) {
		try {
			return (Converter<Object, Object>) converterClass.newInstance();
		} catch (final InstantiationException ex) {
			throw new RuntimeException(ex);
		} catch (final IllegalAccessException ex) {
			throw new RuntimeException(ex);
		}
	}

}
