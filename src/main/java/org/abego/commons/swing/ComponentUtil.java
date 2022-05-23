package org.abego.commons.swing;

import org.abego.commons.lang.exception.MustNotInstantiateException;
import org.eclipse.jdt.annotation.Nullable;

import javax.swing.JOptionPane;
import java.awt.Component;
import java.awt.Point;
import java.awt.Window;

public final class ComponentUtil {

    ComponentUtil() {
        throw new MustNotInstantiateException();
    }

    public static Point center(Component component) {
        return RectangleUtil.center(component.getBounds());
    }

    public static Window findWindow(@Nullable Component component) {
        Component result = component;
        while (result != null && !(result instanceof Window)) {
            result = result.getParent();
        }

        return result != null ? (Window) result : JOptionPane.getRootFrame();
    }
}
