package org.abego.commons.swing;

import org.abego.commons.lang.exception.MustNotInstantiateException;

import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Consumer;

public final class WindowUtil {

    WindowUtil() {
        throw new MustNotInstantiateException();
    }

    public static void onWindowClosed(Window window,
                                      Consumer<WindowEvent> windowClosedHandler) {

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                windowClosedHandler.accept(e);
            }
        });
    }

    /**
     * Center the <code>window</code> on the screen.
     */
    public static void centerOnScreen(Window window) {
        window.setLocationRelativeTo(null);
    }
}
