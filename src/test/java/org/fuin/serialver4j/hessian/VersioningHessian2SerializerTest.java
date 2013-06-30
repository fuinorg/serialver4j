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
package org.fuin.serialver4j.hessian;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.io.IOException;

import my.test.Address;

import org.fuin.serialver4j.base.AbstractTestBase;
import org.fuin.serialver4j.base.ClassesHistory;
import org.fuin.serialver4j.base.DeserializationException;
import org.fuin.serialver4j.base.VersioningSerializer;
import org.fuin.serialver4j.hessian.VersioningHessian2Serializer;
import org.junit.Test;

//TESTCODE:BEGIN
public class VersioningHessian2SerializerTest extends AbstractTestBase {

    @Override
    protected final VersioningSerializer createVersioningSerializer(final ClassesHistory history) {
        return new VersioningHessian2Serializer(history);
    }

    @Test
    public final void testDeserializeHessian2V1() throws IOException, DeserializationException {
        final File file = new File(DATA_DIR, "AddressV1-hessian2.bin");
        final Address result = read(file);
        assertThat(result.getId()).isEqualTo(100000);
        assertThat(result.getFirstName()).isEqualTo("John");
        assertThat(result.getLastName()).isEqualTo("Doe");
    }

    @Test
    public final void testDeserializeHessian2V2() throws IOException, DeserializationException {
        final File file = new File(DATA_DIR, "AddressV2-hessian2.bin");
        final Address result = read(file);
        assertThat(result.getId()).isEqualTo(100000);
        assertThat(result.getFirstName()).isEqualTo("John");
        assertThat(result.getLastName()).isEqualTo("Doe");
    }

    @Test
    public final void testDeserializeHessian2V3() throws IOException, DeserializationException {
        final File file = new File(DATA_DIR, "AddressV3-hessian2.bin");
        final Address result = read(file);
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getFirstName()).isEqualTo("John");
        assertThat(result.getLastName()).isEqualTo("Doe");
    }

    /**
     * Creates the current version of the serialized files.
     * 
     * @param args
     *            Not used.
     * 
     * @throws IOException
     *             The usual suspects of I/O problems.
     */
    public static void main(final String[] args) throws IOException {

        final Address address = new Address(1, "John", "Doe");
        final File file = new File(DATA_DIR, "AddressV3-hessian2.bin");
        write(file, address, new VersioningHessian2Serializer(readHistory()));

        System.out.println("Written successfully: " + file);

    }

}
// TESTCODE:END
