package org.abego.commons.swing;

import org.abego.commons.lang.exception.MustNotInstantiateException;

import java.awt.Point;
import java.awt.Rectangle;

public final class RectangleUtil {

    RectangleUtil() {
        throw new MustNotInstantiateException();
    }

    public static Point center(Rectangle rectangle) {
        return new Point(
                rectangle.x + rectangle.width / 2,
                rectangle.y + rectangle.height / 2);
    }


}
