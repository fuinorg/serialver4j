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
import static org.junit.Assert.fail;

import org.fuin.serialver4j.base.ContentNotFoundException;
import org.fuin.serialver4j.base.Utils;
import org.junit.Test;

//TESTCODE:BEGIN
public class UtilsTest {

    private static final String TEST_XML = "<my.test.Address><versionUID>5570679541850483841</versionUID>"
            + "<id>1</id><firstName>John</firstName><lastName>Doe</lastName></my.test.Address>";

    @Test
    public final void testGetContentSingleCharTag() throws ContentNotFoundException {
        assertThat(Utils.getContent(TEST_XML, "<", ">")).isEqualTo("my.test.Address");
    }

    @Test
    public final void testGetContentXmlTag() throws ContentNotFoundException {
        assertThat(Utils.getContent(TEST_XML, "<versionUID>", "</versionUID>")).isEqualTo(
                "5570679541850483841");
    }

    @Test
    public final void testGetContentBeginTagNotFound() throws ContentNotFoundException {
        try {
            Utils.getContent("</b></a>", "<b>", "</b>");
            fail("Expected " + ContentNotFoundException.class);
        } catch (final ContentNotFoundException ex) {
            assertThat(ex.getMessage()).containsIgnoringCase("begin tag not found");
        }
    }

    @Test
    public final void testGetContentEndTagNotFound() throws ContentNotFoundException {
        try {
            Utils.getContent("<a><b>", "<b>", "</b>");
            fail("Expected " + ContentNotFoundException.class);
        } catch (final ContentNotFoundException ex) {
            assertThat(ex.getMessage()).containsIgnoringCase("end tag not found");
        }
    }

    @Test
    public final void testGetLongContent() throws ContentNotFoundException {
        assertThat(Utils.getLongContent(TEST_XML, "<versionUID>", "</versionUID>")).isEqualTo(
                5570679541850483841L);
    }

    @Test
    public final void testGetLongContentNoLongValue() throws ContentNotFoundException {
        try {
            Utils.getLongContent("<a>xyz</a>", "<a>", "</a>");
            fail("Expected " + ContentNotFoundException.class);
        } catch (final ContentNotFoundException ex) {
            assertThat(ex.getMessage()).containsIgnoringCase("is not a long value");
        }
    }

}
// TESTCODE:END
