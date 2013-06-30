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
import org.fuin.serialver4j.base.VersionedClass;
import org.junit.Test;

//TESTCODE:BEGIN
public final class VersionedClassTest {

    @Test
    public final void testCreate() {

        // PREPARE
        final String packageName = "my.test";
        final String className = "Address";

        // TEST
        final VersionedClass testee = new VersionedClass(packageName, className);

        // ASSERT
        assertThat(testee.getPackageName()).isEqualTo(packageName);
        assertThat(testee.getSimpleClassName()).isEqualTo(className);
        assertThat(testee.getName()).isEqualTo(packageName + "." + className);
        assertThat(testee.getArchivedClasses()).isEmpty();

    }

    @Test
    public final void testInit() throws IOException, ClassNotFoundException {

        // PREPARE

        // Serialize the object to get null transient references
        final VersionedClass versionedClass = new VersionedClass("my.test", "Address");
        final ByteArrayOutputStream out = new ByteArrayOutputStream(100);
        final ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(versionedClass);
        final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        final ObjectInputStream ois = new ObjectInputStream(in);
        final VersionedClass testee = (VersionedClass) ois.readObject();
        assertThat(testee).isNotNull();
        assertThat(testee.getVersionMap()).isNull();

        // TEST
        testee.init();

        // ASSERT
        assertThat(testee.getVersionMap()).isNotNull();

    }

    @Test
    public final void testAddVersion() {

        // PREPARE
        final VersionedClass testee = new VersionedClass("my.test", "Address");
        final Class<?> oldClass = my.test.old.address.v1.Address.class;
        final ClassVersion classVersion = new ClassVersion(4711L, oldClass.getName(),
                "my.test.old.address.v1.AddressConverter");

        // TEST
        testee.addVersion(classVersion);

        // ASSERT
        assertThat(testee.getArchivedClasses()).containsExactly(oldClass);

    }

    @Test
    public final void testRemoveVersion() {

        // PREPARE
        final VersionedClass testee = new VersionedClass("my.test", "Address");
        final Class<?> oldClass = my.test.old.address.v1.Address.class;
        final ClassVersion classVersion = new ClassVersion(4711L, oldClass.getName(),
                "my.test.old.address.v1.AddressConverter");
        testee.addVersion(classVersion);
        assertThat(testee.getArchivedClasses()).containsExactly(oldClass);

        // TEST
        testee.removeVersion(classVersion);

        // PREPARE
        assertThat(testee.getArchivedClasses()).isEmpty();

    }

    @Test
    public final void testFindArchivedClass() {

        // PREPARE
        final VersionedClass testee = new VersionedClass("my.test", "Address");
        final Class<?> oldClass = my.test.old.address.v1.Address.class;
        final ClassVersion classVersion = new ClassVersion(4711L, oldClass.getName(),
                "my.test.old.address.v1.AddressConverter");
        testee.addVersion(classVersion);

        // TEST
        final Class<?> result = testee.findArchivedClass(4711L);

        // ASSERT
        assertThat(result).isEqualTo(oldClass);

    }

}
// TESTCODE:END
