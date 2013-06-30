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
import java.io.IOException;

import org.junit.Test;

import com.thoughtworks.xstream.XStream;

//TESTCODE:BEGIN
public final class ArchivedHistoryBuilderTest {

    protected static final File DATA_DIR = new File("src/test/data");

    @Test
    public final void testCreate() throws IOException {

        // PREPARE
        final XStream xstream = Utils.createConfiguredXStream();
        final File historyFile = new File(DATA_DIR, "versions.xml");
        final ClassesHistory classesHistory = Utils.readFromFile(historyFile);
        final String expectedXml = xstream.toXML(classesHistory);

        // TEST
        final ClassesHistory history = ArchivedHistoryBuilder.create("versionUID",
                new SimpleConverterFactory(), "my.test.old");

        // ASSERT
        assertThat(history).isNotNull();
        final VersionedClass versionedClass = history.findVersionedClass("my.test.Address");
        assertThat(versionedClass).isNotNull();
        assertThat(xstream.toXML(history)).isEqualTo(expectedXml);

    }

}
// TESTCODE:END
