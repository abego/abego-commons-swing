package org.abego.commons.swing;

import java.awt.Point;
import java.awt.Rectangle;

import org.abego.commons.lang.exception.MustNotInstantiateException;

public final class RectangleUtil {
	
	RectangleUtil() {
        throw new MustNotInstantiateException();
    }

	public static Point center(Rectangle rectangle) {
		return new Point(
				rectangle.x+rectangle.width/2,
				rectangle.y+rectangle.height/2);
	}


}
