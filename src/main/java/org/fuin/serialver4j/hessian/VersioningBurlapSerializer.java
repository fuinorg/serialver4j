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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;

import org.fuin.serialver4j.base.ClassesHistory;
import org.fuin.serialver4j.base.DeserializationException;

import com.caucho.burlap.io.BurlapInput;
import com.caucho.burlap.io.BurlapOutput;
import com.caucho.hessian.io.SerializerFactory;

/**
 * Serializes and deserializes a given object using Burlap. The serial version
 * UID of the object and it's type will be added to the stream before
 * serializing the object itself.
 */
public final class VersioningBurlapSerializer extends AbstractVersioningHessianSerializer {

    /**
     * Constructor with history. The pushback buffer size will default to 512
     * and the serializer factory is set to {@link SerializerFactory}.
     * 
     * @param history
     *            History to use.
     */
    public VersioningBurlapSerializer(final ClassesHistory history) {
        super(history, 512, new SerializerFactory());
    }

    /**
     * Constructor with history and serializer factory. The pushback buffer size
     * will default to 512.
     * 
     * @param history
     *            History to use.
     * @param serializerFactory
     *            Serializer factory to use.
     */
    public VersioningBurlapSerializer(final ClassesHistory history,
            final SerializerFactory serializerFactory) {
        super(history, 512, serializerFactory);
    }

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
    public VersioningBurlapSerializer(final ClassesHistory history, final int pushbackBufSize,
            final SerializerFactory serializerFactory) {
        super(history, pushbackBufSize, serializerFactory);
    }

    @Override
    public final Object deserialize(final InputStream in) throws IOException,
            DeserializationException {

        // Create push back stream and read first bytes into buffer
        final PushbackInputStream pushbackIn = new PushbackInputStream(in, getPushbackBufSize());
        final byte[] buf = new byte[getPushbackBufSize()];
        final int count = pushbackIn.read(buf);
        pushbackIn.unread(buf, 0, count);

        // Read information from byte array
        final BurlapInput input = new BurlapInput(new ByteArrayInputStream(buf, 0, count));
        input.readMapStart();
        final String type = input.readType();
        final String tagName = input.readString();
        if (!tagName.equals(getVersionTag())) {
            throw new DeserializationException("Expected tag '" + getVersionTag() + "' but found: "
                    + tagName);
        }
        final long version = input.readLong();

        return readObject(new BurlapInput(pushbackIn), version, type);

    }

    @Override
    public final void serialize(final OutputStream out, final Object obj) throws IOException {
        serialize(new BurlapOutput(out), obj);
    }

}
