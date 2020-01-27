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

import org.abego.commons.formattedtext.FormattedTextProcessor;

import java.awt.Color;
import java.util.Stack;

import static org.abego.commons.lang.StringUtil.htmlEscaped;

/**
 * see https://docs.oracle.com/javase/tutorial/uiswing/components/html.html
 */
final class FormattedTextProcessorForSwingHTML implements FormattedTextProcessor<String> {

    private final StringBuilder text = new StringBuilder();
    private final Stack<Runnable> endAction = new Stack<>();
    private boolean isFormatted = false;
    private int boldLevel = 0;
    private int italicLevel = 0;


    public static FormattedTextProcessorForSwingHTML newFormattedTextRendererForSwing() {
        return new FormattedTextProcessorForSwingHTML();
    }

    private FormattedTextProcessorForSwingHTML() {
    }

    @Override
    public FormattedTextProcessorForSwingHTML process(String s) {
        text.append(htmlEscaped(s));
        return this;
    }

    @Override
    public FormattedTextProcessor<String> beginBlock() {
        // unsupported block types just do nothing (at begin and end)
        atEndDoNothing();
        return this;
    }

    @Override
    public FormattedTextProcessorForSwingHTML beginColor(Color color) {
        isFormatted = true;

        text.append(String.format("<font color=\"#%02x%02x%02x\">",
                                  color.getRed(), color.getGreen(), color.getBlue()));
        atEndAppendText("</font>");
        return this;
    }

    @Override
    public FormattedTextProcessorForSwingHTML beginBold() {
        isFormatted = true;
        if (boldLevel++ == 0) {
            text.append("<b>");
        }
        endAction.push(() -> {
            boldLevel--;
            if (boldLevel == 0) {
                text.append("</b>");
            }
        });
        return this;
    }

    @Override
    public FormattedTextProcessorForSwingHTML beginItalic() {
        isFormatted = true;
        if (italicLevel++ == 0) {
            text.append("<i>");
        }
        endAction.push(() -> {
            italicLevel--;
            if (italicLevel == 0) {
                text.append("</i>");
            }
        });
        return this;
    }

    @Override
    public FormattedTextProcessorForSwingHTML end() {
        if (endAction.empty()) {
            throw new IllegalStateException("Extra end. begin and end must be balanced.");
        }
        endAction.pop().run();
        return this;
    }

    public String build() {
        if (!endAction.empty()) {
            throw new IllegalStateException("Missing end. begin and end must be balanced.");
        }
        return isFormatted ? "<html>" + text.toString() + "</html>" : text.toString();
    }

    private void atEndDoNothing() {
        endAction.push(() -> {});
    }

    private void atEndAppendText(String textToAppend) {
        endAction.push(() -> this.text.append(textToAppend));
    }
}
