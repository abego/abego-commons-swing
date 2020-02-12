/*
 * MIT License
 *
 * Copyright (c) 2020 Udo Borkowski, (ub@abego.org)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.abego.commons.swing.formattedtext;

import org.abego.commons.formattedtext.FormattedText;
import org.abego.commons.lang.exception.MustNotInstantiateException;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.abego.commons.test.JUnit5Util.assertThrowsWithMessage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FormattedTextForSwingTest {
    @Test
    void constructor() {
        assertThrows(MustNotInstantiateException.class, FormattedTextForSwing::new);
    }

    @Test
    void asHTMLForSwing_empty() {
        FormattedText ft = builder -> {};

        String html = FormattedTextForSwing.toHTMLForSwing(ft);

        assertEquals("", html);
    }

    @Test
    void asHTMLForSwing_plainText() {

        FormattedText ft = builder -> builder.text("foo bar");

        String html = FormattedTextForSwing.toHTMLForSwing(ft);

        assertEquals("foo bar", html);
    }

    @Test
    void asHTMLForSwing_colorBlock() {
        FormattedText ft =
                builder -> builder.text("foo ").beginColor(Color.red).text("bar").end();

        String html = FormattedTextForSwing.toHTMLForSwing(ft);

        assertEquals("<html>foo <font color=\"#ff0000\">bar</font></html>", html);
    }

    @Test
    void asHTMLForSwing_boldBlock() {
        FormattedText ft =
                builder -> builder.text("foo ").beginBold().text("bar").end();

        String html = FormattedTextForSwing.toHTMLForSwing(ft);

        assertEquals("<html>foo <b>bar</b></html>", html);
    }

    @Test
    void asHTMLForSwing_italicBlock() {
        FormattedText ft =
                builder -> builder.text("foo ").beginItalic().text("bar").end();

        String html = FormattedTextForSwing.toHTMLForSwing(ft);

        assertEquals("<html>foo <i>bar</i></html>", html);
    }

    @Test
    void asHTMLForSwing_boldAnditalicBlocks() {
        FormattedText ft =
                builder -> builder
                        .beginBold().text("foo ")
                        .beginItalic().text("bar").end()
                        .text("!").end();

        String html = FormattedTextForSwing.toHTMLForSwing(ft);

        assertEquals("<html><b>foo <i>bar</i>!</b></html>", html);
    }

    @Test
    void asHTMLForSwing_missingBegin() {
        FormattedText ft = builder -> builder.text("foo bar").end();


        assertThrowsWithMessage(
                IllegalStateException.class,
                "Extra end. begin and end must be balanced.",
                () -> FormattedTextForSwing.toHTMLForSwing(ft));
    }

    @Test
    void asHTMLForSwing_missingEnd() {
        FormattedText ft = builder -> builder.text("foo ").beginColor(Color.red).text("bar");

        assertThrowsWithMessage(
                IllegalStateException.class,
                "Missing end. begin and end must be balanced.",
                () -> FormattedTextForSwing.toHTMLForSwing(ft));
    }

}