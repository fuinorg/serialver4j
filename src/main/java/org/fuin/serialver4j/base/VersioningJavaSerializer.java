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
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Serializes a given object using a {@link ObjectOutputStream} and deserializes
 * and Object using a {@link VersioningObjectInputStream}.
 */
public final class VersioningJavaSerializer implements VersioningSerializer {

	private final ClassesHistory history;

	/**
	 * Constructor with history.
	 * 
	 * @param history
	 *            History to use.
	 */
	public VersioningJavaSerializer(final ClassesHistory history) {
		super();
		this.history = history;
	}

	@Override
	public final Object deserialize(final InputStream in) throws IOException,
			DeserializationException {
		try {
			return new VersioningObjectInputStream(history, in).readObject();
		} catch (final ClassNotFoundException ex) {
			throw new DeserializationException(ex.getMessage(), ex);
		}
	}

	@Override
	public final void serialize(final OutputStream out, final Object obj)
			throws IOException {
		new ObjectOutputStream(out).writeObject(obj);
	}

}
