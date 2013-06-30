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
package my.test.old.address.v2;

import my.test.old.IdFactory;

import org.fuin.serialver4j.base.Converter;

/**
 * Converts version 2 into version 3.
 */
public class AddressConverter implements Converter<my.test.old.address.v2.Address, my.test.Address> {

    private IdFactory idFactory;

    /**
     * Constructor with ID factory.
     * 
     * @param idFactory
     *            ID factory to use.
     */
    public AddressConverter(final IdFactory idFactory) {
        super();
        this.idFactory = idFactory;
    }

    @Override
    public final my.test.Address convert(final my.test.old.address.v2.Address src) {
        if (src == null) {
            return null;
        }
        return new my.test.Address(idFactory.nextId(), src.getFirstName(), src.getLastName());
    }

}
