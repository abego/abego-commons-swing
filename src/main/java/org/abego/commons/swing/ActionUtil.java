package org.abego.commons.swing;

import org.abego.commons.lang.exception.MustNotInstantiateException;
import org.eclipse.jdt.annotation.Nullable;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

public class ActionUtil {
    ActionUtil() {
        throw new MustNotInstantiateException();
    }

    public static Action newAction(
            String name,
            @Nullable KeyStroke accelerator,
            @Nullable String description,
            @Nullable ImageIcon smallIcon,
            Consumer<ActionEvent> action) {
        return ActionWithEventHandler.newAction(
                name, accelerator, description, smallIcon, action);
    }

    public static Action newAction(
            String name, @Nullable KeyStroke accelerator, Consumer<ActionEvent> action) {
        return ActionWithEventHandler.newAction(
                name, accelerator, name, null, action);
    }

    public static Action newAction(
            String name,
            @Nullable KeyStroke accelerator,
            @Nullable ImageIcon smallIcon,
            Consumer<ActionEvent> action) {
        return ActionWithEventHandler.newAction(
                name, accelerator, name, smallIcon, action);
    }

    public static Action newAction(
            String name,
            @Nullable KeyStroke accelerator,
            @Nullable String description,
            Consumer<ActionEvent> action) {
        return ActionWithEventHandler.newAction(
                name, accelerator, description, null, action);
    }

}
