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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import my.test.Address;
import my.test.old.IdFactory;

import org.fuin.serialver4j.base.ClassesHistory;
import org.fuin.serialver4j.base.Converter;
import org.fuin.serialver4j.base.ConverterFactory;
import org.fuin.serialver4j.base.Utils;
import org.fuin.serialver4j.base.VersioningObjectInputStream;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

//TESTCODE:BEGIN
public class VersioningObjectInputStreamTest {

    private static final File DATA_DIR = new File("src/test/data");

    private static ClassesHistory history;

    @BeforeClass
    public static void setUp() throws IOException {
        final File historyFile = new File(DATA_DIR, "versions.xml");
        history = Utils.readFromFile(historyFile);
        history.init(new ConverterFactory() {
            @Override
            public Converter<?, ?> createConverter(final String converterClassName) {
                if (converterClassName.equals("my.test.old.address.v1.AddressConverter")) {
                    return new my.test.old.address.v1.AddressConverter();
                } else if (converterClassName.equals("my.test.old.address.v2.AddressConverter")) {
                    final IdFactory idFactory = new IdFactory() {
                        @Override
                        public int nextId() {
                            // Returns always the same value for this test
                            return 100000;
                        }
                    };
                    return new my.test.old.address.v2.AddressConverter(idFactory);
                }
                return null;
            }

        });
    }

    @AfterClass
    public static void tearDown() {
        history = null;
    }

    @Test
    public final void testDeserializeJavaV1() throws IOException, ClassNotFoundException {
        final File file = new File(DATA_DIR, "AddressV1-java.bin");
        final Address result = readJava(file);
        assertThat(result.getId()).isEqualTo(100000);
        assertThat(result.getFirstName()).isEqualTo("John");
        assertThat(result.getLastName()).isEqualTo("Doe");
    }

    @Test
    public final void testDeserializeJavaV2() throws IOException, ClassNotFoundException {
        final File file = new File(DATA_DIR, "AddressV2-java.bin");
        final Address result = readJava(file);
        assertThat(result.getId()).isEqualTo(100000);
        assertThat(result.getFirstName()).isEqualTo("John");
        assertThat(result.getLastName()).isEqualTo("Doe");
    }

    @Test
    public final void testDeserializeJavaV3() throws IOException, ClassNotFoundException {
        final File file = new File(DATA_DIR, "AddressV3-java.bin");
        final Address result = readJava(file);
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getFirstName()).isEqualTo("John");
        assertThat(result.getLastName()).isEqualTo("Doe");
    }

    private Address readJava(final File file) throws IOException, ClassNotFoundException {
        final VersioningObjectInputStream in = new VersioningObjectInputStream(history,
                new FileInputStream(file));
        try {
            return (Address) in.readObject();
        } finally {
            in.close();
        }
    }

    private static void writeJava(final File file, final Object obj) throws IOException {
        final ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
        try {
            out.writeObject(obj);
        } finally {
            out.close();
        }
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
        final File file = new File(DATA_DIR, "AddressV3-java.bin");
        writeJava(file, address);

        System.out.println("Written successfully: " + file);

    }

}
// TESTCODE:END
