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

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

/**
 * An object input stream that converts old versions of serialized objects into
 * an up-to-date version.
 */
public final class VersioningObjectInputStream extends ObjectInputStream {

	private ClassesHistory history;

	/**
	 * Constructor with history.
	 * 
	 * @param history
	 *            History.
	 * @param in
	 *            Input stream to read from.
	 * 
	 * @throws IOException
	 *             Any of the usual Input/Output related exceptions.
	 */
	public VersioningObjectInputStream(final ClassesHistory history,
			final InputStream in) throws IOException {
		super(in);
		this.history = history;
		enableResolveObject(true);
	}

	@Override
	protected final Class<?> resolveClass(final ObjectStreamClass desc)
			throws IOException, ClassNotFoundException {

		final VersionedClass versionedClass = history.findVersionedClass(desc
				.getName());
		if (versionedClass == null) {
			return super.resolveClass(desc);
		}

		final Class<?> archivedClass = versionedClass.findArchivedClass(desc
				.getSerialVersionUID());
		if (archivedClass == null) {
			return super.resolveClass(desc);
		}

		return super.resolveClass(ObjectStreamClass.lookup(archivedClass));

	}

	@Override
	@SuppressWarnings("unchecked")
	protected final Object resolveObject(final Object object)
			throws IOException {
		Object obj = object;
		Converter converter;
		while ((converter = history.findConverter(obj.getClass())) != null) {
			obj = converter.convert(obj);
		}
		return super.resolveObject(obj);
	}
}
