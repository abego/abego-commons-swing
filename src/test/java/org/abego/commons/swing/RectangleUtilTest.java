package org.abego.commons.swing;

import org.abego.commons.lang.exception.MustNotInstantiateException;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.awt.Rectangle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RectangleUtilTest {

    @Test
    void constructor() {
        assertThrows(MustNotInstantiateException.class, RectangleUtil::new);
    }


    @Test
    void center() {
        Rectangle r = new Rectangle(10, 10, 40, 20);

        Point pt = RectangleUtil.center(r);

        assertEquals("java.awt.Point[x=30,y=20]", pt.toString());
    }
}