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
package org.fuin.serialver4j.hessian;

import java.io.IOException;

import org.fuin.serialver4j.base.ClassesHistory;
import org.fuin.serialver4j.base.Converter;
import org.fuin.serialver4j.base.VersionedClass;
import org.fuin.serialver4j.base.VersioningSerializer;

import com.caucho.hessian.io.AbstractHessianInput;
import com.caucho.hessian.io.AbstractHessianOutput;
import com.caucho.hessian.io.SerializerFactory;

/**
 * Serializes and deserializes a given object using hessian or burlap.
 */
public abstract class AbstractVersioningHessianSerializer implements VersioningSerializer {

    private final ClassesHistory history;

    private final int pushbackBufSize;

    private final SerializerFactory serializerFactory;

    /**
     * Constructor with history.
     * 
     * @param history
     *            History to use.
     * @param pushbackBufSize
     *            Size of the push back buffer.
     * @param serializerFactory
     *            Serializer factory to use.
     */
    public AbstractVersioningHessianSerializer(final ClassesHistory history,
            final int pushbackBufSize, final SerializerFactory serializerFactory) {
        super();
        this.history = history;
        this.pushbackBufSize = pushbackBufSize;
        this.serializerFactory = serializerFactory;
    }

    /**
     * Serializes the object using the given hessian output.
     * 
     * @param output
     *            Output to use.
     * @param obj
     *            Object to write to the output.
     * 
     * @throws IOException
     *             Error writing to the output.
     */
    protected final void serialize(final AbstractHessianOutput output, final Object obj)
            throws IOException {

        output.setSerializerFactory(serializerFactory);
        output.writeObject(obj);

    }

    /**
     * Reads an object of the given type from the input converting it to the
     * current version.
     * 
     * @param input
     *            Input to use.
     * @param version
     *            Version of the object on the input.
     * @param type
     *            Type of the object on the input.
     * 
     * @return Object converted into the current type.
     * 
     * @throws IOException
     *             Error reading from the input.
     */
    @SuppressWarnings("unchecked")
    protected final Object readObject(final AbstractHessianInput input, final long version,
            final String type) throws IOException {

        input.setSerializerFactory(serializerFactory);

        final VersionedClass versionedClass = history.findVersionedClass(type);
        if (versionedClass == null) {
            return input.readObject();
        }

        final Class<?> archivedClass = versionedClass.findArchivedClass(version);
        if (archivedClass == null) {
            return input.readObject();
        }

        Object obj = input.readObject(archivedClass);
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

    /**
     * Returns the size of the push back buffer.
     * 
     * @return Size of the push back buffer.
     */
    public final int getPushbackBufSize() {
        return pushbackBufSize;
    }

    /**
     * Returns the version tag from the history.
     * 
     * @return Name of the version tag.
     */
    public final String getVersionTag() {
        return history.getVersionTag();
    }

}
