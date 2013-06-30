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
import java.util.UUID;

import com.caucho.hessian.io.AbstractHessianInput;

/**
 * Deserializes a {@link UUID} object.
 */
public final class UUIDDeserializer extends BaseDeserializer {

    @Override
    public final Class<?> getType() {
        return UUID.class;
    }

    @Override
    protected final Object readValue(final AbstractHessianInput in) throws IOException {
        final String str = in.readString();
        return UUID.fromString(str);
    }

}
