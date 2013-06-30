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

import com.caucho.hessian.io.AbstractDeserializer;
import com.caucho.hessian.io.AbstractHessianInput;

/**
 * Base class for deserializing objects that support conversion into a string
 * and back.
 */
public abstract class BaseDeserializer extends AbstractDeserializer {

    @Override
    public final Object readMap(final AbstractHessianInput in) throws IOException {

        final int ref = in.addRef(null);
        Object obj = null;
        while (!in.isEnd()) {
            final String key = in.readString();
            if (key.equals("value")) {
                obj = readValue(in);
            } else {
                in.readString();
            }
        }
        in.readMapEnd();
        in.setRef(ref, obj);
        return obj;

    }

    @Override
    public final Object readObject(final AbstractHessianInput in, final String[] fieldNames)
            throws IOException {

        final int ref = in.addRef(null);
        Object obj = null;
        for (String key : fieldNames) {
            if (key.equals("value")) {
                obj = readValue(in);
            } else {
                in.readObject();
            }
        }
        in.setRef(ref, obj);
        return obj;

    }

    @Override
    public abstract Class<?> getType();

    /**
     * Reads the value from the input.
     * 
     * @param in
     *            Input to read from.
     * 
     * @return Object created from the input.
     * 
     * @throws IOException
     *             Error reading from the input.
     */
    protected abstract Object readValue(final AbstractHessianInput in) throws IOException;

}
