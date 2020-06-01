package org.abego.commons.swing;

import org.abego.commons.lang.exception.MustNotInstantiateException;
import org.junit.jupiter.api.Test;

import javax.swing.JButton;

import java.awt.Point;

import static org.junit.jupiter.api.Assertions.*;

class ComponentUtilTest {

    @Test
    void constructor() {
        assertThrows(MustNotInstantiateException.class,ComponentUtil::new);
    }


    @Test
    void center() {
        JButton btn = new JButton();
        btn.setBounds(10,10,40, 20);

        Point r = ComponentUtil.center(btn);
        
        assertEquals("java.awt.Point[x=30,y=20]",r.toString());
    }
}