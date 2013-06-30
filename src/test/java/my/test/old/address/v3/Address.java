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
package my.test.old.address.v3;

import java.io.Serializable;

/**
 * Test class version 3.
 */
public class Address implements Serializable {

    private static final long serialVersionUID = 5570679541850483841L;

    private long versionUID = serialVersionUID;

    private int id;

    private String firstName;

    private String lastName;

    /**
     * Constructor with all data.
     * 
     * @param id
     *            Unique id.
     * @param firstName
     *            First name.
     * @param lastName
     *            Last name.
     */
    public Address(final int id, final String firstName, final String lastName) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Returns the id.
     * 
     * @return Unique id.
     */
    public final int getId() {
        return id;
    }

    /**
     * Sets the id to a new value.
     * 
     * @param id
     *            Unique id to set.
     */
    public final void setId(final int id) {
        this.id = id;
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
        result = prime * result + id;
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
        if (id != other.id)
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
        return this.getClass() + "{" + firstName + " " + lastName + " [" + id + "]" + "}";
    }
}
