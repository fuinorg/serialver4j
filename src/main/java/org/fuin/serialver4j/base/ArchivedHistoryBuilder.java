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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

/**
 * Creates a {@link ClassesHistory} by collecting all {@link Archived} classes.
 */
public final class ArchivedHistoryBuilder {

	/**
	 * private constructor.
	 */
	private ArchivedHistoryBuilder() {
		throw new UnsupportedOperationException(
				"Cannot create an instance of a utility class!");
	}

	/**
	 * Collects all classes with a {@link Archived} annotation and creates a
	 * history object with that information.
	 * 
	 * @param versionTag
	 *            Version tag for the history.
	 * @param converterFactory
	 *            Converter factory to use.
	 * @param packageName
	 *            Name of the archive package to scan.
	 * 
	 * @return History object.
	 */
	public static ClassesHistory create(final String versionTag,
			final ConverterFactory converterFactory, final String packageName) {

		final ClassesHistory history = new ClassesHistory(versionTag,
				converterFactory);

		final Reflections reflections = createReflections(packageName);

		final Set<Class<?>> archivedClasses = reflections
				.getTypesAnnotatedWith(Archived.class);
		for (final Class<?> archivedClass : archivedClasses) {
			final Archived archived = archivedClass
					.getAnnotation(Archived.class);
			VersionedClass versionedClass = history.findVersionedClass(archived
					.clasz().getName());
			if (versionedClass == null) {
				versionedClass = new VersionedClass(archived.clasz()
						.getPackage().getName(), archived.clasz()
						.getSimpleName());
				history.add(versionedClass);
			}
			final ClassVersion classVersion = new ClassVersion(
					Utils.getSerialVersionUID(archivedClass),
					archivedClass.getName(), archived.converter().getName());
			versionedClass.addVersion(classVersion);

		}

		return history;
	}

	/**
	 * Creates a reflection object for finding archived classes.
	 * 
	 * @param packageName
	 *            Name of the package to scan.
	 * 
	 * @return Reflection object.
	 */
	private static Reflections createReflections(final String packageName) {
		final ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
		final Set<URL> urls = getUrls(packageName);
		configurationBuilder.setUrls(urls);
		configurationBuilder.setScanners(new TypeAnnotationsScanner(),
				new SubTypesScanner());
		final FilterBuilder filterBuilder = new FilterBuilder();
		filterBuilder.include(FilterBuilder.prefix(packageName));
		configurationBuilder.filterInputsBy(filterBuilder);
		final Reflections reflections = new Reflections(configurationBuilder);
		return reflections;
	}

	/**
	 * Returns the URLs for the given package. This is a workaround for a
	 * strange behaviour of
	 * {@link ClasspathHelper#getUrlsForPackagePrefix(String)}. It does not work
	 * out-of-the-box when run as Maven test. It contains the package name in
	 * the URL. That package name part is stripped by this method.
	 * 
	 * @param packageName
	 *            Name of the package to find URLs for.
	 * 
	 * @return List of URLs.
	 */
	private static Set<URL> getUrls(final String packageName) {
		final Set<URL> urls = new HashSet<URL>();
		final String packagePath = packageName.replace('.', '/');
		final int packageLen = packagePath.length();
		final Set<URL> foundUrls = ClasspathHelper.forPackage(packageName,
				ArchivedHistoryBuilder.class.getClassLoader());
		for (URL url : foundUrls) {
			final String urlStr = url.toString();
			if (urlStr.endsWith(packagePath)) {
				try {
					final URL newUrl = new URL(urlStr.substring(0,
							urlStr.length() - packageLen));
					urls.add(newUrl);
				} catch (final MalformedURLException ex) {
					throw new RuntimeException(ex);
				}
			} else {
				urls.add(url);
			}
		}
		return urls;
	}

}
