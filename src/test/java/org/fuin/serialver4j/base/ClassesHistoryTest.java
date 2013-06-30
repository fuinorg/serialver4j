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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.fuin.serialver4j.base.ClassVersion;
import org.fuin.serialver4j.base.ClassesHistory;
import org.fuin.serialver4j.base.Converter;
import org.fuin.serialver4j.base.ConverterFactory;
import org.fuin.serialver4j.base.SimpleConverterFactory;
import org.fuin.serialver4j.base.VersionedClass;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//TESTCODE:BEGIN
public class ClassesHistoryTest {

    private ConverterFactory converterFactory;

    @Before
    public final void setUp() {
        converterFactory = new ConverterFactory() {
            @Override
            public Converter<?, ?> createConverter(final String converterClassName) {
                return null;
            }
        };
    }

    @After
    public final void tearDown() {
        converterFactory = null;
    }

    @Test
    public final void testClassesHistoryCreateWithConverterFactory() {

        // TEST
        final ClassesHistory testee = new ClassesHistory(converterFactory);

        // ASSERT
        assertThat(testee).isNotNull();
        assertThat(testee.getConverterFactory()).isSameAs(converterFactory);

    }

    @Test
    public final void testClassesHistoryCreateWithVersionTagAndConverterFactory() {

        // PREPARE
        final String versionTag = "versionUID";

        // TEST
        final ClassesHistory testee = new ClassesHistory(versionTag, converterFactory);

        // ASSERT
        assertThat(testee).isNotNull();
        assertThat(testee.getVersionTag()).isEqualTo(versionTag);
        assertThat(testee.getConverterFactory()).isSameAs(converterFactory);

    }

    @Test
    public final void testInit() throws IOException, ClassNotFoundException {

        // PREPARE

        // Serialize the object to get null transient references
        final ClassesHistory history = new ClassesHistory(new SimpleConverterFactory());
        final ByteArrayOutputStream out = new ByteArrayOutputStream(100);
        final ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(history);
        final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        final ObjectInputStream ois = new ObjectInputStream(in);
        final ClassesHistory testee = (ClassesHistory) ois.readObject();
        assertThat(testee).isNotNull();
        assertThat(testee.getConverterFactory()).isNull();

        // TEST
        testee.init(converterFactory);

        // ASSERT
        assertThat(testee.getConverterFactory()).isSameAs(converterFactory);

    }

    @Test
    public final void testAdd() {

        // PREPARE
        final ClassesHistory testee = new ClassesHistory(converterFactory);
        final VersionedClass versionedClass = new VersionedClass("a.b.c", "Def");

        // TEST
        testee.add(versionedClass);

        // ASSERT
        assertThat(testee.findVersionedClass("a.b.c.Def")).isSameAs(versionedClass);

    }

    @Test
    public final void testRemove() {

        // PREPARE
        final ClassesHistory testee = new ClassesHistory(converterFactory);
        final VersionedClass versionedClass = new VersionedClass("a.b.c", "Def");
        testee.add(versionedClass);
        assertThat(testee.findVersionedClass("a.b.c.Def")).isSameAs(versionedClass);

        // TEST
        testee.remove(versionedClass);

        // ASSERT
        assertThat(testee.findVersionedClass("a.b.c.Def")).isNull();

    }

    @Test
    public final void testFindConverter() {

        // PREPARE
        final ClassesHistory testee = new ClassesHistory(converterFactory);
        final VersionedClass versionedClass = new VersionedClass("my.test", "Address");
        final ClassVersion classVersion = new ClassVersion(4711L, "my.test.old.address.v1.Address",
                "my.test.old.address.v1.AddressConverter");
        versionedClass.addVersion(classVersion);
        testee.add(versionedClass);

        // TEST
        final VersionedClass result = testee.findVersionedClass("my.test.Address");

        // ASSERT
        assertThat(result).isSameAs(versionedClass);

    }

}
// TESTCODE:END
