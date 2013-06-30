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

import java.io.Serializable;

import org.fuin.serialver4j.base.Archived;

/**
 * Test class version 2.
 */
@Deprecated
@Archived(clasz = my.test.Address.class, converter = AddressConverter.class)
public class Address implements Serializable {

    private static final long serialVersionUID = -2466841694916192299L;

    private long versionUID = serialVersionUID;

    private String firstName;

    private String lastName;

    /**
     * Constructor with first and last name.
     * 
     * @param firstName
     *            First name.
     * @param lastName
     *            Last name.
     */
    public Address(final String firstName, final String lastName) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Returns the first name.
     * 
     * @return First name.
     */
    public final String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name to a new value.
     * 
     * @param firstName
     *            First name to set.
     */
    public final void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the last name.
     * 
     * @return Last name.
     */
    public final String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name to a new value.
     * 
     * @param lastName
     *            Last name to set.
     */
    public final void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    // CHECKSTYLE:OFF Generated code

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
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
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        return true;
    }

    // CHECKSTYLE:ON

    @Override
    public final String toString() {
        return this.getClass() + "{" + firstName + " " + lastName + "}";
    }

}
