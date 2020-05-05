package org.abego.commons.swing;

import org.abego.commons.lang.exception.MustNotInstantiateException;
import org.abego.commons.var.Var;
import org.abego.commons.var.VarUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

import static javax.swing.SwingUtilities.invokeAndWait;
import static javax.swing.SwingUtilities.invokeLater;
import static javax.swing.SwingUtilities.isEventDispatchThread;
import static org.abego.commons.lang.exception.UncheckedException.newUncheckedException;

public final class SwingUtilitiesUtil {

    /**
     * Run the runnable in the Event Dispatch Thread.
     *
     * <p>The method will return when the runnable is finished .</p>
     *
     * <p>The method may also be called from the Event Dispatch Thread.</p>
     */
    public static void runInEDT(Runnable runnable) {
        if (isEventDispatchThread()) {
            runnable.run();
        } else {
            try {
                invokeAndWait(runnable);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw newUncheckedException("Interrupted in runInEDT", e); // NON-NLS
            } catch (InvocationTargetException e) {
                throw newUncheckedException("Error in runInEDT", e); // NON-NLS
            }
        }
    }

    /**
     * Run the runnable in the Event Dispatch Thread.
     *
     * <p>The method may return before the runnable is finished, or has started.</p>
     *
     * <p>The method may also be called from the Event Dispatch Thread.</p>
     */
    public static void runAsyncInEDT(Runnable runnable) {
        if (isEventDispatchThread()) {
            runnable.run();
        } else {
            invokeLater(runnable);
        }
    }

    /**
     * Evaluate the expression in the Event Dispatch Thread and return the result.
     *
     * <p>The method may also be called from the Event Dispatch Thread.</p>
     */
    public static <T> T evalInEDT(Supplier<T> expression) {
        if (isEventDispatchThread()) {
            return expression.get();
        }

        Var<T> result = VarUtil.newVar();
        try {
            invokeAndWait(() -> result.set(expression.get()));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw newUncheckedException("Interrupted in evalInEDT", e); // NON-NLS
        } catch (InvocationTargetException e) {
            throw newUncheckedException("Error in evalInEDT", e); // NON-NLS
        }
        return result.get();
    }

    SwingUtilitiesUtil() {
        throw new MustNotInstantiateException();
    }

}
