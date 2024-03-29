/*
 * MIT License
 *
 * Copyright (c) 2019 Udo Borkowski, (ub@abego.org)
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

package org.abego.commons.swing;

import org.abego.commons.lang.exception.MustNotInstantiateException;

import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;

import static org.abego.commons.lang.SystemUtil.isMacOS;

public final class KeyStrokeUtil {
    KeyStrokeUtil() {
        throw new MustNotInstantiateException();
    }

    public static String acceleratorText(KeyStroke keyStroke) {
        String acceleratorText = "";
        int modifiers = keyStroke.getModifiers();
        if (modifiers > 0) {
            acceleratorText = KeyEvent.getModifiersExText(modifiers);
            if (!isMacOS()) {
                acceleratorText += "+";
            } else if (endsWithLetter(acceleratorText)) {
                acceleratorText += " ";
            }
        }
        acceleratorText += KeyEvent.getKeyText(keyStroke.getKeyCode());
        return acceleratorText;
    }

    private static boolean endsWithLetter(String text) {
        return !text.isEmpty() && Character.isLetter(text.charAt(text.length() - 1));
    }


}
