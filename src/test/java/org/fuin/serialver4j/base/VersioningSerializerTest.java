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
import java.util.ArrayList;
import java.util.HashMap;

import my.test2.Greeter;
import my.test2.One;
import my.test2.Three;
import my.test2.Two;

import org.fuin.serialver4j.base.ClassesHistory;
import org.fuin.serialver4j.base.DeserializationException;
import org.fuin.serialver4j.base.SimpleConverterFactory;
import org.fuin.serialver4j.base.VersioningJavaSerializer;
import org.fuin.serialver4j.base.VersioningSerializer;
import org.fuin.serialver4j.hessian.VersioningBurlapSerializer;
import org.fuin.serialver4j.hessian.VersioningHessian2Serializer;
import org.fuin.serialver4j.hessian.VersioningHessianSerializer;
import org.fuin.serialver4j.xstream.VersioningXStreamSerializer;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

//TESTCODE:BEGIN
public final class VersioningSerializerTest {

    private Three obj;

    private One one;

    private Two two;

    private ClassesHistory history;

    @Before
    public final void setUp() {

        history = new ClassesHistory(new SimpleConverterFactory());

        one = new One();
        two = new Two();

        one.setTwo(two);
        two.setOne(one);

        obj = new Three();
        obj.setGreeters(new ArrayList<Greeter>());
        obj.setNames(new HashMap<String, Greeter>());

        obj.setGreeter(one);
        obj.getGreeters().add(one);
        obj.getGreeters().add(two);
        obj.getNames().put(one.getName(), one);
        obj.getNames().put(two.getName(), two);

    }

    @After
    public final void tearDown() {
        obj = null;
    }

    @Test
    public final void testBurlap() throws IOException, DeserializationException {
        assertResult((Three) serializeDeserialize(new VersioningBurlapSerializer(history), obj));
    }

    @Ignore
    // FIXME michael 03.11.2010 Hessian 1 has a problem with maps & interfaces!
    @Test
    public final void testHessian() throws IOException, DeserializationException {
        assertResult((Three) serializeDeserialize(new VersioningHessianSerializer(history), obj));
    }

    @Test
    public final void testHessian2() throws IOException, DeserializationException {
        assertResult((Three) serializeDeserialize(new VersioningHessian2Serializer(history), obj));
    }

    @Test
    public final void testJava() throws IOException, DeserializationException {
        assertResult((Three) serializeDeserialize(new VersioningJavaSerializer(history), obj));
    }

    @Test
    public final void testXStream() throws IOException, DeserializationException {
        final XStream xstream = Utils.createConfiguredXStream();
        assertResult((Three) serializeDeserialize(new VersioningXStreamSerializer(history, xstream,
                512), obj));
    }

    private void assertResult(final Three three) {

        assertThat(three).isNotNull();
        assertThat(three.getGreeter()).isEqualTo(one);
        assertThat(obj.getGreeters()).hasSize(2);
        assertThat(obj.getGreeters()).contains(one, two);
        assertThat(obj.getNames()).hasSize(2);
        assertThat(obj.getNames().keySet()).contains(one.getName(), two.getName());
        assertThat(obj.getNames().values()).contains(one, two);

        final One on = (One) obj.getNames().get(one.getName());
        final Two tw = (Two) obj.getNames().get(two.getName());
        assertThat(on.getTwo()).isSameAs(tw);
        assertThat(tw.getOne()).isSameAs(on);

    }

    private Object serializeDeserialize(final VersioningSerializer serializer, final Object obj)
            throws IOException, DeserializationException {

        final ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        serializer.serialize(out, obj);

        final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        return serializer.deserialize(in);

    }

}
// TESTCODE:END
