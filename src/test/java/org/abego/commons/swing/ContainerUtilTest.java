package org.abego.commons.swing;

import org.abego.commons.lang.exception.MustNotInstantiateException;
import org.junit.jupiter.api.Test;

import javax.swing.JButton;
import javax.swing.JPanel;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContainerUtilTest {

    @Test
    void constructor() {
        assertThrows(MustNotInstantiateException.class, ContainerUtil::new);
    }

    @Test
    void isPartOf() {
        JPanel panel = new JPanel();
        JButton btn = new JButton();
        JButton externalBtn = new JButton();
        panel.add(btn);

        assertTrue(ContainerUtil.isPartOf(btn, panel));
        assertFalse(ContainerUtil.isPartOf(externalBtn, panel));
    }
}