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
package org.fuin.serialver4j.xstream;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PushbackInputStream;

import org.fuin.serialver4j.base.ClassesHistory;
import org.fuin.serialver4j.base.ContentNotFoundException;
import org.fuin.serialver4j.base.Converter;
import org.fuin.serialver4j.base.DeserializationException;
import org.fuin.serialver4j.base.Utils;
import org.fuin.serialver4j.base.VersionedClass;
import org.fuin.serialver4j.base.VersioningSerializer;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;

/**
 * Serializes and deserializes a given object using XStream.
 */
public final class VersioningXStreamSerializer implements VersioningSerializer {

    private final ClassesHistory history;

    private final XStream xstream;

    private final int pushbackBufSize;

    /**
     * Constructor with history, xstream and push back buffer size.
     * 
     * @param history
     *            History to use.
     * @param xstream
     *            Read initialized XStream instance.
     * @param pushbackBufSize
     *            Size of the push back buffer.
     */
    public VersioningXStreamSerializer(final ClassesHistory history, final XStream xstream,
            final int pushbackBufSize) {
        super();
        this.history = history;
        this.xstream = xstream;
        this.pushbackBufSize = pushbackBufSize;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final Object deserialize(final InputStream in) throws IOException,
            DeserializationException {

        final PushbackInputStream pushbackIn = new PushbackInputStream(in, pushbackBufSize);
        final byte[] buf = new byte[pushbackBufSize];
        final int count = pushbackIn.read(buf);
        pushbackIn.unread(buf, 0, count);
        final String str = new String(buf, 0, count, "UTF-8");

        final long version;
        try {
            version = Utils.getLongContent(str, "<" + history.getVersionTag() + ">", "</"
                    + history.getVersionTag() + ">");
        } catch (final ContentNotFoundException ex) {
            throw new DeserializationException("Error reading the '" + history + "' tag", ex);
        }

        final String type;
        try {
            type = Utils.getContent(str, "<", ">");
        } catch (final ContentNotFoundException ex) {
            throw new DeserializationException("Error reading the type", ex);
        }

        final InputStreamReader reader = new InputStreamReader(pushbackIn, "UTF-8");

        final VersionedClass versionedClass = history.findVersionedClass(type);
        if (versionedClass == null) {
            return xstream.fromXML(reader);
        }

        final Class<?> archivedClass = versionedClass.findArchivedClass(version);
        if (archivedClass == null) {
            return xstream.fromXML(reader);
        }

        xstream.alias(type, archivedClass);

        Object obj = xstream.fromXML(reader);
        if (!archivedClass.isInstance(obj)) {
            throw new IllegalStateException("Expected class '" + archivedClass.getName()
                    + "', but was:" + obj.getClass().getName());
        }

        Converter converter;
        while ((converter = history.findConverter(obj.getClass())) != null) {
            obj = converter.convert(obj);
        }
        return obj;

    }

    @Override
    public final void serialize(final OutputStream out, final Object obj) throws IOException {
        xstream.marshal(obj, new CompactWriter(new OutputStreamWriter(out, "UTF-8")));
    }

}
