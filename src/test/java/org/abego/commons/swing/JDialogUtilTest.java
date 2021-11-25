package org.abego.commons.swing;

import org.abego.commons.lang.exception.MustNotInstantiateException;
import org.junit.jupiter.api.Test;

import javax.swing.JLabel;
import java.awt.AWTException;

import static org.abego.commons.swing.ComponentUtil.findWindow;
import static org.abego.commons.swing.JComponentUtil.onJComponentBecomesVisible;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JDialogUtilTest {

    @Test
    void constructor() {
        assertThrows(MustNotInstantiateException.class,JDialogUtil::new);
    }

    @Test
    void showInDialog() {
        JLabel label = new JLabel("bar");

        // close the dialog after it became visible
        onJComponentBecomesVisible(label,()-> findWindow(label).dispose());

        JDialogUtil.showInDialog("foo", label);
    }

    @Test
    void showInDialog_noTitle() throws AWTException {
        JLabel label = new JLabel("bar");

        // close the dialog after it became visible
        onJComponentBecomesVisible(label,()-> findWindow(label).dispose());

        JDialogUtil.showInDialog(null,label);
    }
}