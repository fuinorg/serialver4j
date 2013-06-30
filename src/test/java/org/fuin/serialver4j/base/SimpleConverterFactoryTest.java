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

import org.fuin.serialver4j.base.Converter;
import org.fuin.serialver4j.base.SimpleConverterFactory;
import org.junit.Test;

//TESTCODE:BEGIN
public final class SimpleConverterFactoryTest {

    @Test
    public final void testCreateConverter() {

        // PREPARE
        final SimpleConverterFactory testee = new SimpleConverterFactory();

        // TEST
        final Converter<Object, Object> converter = testee
                .createConverter("my.test.old.address.v1.AddressConverter");

        // ASSERT
        assertThat(converter).isNotNull();
        assertThat(converter.getClass()).isEqualTo(my.test.old.address.v1.AddressConverter.class);

    }
}
// TESTCODE:END
