package org.abego.commons.swing;

import org.abego.commons.lang.exception.MustNotInstantiateException;
import org.eclipse.jdt.annotation.Nullable;

import javax.swing.JComponent;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

public final class JComponentUtil {

    JComponentUtil() {
        throw new MustNotInstantiateException();
    }

    public static void clearBorder(@Nullable JComponent component) {
        if (component != null) {
            component.setBorder(null);
        }
    }

    public static void onJComponentBecomesVisible(JComponent component, Runnable code) {
        component.addAncestorListener(new AncestorListener() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                code.run();
            }

            @Override
            public void ancestorRemoved(AncestorEvent event) {

            }

            @Override
            public void ancestorMoved(AncestorEvent event) {

            }
        });
    }


}
