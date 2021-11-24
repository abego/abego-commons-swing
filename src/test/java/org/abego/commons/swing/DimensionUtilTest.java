package org.abego.commons.swing;

import org.abego.commons.lang.exception.MustNotInstantiateException;
import org.junit.jupiter.api.Test;

import java.awt.Dimension;

import static org.abego.commons.swing.DimensionUtil.contains;
import static org.abego.commons.swing.DimensionUtil.shrinkToFitFactor;
import static org.junit.jupiter.api.Assertions.*;

class DimensionUtilTest {

    @Test
    void constructor() {
        assertThrows(MustNotInstantiateException.class, DimensionUtil::new);
    }

    @Test
    void shrinkToFitFactor_OK() {
        Dimension smallBox = new Dimension(10, 20);
        Dimension tooTallBox = new Dimension(100, 200);
        Dimension tooWideBox = new Dimension(200, 100);
        Dimension boundingBox = new Dimension(100, 100);

        assertEquals(1.0, shrinkToFitFactor(smallBox, boundingBox));
        assertEquals(0.5, shrinkToFitFactor(tooWideBox, boundingBox));
        assertEquals(0.5, shrinkToFitFactor(tooTallBox, boundingBox));
        assertEquals(1.0, shrinkToFitFactor(boundingBox, boundingBox));
    }

    @Test
    void contains_OK() {
        Dimension dimension = new Dimension(100, 100);
        Dimension smallBox = new Dimension(10, 20);
        Dimension tallBox = new Dimension(50, 100);
        Dimension wideBox = new Dimension(100, 50);
        Dimension tooTallBox = new Dimension(50, 200);
        Dimension tooWideBox = new Dimension(200, 50);
        Dimension bigBox = new Dimension(200, 200);

        assertTrue(contains(dimension, dimension));

        assertTrue(contains(dimension, smallBox));
        assertTrue(contains(dimension, tallBox));
        assertTrue(contains(dimension, wideBox));
        assertFalse(contains(dimension, tooTallBox));
        assertFalse(contains(dimension, tooWideBox));
        assertFalse(contains(dimension, bigBox));

        assertFalse(contains(smallBox, dimension));
        assertFalse(contains(tallBox, dimension));
        assertFalse(contains(wideBox, dimension));
        assertFalse(contains(tooTallBox, dimension));
        assertFalse(contains(tooWideBox, dimension));
        assertTrue(contains(bigBox, dimension));
    }
}