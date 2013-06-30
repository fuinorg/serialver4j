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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;

import com.thoughtworks.xstream.XStream;

/**
 * Helper class.
 */
public final class Utils {

	/**
	 * Private constructor.
	 */
	private Utils() {
		throw new UnsupportedOperationException(
				"Cannot create an instance of a utility class!");
	}

	/**
	 * Writes the history into an XML file.
	 * 
	 * @param file
	 *            Target file.
	 * @param history
	 *            History to serialize into XML.
	 * 
	 * @throws IOException
	 *             Error writing the file.
	 */
	public static void writeToFile(final File file, final ClassesHistory history)
			throws IOException {
		final OutputStream out = new BufferedOutputStream(new FileOutputStream(
				file));
		try {
			final XStream xstream = Utils.createConfiguredXStream();
			xstream.toXML(history, out);
		} finally {
			out.close();
		}
	}

	/**
	 * Reads the history from an XML file.
	 * 
	 * @param file
	 *            XML file to read.
	 * 
	 * @return History.
	 * 
	 * @throws IOException
	 *             Error reading the file.
	 */
	public static ClassesHistory readFromFile(final File file)
			throws IOException {
		final InputStream in = new BufferedInputStream(
				new FileInputStream(file));
		try {
			final XStream xstream = Utils.createConfiguredXStream();
			return (ClassesHistory) xstream.fromXML(in);
		} finally {
			in.close();
		}
	}

	/**
	 * Returns the serial version UID from a class.
	 * 
	 * @param clasz
	 *            Class with static serial version UID field.
	 * 
	 * @return Value of static <code>serialVersionUID</code> attribute.
	 */
	public static long getSerialVersionUID(final Class<?> clasz) {
		try {
			final Field field = clasz.getDeclaredField("serialVersionUID");
			field.setAccessible(true);
			return (Long) field.get(null);
		} catch (final NoSuchFieldException ex) {
			throw new RuntimeException(
					"The class has no explicit serialVersionUID: "
							+ clasz.getName(), ex);
		} catch (final IllegalArgumentException ex) {
			throw new RuntimeException(ex);
		} catch (final IllegalAccessException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Returns the content between the begin and the and tag.
	 * 
	 * @param str
	 *            Text to search within.
	 * @param beginTag
	 *            Start tag.
	 * @param endTag
	 *            End tag.
	 * 
	 * @return Content between begin and and tag.
	 * 
	 * @throws ContentNotFoundException
	 *             The content cannot be extracted.
	 */
	public static String getContent(final String str, final String beginTag,
			final String endTag) throws ContentNotFoundException {

		assertNotNull("str", str);
		assertNotNull("beginTag", beginTag);
		assertNotNull("endTag", endTag);

		final int startPos = str.indexOf(beginTag);
		if (startPos == -1) {
			throw new ContentNotFoundException("Begin tag not found: '"
					+ beginTag + "'");
		}
		final int endPos = str.indexOf(endTag, startPos + beginTag.length());
		if (endPos == -1) {
			throw new ContentNotFoundException("End tag not found: '" + endTag
					+ "'");
		}
		return str.substring(startPos + beginTag.length(), endPos);
	}

	/**
	 * Returns the content between the begin and the and tag as long value.
	 * 
	 * @param str
	 *            Text to search within.
	 * @param beginTag
	 *            Start tag.
	 * @param endTag
	 *            End tag.
	 * 
	 * @return Long value between begin and and tag.
	 * 
	 * @throws ContentNotFoundException
	 *             The content cannot be extracted.
	 */
	public static long getLongContent(final String str, final String beginTag,
			final String endTag) throws ContentNotFoundException {

		final String value = getContent(str, beginTag, endTag);
		try {
			return Long.valueOf(value);
		} catch (final NumberFormatException ex) {
			throw new ContentNotFoundException("The content between '"
					+ beginTag + "' and '" + endTag
					+ "' is not a long value: '" + value + "'");
		}

	}

	private static final void assertNotNull(final String name,
			final Object value) {
		if (value == null) {
			throw new IllegalArgumentException("Argument '" + name
					+ "' cannot be null!");
		}
	}

	/**
	 * Creates a ready initialized XStream instance.
	 * 
	 * @return New instance.
	 */
	public static XStream createConfiguredXStream() {
		final XStream xstream = new XStream();
		xstream.alias("history", ClassesHistory.class);
		xstream.useAttributeFor(ClassesHistory.class, "versionTag");
		xstream.alias("class", VersionedClass.class);
		xstream.useAttributeFor(VersionedClass.class, "packageName");
		xstream.aliasField("package", VersionedClass.class, "packageName");
		xstream.useAttributeFor(VersionedClass.class, "className");
		xstream.aliasField("name", VersionedClass.class, "className");
		xstream.alias("version", ClassVersion.class);
		xstream.aliasField("oldClass", ClassVersion.class, "className");
		xstream.aliasField("converterClass", ClassVersion.class,
				"converterClassName");
		xstream.addImplicitCollection(ClassesHistory.class, "versionedClasses");
		xstream.addImplicitCollection(VersionedClass.class, "versions");
		return xstream;
	}

}
