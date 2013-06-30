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

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.SerializerFactory;

/**
 * Serializes and deserializes a given object using Hessian2. The serial version
 * UID of the object and it's type will be added to the stream before
 * serializing the object itself.
 */
public final class VersioningHessian2Serializer extends AbstractVersioningHessianSerializer {

    /**
     * Constructor with history. The pushback buffer size will default to 512
     * and the serializer factory is set to {@link SerializerFactory}.
     * 
     * @param history
     *            History to use.
     */
    public VersioningHessian2Serializer(final ClassesHistory history) {
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
    public VersioningHessian2Serializer(final ClassesHistory history,
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
    public VersioningHessian2Serializer(final ClassesHistory history, final int pushbackBufSize,
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
        final Hessian2Input input = new Hessian2Input(new ByteArrayInputStream(buf, 0, count));
        int tag = input.read();
        if (tag != 'O') {
            throw new DeserializationException("Expected 'O' but found: '" + tag + "'");
        }
        final String type = input.readString();
        final int len = input.readInt();
        for (int i = 0; i < len; i++) {
            final String fieldName = input.readString();
            if ((i == 0) && !fieldName.equals(getVersionTag())) {
                throw new DeserializationException("Expected '" + getVersionTag()
                        + "' as first field, but found: '" + fieldName + "'");
            }
        }
        tag = (char) input.read();
        if (tag != 'o') {
            throw new DeserializationException("Expected 'o' but found: '" + tag + "'");
        }
        tag = input.read();
        if (tag != 0x90) {
            throw new DeserializationException("Expected '0x90' but found: '" + tag + "'");
        }
        final long version = input.readLong();

        // Deserialize with detected type and version
        final Hessian2Input hessian2Input = new Hessian2Input(pushbackIn);
        try {
            return readObject(hessian2Input, version, type);
        } finally {
            hessian2Input.close();
        }
    }

    @Override
    public final void serialize(final OutputStream out, final Object obj) throws IOException {
        final Hessian2Output hessian2Outpout = new Hessian2Output(out);
        try {
            serialize(hessian2Outpout, obj);
        } finally {
            hessian2Outpout.close();
        }
    }

}
