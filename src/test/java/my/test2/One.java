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
package my.test2;

/**
 * Test class 1.
 */
public final class One implements Greeter {

    private static final long serialVersionUID = 2767432006230562125L;

    private long version = serialVersionUID;

    private Two two;

    /**
     * Returns two.
     * 
     * @return Two.
     */
    public final Two getTwo() {
        return two;
    }

    /**
     * Sets two to a new value.
     * 
     * @param two
     *            Value to set.
     */
    public final void setTwo(final Two two) {
        this.two = two;
    }

    @Override
    public final void greet() {
        System.out.println("Hi, this is " + getName());
    }

    @Override
    public final String getName() {
        return "one";
    }

    // CHECKSTYLE:OFF Generated code...

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
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
        One other = (One) obj;
        if (getName() == null) {
            if (other.getName() != null)
                return false;
        } else if (!getName().equals(other.getName()))
            return false;
        return true;
    }

    // CHECKSTYLE:ON

}
