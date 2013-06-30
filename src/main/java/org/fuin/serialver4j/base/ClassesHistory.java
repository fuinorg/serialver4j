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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * History of classes.
 */
public final class ClassesHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	private String versionTag = "version";

	private List<VersionedClass> versionedClasses;

	private transient ConverterFactory converterFactory;

	private transient Map<String, VersionedClass> nameMap;

	private transient Map<Class<?>, Converter<?, ?>> classMap;

	/**
	 * Constructor with converter factory. The version tag is set to "version".
	 * 
	 * @param converterFactory
	 *            Converter factory to use.
	 */
	public ClassesHistory(final ConverterFactory converterFactory) {
		this("version", converterFactory);
	}

	/**
	 * Constructor with tag and converter factory.
	 * 
	 * @param versionTag
	 *            Tag name of the version tag.
	 * @param converterFactory
	 *            Converter factory to use.
	 */
	public ClassesHistory(final String versionTag,
			final ConverterFactory converterFactory) {
		super();
		this.versionTag = versionTag;
		this.converterFactory = converterFactory;
		this.versionedClasses = new ArrayList<VersionedClass>();
		this.nameMap = new HashMap<String, VersionedClass>();
		this.classMap = new HashMap<Class<?>, Converter<?, ?>>();
	}

	/**
	 * Initialize the instance after it has been deserialized. Transient fields
	 * are set properly after calling this method.
	 * 
	 * @param converterFactory
	 *            Factory to use.
	 */
	public final void init(final ConverterFactory converterFactory) {
		this.converterFactory = converterFactory;
		nameMap = new HashMap<String, VersionedClass>();
		classMap = new HashMap<Class<?>, Converter<?, ?>>();
		for (final VersionedClass versionedClass : versionedClasses) {
			versionedClass.init();
			addToMaps(versionedClass);
		}
	}

	/**
	 * Returns the converter factory.
	 * 
	 * @return Converter factory.
	 */
	public final ConverterFactory getConverterFactory() {
		return converterFactory;
	}

	private void addToMaps(final VersionedClass versionedClass) {

		nameMap.put(versionedClass.getName(), versionedClass);

		final List<Class<?>> archivedClasses = versionedClass
				.getArchivedClasses();
		for (final Class<?> archivedClass : archivedClasses) {
			final String converterName = archivedClass.getName() + "Converter";
			classMap.put(archivedClass,
					converterFactory.createConverter(converterName));
		}

	}

	/**
	 * Adds a class to the history.
	 * 
	 * @param versionedClass
	 *            Class to add.
	 */
	public final void add(final VersionedClass versionedClass) {
		versionedClasses.add(versionedClass);
		addToMaps(versionedClass);
	}

	/**
	 * Removes a class from the history.
	 * 
	 * @param versionedClass
	 *            Class to remove.
	 */
	public final void remove(final VersionedClass versionedClass) {
		versionedClasses.remove(versionedClass);
		nameMap.remove(versionedClass.getName());
	}

	/**
	 * Tries to finds a class by it's name.
	 * 
	 * @param name
	 *            Full qualified name of the class.
	 * 
	 * @return Version information for the class or <code>null</code> if no
	 *         information exists.
	 */
	public final VersionedClass findVersionedClass(final String name) {
		return nameMap.get(name);
	}

	/**
	 * Tries to find a converter for a given class.
	 * 
	 * @param oldClass
	 *            Possibly old class.
	 * 
	 * @return Converter if one exists, else <code>null</code>.
	 */
	public final Converter<? extends Object, ? extends Object> findConverter(
			final Class<?> oldClass) {
		return classMap.get(oldClass);
	}

	/**
	 * Returns the name of the version tag.
	 * 
	 * @return Name of the version tag.
	 */
	public final String getVersionTag() {
		return versionTag;
	}

}
