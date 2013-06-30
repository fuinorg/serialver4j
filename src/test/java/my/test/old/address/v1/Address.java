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

import java.io.Serializable;

import org.fuin.serialver4j.base.Archived;

/**
 * Test class version 1.
 */
@Deprecated
@Archived(clasz = my.test.Address.class, converter = AddressConverter.class)
public class Address implements Serializable {

    private static final long serialVersionUID = -7393761964342362874L;

    private long versionUID = serialVersionUID;

    private String name;

    /**
     * Constructor with name.
     * 
     * @param name
     *            Name.
     */
    public Address(final String name) {
        super();
        this.name = name;
    }

    /**
     * Returns the name.
     * 
     * @return Name.
     */
    public final String getName() {
        return name;
    }

    /**
     * Sets the name to a new value.
     * 
     * @param name
     *            Name to set.
     */
    public final void setName(final String name) {
        this.name = name;
    }

    // CHECKSTYLE:OFF Generated code
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Address other = (Address) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    // CHECKSTYLE:ON

    @Override
    public final String toString() {
        return this.getClass() + "{" + name + "}";
    }

}
