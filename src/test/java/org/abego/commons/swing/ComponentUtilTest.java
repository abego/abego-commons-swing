package org.abego.commons.swing;

import org.abego.commons.lang.exception.MustNotInstantiateException;
import org.junit.jupiter.api.Test;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.Point;
import java.awt.Window;

import static org.abego.commons.swing.ComponentUtil.findWindow;
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

    @Test
    void findWindow_ok() {
        JTextField component = new JTextField();
        JFrame frame = new JFrame();

        frame.add(component);

        Window window = findWindow(component);

        assertEquals(frame,window);
    }

    @Test
    void findWindow_orphanComponent() {
        JTextField component = new JTextField();

        Window window = findWindow(component);

        assertEquals(JOptionPane.getRootFrame(),window);
    }

    @Test
    void findWindow_null() {
        Window window = findWindow(null);

        assertEquals(JOptionPane.getRootFrame(),window);
    }
}
