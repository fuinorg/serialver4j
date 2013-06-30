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
 * Converts a source object into a target object.
 * 
 * @param <SRC>
 *            Type of the source object.
 * @param <DEST>
 *            Type of the destination object.
 */
public interface Converter<SRC, DEST> {

	/**
	 * Converts a source instance into a target instance.
	 * 
	 * @param src
	 *            Source object to convert.
	 * 
	 * @return New created and populated with data from the source object.
	 */
	public DEST convert(SRC src);

}
