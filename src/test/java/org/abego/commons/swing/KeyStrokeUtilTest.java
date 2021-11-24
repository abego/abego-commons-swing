package org.abego.commons.swing;

import org.abego.commons.lang.exception.MustNotInstantiateException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import javax.swing.KeyStroke;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import static org.abego.commons.swing.KeyStrokeUtil.acceleratorText;
import static org.junit.jupiter.api.Assertions.*;

class KeyStrokeUtilTest {

    @Test
    void constructor() {
        assertThrows(MustNotInstantiateException.class, KeyStrokeUtil::new);
    }

    @Test
    @EnabledOnOs({OS.WINDOWS})
    void acceleratorText_Windows() {

        assertEquals("Insert",
                acceleratorText(KeyStroke.getKeyStroke("INSERT")));
        assertEquals("Ctrl+Delete",
                acceleratorText(KeyStroke.getKeyStroke("control DELETE")));
        assertEquals("Alt+Shift+X",
                acceleratorText(KeyStroke.getKeyStroke("alt shift X")));
        assertEquals("Alt+Shift+X",
                acceleratorText(KeyStroke.getKeyStroke("alt shift released X")));
        assertEquals("???",
                acceleratorText(KeyStroke.getKeyStroke(
                        KeyEvent.VK_C,
                        Toolkit.getDefaultToolkit().getMenuShortcutKeyMask())));
    }

    @Test
    @EnabledOnOs({OS.MAC})
    void acceleratorText_MacOS() {

        assertEquals("Insert",
                acceleratorText(KeyStroke.getKeyStroke("INSERT")));
        assertEquals("Ctrl Delete",
                acceleratorText(KeyStroke.getKeyStroke("control DELETE")));
        assertEquals("Alt+Shift X",
                acceleratorText(KeyStroke.getKeyStroke("alt shift X")));
        assertEquals("Alt+Shift X",
                acceleratorText(KeyStroke.getKeyStroke("alt shift released X")));
        assertEquals("⌘C",
                acceleratorText(KeyStroke.getKeyStroke(
                        KeyEvent.VK_C,
                        Toolkit.getDefaultToolkit().getMenuShortcutKeyMask())));
    }
}