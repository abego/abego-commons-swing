package org.abego.commons.swing;

import org.abego.commons.lang.exception.MustNotInstantiateException;

import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Consumer;

final public class WindowUtil {

    public static void onWindowClosed(Window window,
                                      Consumer<WindowEvent> windowClosedHandler) {

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                windowClosedHandler.accept(e);
            }
        });
    }

    WindowUtil() {
        throw new MustNotInstantiateException();
    }
}
