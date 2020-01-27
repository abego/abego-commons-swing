package org.abego.commons.swing;

import org.abego.commons.lang.exception.MustNotInstantiateException;
import org.abego.commons.range.IntRange;

import javax.swing.text.JTextComponent;

import static org.abego.commons.range.IntRangeDefault.newIntRange;

public final class JTextComponentUtil {

    public static IntRange getSelectedRange(JTextComponent textComponent) {
        return newIntRange(textComponent.getSelectionStart(), textComponent.getSelectionEnd());
    }

    public static void setSelectedRange(JTextComponent textComponent, IntRange range) {
        textComponent.setSelectionStart(range.getStart());
        textComponent.setSelectionEnd(range.getEnd());
    }

    public static boolean isValidSelection(JTextComponent textComponent, IntRange range) {
        int length = textComponent.getText().length();
        return range.getStart() >= 0 && range.getEnd() >= 0
                && range.getStart() < length && range.getEnd() < length;
    }

    JTextComponentUtil() {
        throw new MustNotInstantiateException();
    }
}
