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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.caucho.burlap.io.BurlapInput;
import com.caucho.burlap.io.BurlapOutput;
import com.caucho.hessian.io.AbstractHessianInput;
import com.caucho.hessian.io.AbstractHessianOutput;
import com.caucho.hessian.io.ExtSerializerFactory;
import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.caucho.hessian.io.SerializerFactory;

//TESTCODE:BEGIN
public class UUIDSerializerTest {

    private SerializerFactory serializerFactory;

    @Before
    public final void setUp() {
        final ExtSerializerFactory extSerializerFactory = new ExtSerializerFactory();
        extSerializerFactory.addSerializer(UUID.class, new UUIDSerializer());
        extSerializerFactory.addDeserializer(UUID.class, new UUIDDeserializer());
        serializerFactory = new SerializerFactory();
        serializerFactory.addFactory(extSerializerFactory);
    }

    @After
    public final void tearDown() {
        serializerFactory = null;
    }

    @Test
    public final void testBurlap() throws IOException {
        execute(new HessianCreator() {
            @Override
            public AbstractHessianOutput createOutput(final OutputStream out) {
                return new BurlapOutput(out);
            }

            @Override
            public AbstractHessianInput createInput(final InputStream in) {
                return new BurlapInput(in);
            }
        });
    }

    @Test
    public final void testHessian() throws IOException {
        execute(new HessianCreator() {
            @Override
            public AbstractHessianOutput createOutput(final OutputStream out) {
                return new HessianOutput(out);
            }

            @Override
            public AbstractHessianInput createInput(final InputStream in) {
                return new HessianInput(in);
            }
        });
    }

    @Ignore
    // FIXME michael Fix test!
    @Test
    public final void testHessian2() throws IOException {
        execute(new HessianCreator() {
            @Override
            public AbstractHessianOutput createOutput(final OutputStream out) {
                return new Hessian2Output(out);
            }

            @Override
            public AbstractHessianInput createInput(final InputStream in) {
                return new Hessian2Input(in);
            }
        });
    }

    private void execute(final HessianCreator creator) throws IOException {

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final AbstractHessianOutput output = creator.createOutput(out);
        output.setSerializerFactory(serializerFactory);

        final UUID uuid = UUID.randomUUID();
        output.writeObject(uuid);

        final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        final AbstractHessianInput input = creator.createInput(in);
        input.setSerializerFactory(serializerFactory);
        final Object obj = input.readObject();

        assertThat(obj).isInstanceOf(UUID.class);
        assertThat(obj).isEqualTo(uuid);
    }

    private static interface HessianCreator {

        public AbstractHessianOutput createOutput(OutputStream out);

        public AbstractHessianInput createInput(InputStream in);

    }

}
// TESTCODE:END
