package org.abego.commons.swing;

import org.abego.commons.lang.exception.MustNotInstantiateException;

import java.awt.Component;
import java.awt.Container;

public final class ContainerUtil {

    ContainerUtil() {
        throw new MustNotInstantiateException();
    }

    public static boolean isPartOf(Component partCandidate, Container container) {
        Component c = partCandidate;
        while (c != null) {
            if (container == c) {
                return true;
            }
            c = c.getParent();
        }
        return false;
    }


}
