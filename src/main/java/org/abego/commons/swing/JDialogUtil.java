package org.abego.commons.swing;

import org.abego.commons.lang.exception.MustNotInstantiateException;
import org.eclipse.jdt.annotation.Nullable;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;
import java.awt.Component;
import java.awt.event.KeyEvent;

public final class JDialogUtil {

    JDialogUtil() {
        throw new MustNotInstantiateException();
    }

    public static void showInDialog(@Nullable String title, Component component) {
        final JDialog dlg = new JDialog();
        dlg.setModal(true);

        if (title != null)
            dlg.setTitle(title);

        dlg.getContentPane().add(component);
        dlg.pack();

        registerCloseOnEscapeAction(dlg);

        WindowUtil.centerOnScreen(dlg);

        dlg.setVisible(true);
        dlg.dispose();
    }

    /**
     * Make sure pressing the escape key closes/disposes the dialog
     */
    private static void registerCloseOnEscapeAction(final JDialog dialog) {
        dialog.getRootPane().registerKeyboardAction(
                e -> dialog.dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

}
