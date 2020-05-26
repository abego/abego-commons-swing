package org.abego.commons.swing;

import org.abego.commons.lang.exception.MustNotInstantiateException;

import java.awt.Component;
import java.awt.Point;

public final class ComponentUtil {

    ComponentUtil() {
        throw new MustNotInstantiateException();
    }

    public static Point center(Component component) {
        return RectangleUtil.center(component.getBounds());
    }

}
