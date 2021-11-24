package org.abego.commons.swing;

import org.abego.commons.lang.exception.MustNotInstantiateException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.swing.JLabel;

import static org.junit.jupiter.api.Assertions.*;

class JDialogUtilTest {

    @Test
    void constructor() {
        assertThrows(MustNotInstantiateException.class,JDialogUtil::new);
    }

    @Test
    @Disabled // Hard to test without the GUI Testing framework...
    void showInDialog() {
        JLabel label = new JLabel("bar");
        JDialogUtil.showInDialog("foo",label);
    }
}