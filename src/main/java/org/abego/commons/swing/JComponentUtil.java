package org.abego.commons.swing;

import org.abego.commons.lang.exception.MustNotInstantiateException;
import org.eclipse.jdt.annotation.Nullable;

import javax.swing.JComponent;

final public class JComponentUtil {

    public static void clearBorder(@Nullable JComponent component) {
        if (component != null) {
            component.setBorder(null);
        }
    }

    JComponentUtil() {
        throw new MustNotInstantiateException();
    }
}
