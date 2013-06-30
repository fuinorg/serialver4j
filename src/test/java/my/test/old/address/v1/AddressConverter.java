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
package my.test.old.address.v1;

import org.fuin.serialver4j.base.Converter;

/**
 * Converts version 1 into version 2.
 */
public class AddressConverter implements
        Converter<my.test.old.address.v1.Address, my.test.old.address.v2.Address> {

    @Override
    public final my.test.old.address.v2.Address convert(final my.test.old.address.v1.Address src) {
        if (src == null) {
            return null;
        }
        final String firstName;
        final String lastName;
        final String name = src.getName();
        if (name == null) {
            firstName = null;
            lastName = null;
        } else {
            final int p = name.indexOf(" ");
            if (p == -1) {
                firstName = null;
                lastName = name;
            } else {
                firstName = name.substring(0, p);
                lastName = name.substring(p + 1);
            }
        }
        return new my.test.old.address.v2.Address(firstName, lastName);
    }

}
