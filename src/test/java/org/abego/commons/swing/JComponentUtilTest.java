package org.abego.commons.swing;

import org.abego.commons.lang.exception.MustNotInstantiateException;
import org.junit.jupiter.api.Test;

import javax.swing.JButton;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JComponentUtilTest {
    @Test
    void constructor() {
        assertThrows(MustNotInstantiateException.class, JComponentUtil::new);
    }


    @Test
    void clearBorder() {
        JButton button = new JButton();
        assertNotNull(button.getBorder());

        // non-null case
        JComponentUtil.clearBorder(button);
        assertNull(button.getBorder());

        // null case
        JComponentUtil.clearBorder(null); // no exception
    }
}