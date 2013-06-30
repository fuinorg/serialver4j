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

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Test class 3.
 */
public final class Three implements Serializable {

    private static final long serialVersionUID = -3142439973226665122L;

    private long version = serialVersionUID;

    private Greeter greeter;

    private List<Greeter> greeters;

    private Map<String, Greeter> names;

    /**
     * Returns the greeter.
     * 
     * @return Greeter.
     */
    public final Greeter getGreeter() {
        return greeter;
    }

    /**
     * Sets the greeter to a new value.
     * 
     * @param greeter
     *            Value to set.
     */
    public final void setGreeter(final Greeter greeter) {
        this.greeter = greeter;
    }

    /**
     * Returns the list of greeters.
     * 
     * @return Greeter list.
     */
    public final List<Greeter> getGreeters() {
        return greeters;
    }

    /**
     * Sets the list of greeters to anew value.
     * 
     * @param greeters
     *            Value to set.
     */
    public final void setGreeters(final List<Greeter> greeters) {
        this.greeters = greeters;
    }

    /**
     * Returns the names map.
     * 
     * @return Map.
     */
    public final Map<String, Greeter> getNames() {
        return names;
    }

    /**
     * Sets the map to a new value.
     * 
     * @param names
     *            Valeu to set.
     */
    public final void setNames(final Map<String, Greeter> names) {
        this.names = names;
    }

}
