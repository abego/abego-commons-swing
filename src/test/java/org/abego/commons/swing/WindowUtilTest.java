package org.abego.commons.swing;

import org.abego.commons.lang.exception.MustNotInstantiateException;
import org.abego.commons.var.Var;
import org.junit.jupiter.api.Test;

import javax.swing.JFrame;

import static org.abego.commons.var.VarUtil.newVar;
import static org.junit.jupiter.api.Assertions.*;

class WindowUtilTest {
    @Test
    void constructor() {
        assertThrows(MustNotInstantiateException.class, WindowUtil::new);
    }

    @Test
    void onWindowClosed_OK() {
        JFrame frame = new JFrame();
        Var<String> out = newVar();
        WindowUtil.onWindowClosed(frame,e->out.set("foo"));
        //TODO: hard to test without GuiTesting...
    }
}