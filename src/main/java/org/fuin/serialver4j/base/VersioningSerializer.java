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
import java.io.OutputStream;

/**
 * Serializes or deserializes a given object. Old serialized versions of the
 * objects are converted into their current representation when deserializing
 * the objects.
 */
public interface VersioningSerializer {

	/**
	 * Writes the object to the stream.
	 * 
	 * @param out
	 *            Stream to write to.
	 * @param obj
	 *            Object to serialize.
	 * 
	 * @throws IOException
	 *             Error writing to the stream.
	 */
	public void serialize(OutputStream out, Object obj) throws IOException;

	/**
	 * Reads an object from the stream and converts old versions of the object
	 * into the current representation.
	 * 
	 * @param in
	 *            Stream to read.
	 * 
	 * @return Object.
	 * 
	 * @throws IOException
	 *             Error reading the stream or the stream contains a wrong
	 *             format.
	 * @throws DeserializationException
	 *             Error deserializing the input to an object.
	 */
	public Object deserialize(InputStream in) throws IOException,
			DeserializationException;

}
