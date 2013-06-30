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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import my.test.Address;
import my.test.old.IdFactory;

import org.fuin.serialver4j.base.ClassesHistory;
import org.fuin.serialver4j.base.Converter;
import org.fuin.serialver4j.base.ConverterFactory;
import org.fuin.serialver4j.base.DeserializationException;
import org.fuin.serialver4j.base.Utils;
import org.fuin.serialver4j.base.VersioningSerializer;
import org.junit.AfterClass;
import org.junit.BeforeClass;

//TESTCODE:BEGIN
public abstract class AbstractTestBase {

    protected static final File DATA_DIR = new File("src/test/data");

    private static ClassesHistory history;

    @BeforeClass
    public static void setUp() throws IOException {
        history = readHistory();
    }

    @AfterClass
    public static void tearDown() {
        history = null;
    }

    protected abstract VersioningSerializer createVersioningSerializer(final ClassesHistory history);

    protected Address read(final File file) throws IOException, DeserializationException {
        final InputStream in = new FileInputStream(file);
        try {
            final VersioningSerializer serializer = createVersioningSerializer(history);
            return (Address) serializer.deserialize(in);
        } finally {
            in.close();
        }
    }

    protected void write(final File file, final Object obj) throws IOException {
        write(file, obj, createVersioningSerializer(history));
    }

    protected static void write(final File file, final Object obj,
            final VersioningSerializer serializer) throws IOException {
        final OutputStream out = new FileOutputStream(file);
        try {
            serializer.serialize(out, obj);
        } finally {
            out.close();
        }
    }

    protected static ClassesHistory readHistory() throws IOException {
        final File historyFile = new File(DATA_DIR, "versions.xml");
        final ClassesHistory classesHistory = Utils.readFromFile(historyFile);
        classesHistory.init(new ConverterFactory() {
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
        return classesHistory;
    }

}
// TESTCODE:END
