package org.abego.commons.swing;

import org.abego.commons.lang.exception.MustNotInstantiateException;
import org.abego.commons.var.Var;
import org.junit.jupiter.api.Test;

import javax.swing.SwingUtilities;
import java.lang.reflect.InvocationTargetException;

import static org.abego.commons.swing.SwingUtilitiesUtil.evalInEDT;
import static org.abego.commons.swing.SwingUtilitiesUtil.runAsyncInEDT;
import static org.abego.commons.swing.SwingUtilitiesUtil.runInEDT;
import static org.abego.commons.var.VarUtil.newVar;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SwingUtilitiesUtilTest {
    @Test
    void constructor() {
        assertThrows(MustNotInstantiateException.class, SwingUtilitiesUtil::new);
    }


    @Test
    void runInEDT_OK() {
        Var<Boolean> isInEDT = newVar();

        runInEDT(() -> isInEDT.set(SwingUtilities.isEventDispatchThread()));

        assertTrue(isInEDT.get());
    }

    @Test
    void runInEDT_inEDT() throws InterruptedException, InvocationTargetException {
        Var<Boolean> isInEDT = newVar();

        SwingUtilities.invokeAndWait(() ->
                runInEDT(() -> isInEDT.set(SwingUtilities.isEventDispatchThread())));

        assertTrue(isInEDT.get());
    }

    @Test
    void runAsyncInEDT_OK() throws InterruptedException, InvocationTargetException {
        Var<Boolean> isInEDT = newVar();

        runAsyncInEDT(() -> isInEDT.set(SwingUtilities.isEventDispatchThread()));

        SwingUtilities.invokeAndWait(() -> {});
        assertTrue(isInEDT.get());
    }

    @Test
    void runAsyncInEDT_inEDT() throws InterruptedException, InvocationTargetException {
        Var<Boolean> isInEDT = newVar();

        SwingUtilities.invokeAndWait(() ->
                runAsyncInEDT(() -> isInEDT.set(SwingUtilities.isEventDispatchThread())));

        SwingUtilities.invokeAndWait(() -> {});
        assertTrue(isInEDT.get());
    }

    @Test
    void evalInEDT_OK() {
        Var<Boolean> isInEDT = newVar();

        String actual = evalInEDT(() -> {
            isInEDT.set(SwingUtilities.isEventDispatchThread());
            return "foo";
        });

        assertTrue(isInEDT.get());
        assertEquals("foo", actual);
    }

    @Test
    void evalInEDT_inEDT() throws InterruptedException, InvocationTargetException {
        Var<Boolean> isInEDT = newVar();
        Var<String> actual = newVar();

        SwingUtilities.invokeAndWait(() ->
                actual.set(evalInEDT(() -> {
                    isInEDT.set(SwingUtilities.isEventDispatchThread());
                    return "foo";
                })));

        assertTrue(isInEDT.get());
        assertEquals("foo", actual.get());
    }

}