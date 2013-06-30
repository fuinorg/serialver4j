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
 * Test class 2.
 */
public final class Two implements Greeter {

    private static final long serialVersionUID = -3507683469750840693L;

    private long version = serialVersionUID;

    private One one;

    /**
     * Returns one.
     * 
     * @return One.
     */
    public final One getOne() {
        return one;
    }

    /**
     * Sets one to a new value.
     * 
     * @param one
     *            Value to set.
     */
    public final void setOne(final One one) {
        this.one = one;
    }

    @Override
    public final void greet() {
        System.out.println("Hi, this is " + getName());
    }

    @Override
    public final String getName() {
        return "two";
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
        Two other = (Two) obj;
        if (getName() == null) {
            if (other.getName() != null)
                return false;
        } else if (!getName().equals(other.getName()))
            return false;
        return true;
    }

    // CHECKSTYLE:ON

}
