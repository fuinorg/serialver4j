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
 * Version of a class with a unique id.
 */
public final class ClassVersion {

	private Long serialVersionUID;

	private String className;

	private String converterClassName;

	/**
	 * Constructor with all data.
	 * 
	 * @param serialVersionUID
	 *            Serial version UID.
	 * @param className
	 *            Full qualified name of the old version.
	 * @param converterClassName
	 *            Full qualified converter class name.
	 */
	public ClassVersion(final Long serialVersionUID, final String className,
			final String converterClassName) {
		super();
		this.serialVersionUID = serialVersionUID;
		this.className = className;
		this.converterClassName = converterClassName;
	}

	/**
	 * Returns the serial version UID.
	 * 
	 * @return Serial version UID.
	 */
	public final Long getSerialVersionUID() {
		return serialVersionUID;
	}

	/**
	 * Returns the name of the old version.
	 * 
	 * @return Full qualified class name.
	 */
	public final String getClassName() {
		return className;
	}

	/**
	 * Returns the name of the converter class.
	 * 
	 * @return Full qualified converter class name.
	 */
	public final String getConverterClassName() {
		return converterClassName;
	}

	// CHECKSTYLE:OFF Generated code

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((serialVersionUID == null) ? 0 : serialVersionUID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClassVersion other = (ClassVersion) obj;
		if (serialVersionUID == null) {
			if (other.serialVersionUID != null)
				return false;
		} else if (!serialVersionUID.equals(other.serialVersionUID))
			return false;
		return true;
	}

	// CHECKSTYLE:ON

	@Override
	public final String toString() {
		return serialVersionUID + ", class=" + className + ", converter="
				+ converterClassName;
	}

}
