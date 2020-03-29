package org.abego.commons.swing;

import static javax.swing.SwingUtilities.invokeAndWait;
import static javax.swing.SwingUtilities.isEventDispatchThread;
import static org.abego.commons.lang.exception.UncheckedException.newUncheckedException;

import java.lang.reflect.InvocationTargetException;

import org.abego.commons.lang.exception.MustNotInstantiateException;

public final class SwingUtilitiesUtil {
    
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

	SwingUtilitiesUtil() {
        throw new MustNotInstantiateException();
    }

}
