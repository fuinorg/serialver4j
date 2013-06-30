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
package org.fuin.serialver4j.base;

import static org.fest.assertions.Assertions.assertThat;

import org.fuin.serialver4j.base.ClassVersion;
import org.junit.Test;

//TESTCODE:BEGIN
public final class ClassVersionTest {

    @Test
    public final void testCreateAndGet() {

        // PREPARE
        final Long serialVersionUID = 4711L;
        final String className = "a.b.C";
        final String converterClassName = "a.b.ConverterC";

        // TEST
        final ClassVersion testee = new ClassVersion(serialVersionUID, className,
                converterClassName);

        // ASSERT
        assertThat(testee).isNotNull();
        assertThat(testee.getSerialVersionUID()).isEqualTo(serialVersionUID);
        assertThat(testee.getClassName()).isEqualTo(className);
        assertThat(testee.getConverterClassName()).isEqualTo(converterClassName);

    }

}
// TESTCODE:END
