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

import com.caucho.hessian.io.AbstractHessianOutput;
import com.caucho.hessian.io.AbstractSerializer;

/**
 * Base class for serializing objects that support conversion into a string and
 * back.
 */
public abstract class BaseSerializer extends AbstractSerializer {

    @Override
    public final void writeObject(final Object obj, final AbstractHessianOutput out)
            throws IOException {

        if (obj == null) {
            out.writeNull();
            return;
        }

        final Class<?> clasz = obj.getClass();
        if (out.addRef(obj)) {
            return;
        }

        final int ref = out.writeObjectBegin(clasz.getName());
        if (ref < -1) {
            out.writeString("value");
            writeValue(out, obj);
            out.writeMapEnd();
        } else {
            if (ref == -1) {
                out.writeInt(1);
                out.writeString("value");
                out.writeObjectBegin(clasz.getName());
            }
            writeValue(out, obj);
        }

    }

    /**
     * Writes the value to the output.
     * 
     * @param out
     *            Output to write to.
     * @param obj
     *            Object to write.
     * 
     * @throws IOException
     *             Error writing to the output.
     */
    protected abstract void writeValue(final AbstractHessianOutput out, Object obj)
            throws IOException;

}
