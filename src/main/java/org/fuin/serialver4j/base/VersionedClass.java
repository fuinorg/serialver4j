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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class with one or more old versions.
 */
public final class VersionedClass implements Serializable {

	private static final long serialVersionUID = 1L;

	private String packageName;

	private String className;

	private List<ClassVersion> versions;

	private transient Map<Long, Class<?>> versionMap;

	/**
	 * Constructor with package and name of the current class.
	 * 
	 * @param packageName
	 *            Package name.
	 * @param className
	 *            Class name without package.
	 */
	public VersionedClass(final String packageName, final String className) {
		super();
		this.packageName = packageName;
		this.className = className;
		this.versions = new ArrayList<ClassVersion>();
		this.versionMap = new HashMap<Long, Class<?>>();
	}

	/**
	 * Initialize the instance after it has been deserialized. Transient fields
	 * are set properly after calling this method.
	 */
	public final void init() {
		versionMap = new HashMap<Long, Class<?>>();
		for (final ClassVersion version : versions) {
			addToMap(version);
		}
	}

	private void addToMap(final ClassVersion version) {
		versionMap.put(version.getSerialVersionUID(), classFor(version));
	}

	/**
	 * Adds a version to the class.
	 * 
	 * @param version
	 *            Version to add.
	 */
	public final void addVersion(final ClassVersion version) {
		versions.add(version);
		addToMap(version);
	}

	/**
	 * Removes a version from the class.
	 * 
	 * @param version
	 *            Version to remove.
	 */
	public final void removeVersion(final ClassVersion version) {
		versions.remove(version);
		versionMap.remove(version.getSerialVersionUID());
	}

	/**
	 * Returns the full qualified name of the class.
	 * 
	 * @return Package + Simple Name.
	 */
	public final String getName() {
		return packageName + "." + className;
	}

	/**
	 * Returns the package name.
	 * 
	 * @return Package name.
	 */
	public final String getPackageName() {
		return packageName;
	}

	/**
	 * Returns the simple name of the class.
	 * 
	 * @return Class name without package.
	 */
	public final String getSimpleClassName() {
		return className;
	}

	// CHECKSTYLE:OFF Generated code

	@Override
	public int hashCode() {
		final String name = getName();
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		final String name = getName();
		VersionedClass other = (VersionedClass) obj;
		if (name == null) {
			if (other.getName() != null)
				return false;
		} else if (!name.equals(other.getName()))
			return false;
		return true;
	}

	// CHECKSTYLE:ON

	/**
	 * Tries to find a version by it's serial version UID.
	 * 
	 * @param serialVersionUID
	 *            Version to locate.
	 * 
	 * @return Class or <code>null</code> if the version is unknown.
	 */
	public final Class<?> findArchivedClass(final Long serialVersionUID) {
		return versionMap.get(serialVersionUID);
	}

	/**
	 * Returns a list of archived classes.
	 * 
	 * @return List of all known old classes.
	 */
	public final List<Class<?>> getArchivedClasses() {
		final List<Class<?>> classes = new ArrayList<Class<?>>();
		for (final ClassVersion version : versions) {
			classes.add(classFor(version));
		}
		return classes;
	}

	/**
	 * Creates a class for the given version.
	 * 
	 * @param version
	 *            Version of the class.
	 * 
	 * @return Class.
	 */
	public Class<?> classFor(final ClassVersion version) {
		final String className = version.getClassName();
		try {
			return Class.forName(className);
		} catch (final ClassNotFoundException ex) {
			throw new RuntimeException("Cannot find archived class!", ex);
		}
	}

	@Override
	public final String toString() {
		return getName();
	}

	/**
	 * Returns an unmodifiable map with the versions. Just package visible for
	 * tests - Do not use otherwise - May change without notice.
	 * 
	 * @return Version map or <code>null</code>.
	 */
	final Map<Long, Class<?>> getVersionMap() {
		if (versionMap == null) {
			return null;
		}
		return Collections.unmodifiableMap(versionMap);
	}

}
