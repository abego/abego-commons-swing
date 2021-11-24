package org.abego.commons.swing;

import org.abego.commons.lang.exception.MustNotInstantiateException;
import org.abego.commons.var.Var;
import org.junit.jupiter.api.Test;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import java.awt.event.ActionEvent;
import java.util.function.Consumer;

import static java.awt.event.ActionEvent.ACTION_PERFORMED;
import static org.abego.commons.var.VarUtil.newVar;
import static org.junit.jupiter.api.Assertions.*;

class ActionUtilTest {

    @Test
    void constructor() {
        assertThrows(MustNotInstantiateException.class, ActionUtil::new);
    }

    @Test
    void newAction_allArgs() {
        Var<String> out = newVar();
        out.set("");

        String text = "foo";
        KeyStroke accelerator = KeyStroke.getKeyStroke("A");
        String description = "bar";
        ImageIcon smallIcon = new ImageIcon();
        Consumer<ActionEvent> actionCode = e -> out.set("baz");

        Action action = ActionUtil.newAction(
                text, accelerator, description, smallIcon, actionCode);

        assertEquals(text, action.getValue(Action.NAME));
        assertEquals(accelerator, action.getValue(Action.ACCELERATOR_KEY));
        assertEquals(description, action.getValue(Action.SHORT_DESCRIPTION));
        assertEquals(smallIcon, action.getValue(Action.SMALL_ICON));

        // perform the action to check if the action code was assigned/executed
        assertEquals("", out.get());
        action.actionPerformed(new ActionEvent(this, ACTION_PERFORMED, "cmd"));
        assertEquals("baz", out.get());
    }

    @Test
    void newAction_noDescription() {
        Var<String> out = newVar();
        out.set("");

        String text = "foo";
        KeyStroke accelerator = KeyStroke.getKeyStroke("A");
        ImageIcon smallIcon = new ImageIcon();
        Consumer<ActionEvent> actionCode = e -> out.set("baz");

        Action action = ActionUtil.newAction(
                text, accelerator, smallIcon, actionCode);

        assertEquals(text, action.getValue(Action.NAME));
        assertEquals(accelerator, action.getValue(Action.ACCELERATOR_KEY));
        assertEquals(smallIcon, action.getValue(Action.SMALL_ICON));

        // perform the action to check if the action code was assigned/executed
        assertEquals("", out.get());
        action.actionPerformed(new ActionEvent(this, ACTION_PERFORMED, "cmd"));
        assertEquals("baz", out.get());
    }

    @Test
    void newAction_noIcon() {
        Var<String> out = newVar();
        out.set("");

        String text = "foo";
        KeyStroke accelerator = KeyStroke.getKeyStroke("A");
        String description = "bar";
        Consumer<ActionEvent> actionCode = e -> out.set("baz");

        Action action = ActionUtil.newAction(
                text, accelerator, description, actionCode);

        assertEquals(text, action.getValue(Action.NAME));
        assertEquals(accelerator, action.getValue(Action.ACCELERATOR_KEY));
        assertEquals(description, action.getValue(Action.SHORT_DESCRIPTION));

        // perform the action to check if the action code was assigned/executed
        assertEquals("", out.get());
        action.actionPerformed(new ActionEvent(this, ACTION_PERFORMED, "cmd"));
        assertEquals("baz", out.get());
    }

    @Test
    void newAction_noDescriptionNoIcon() {
        Var<String> out = newVar();
        out.set("");

        String text = "foo";
        KeyStroke accelerator = KeyStroke.getKeyStroke("A");
        Consumer<ActionEvent> actionCode = e -> out.set("baz");

        Action action = ActionUtil.newAction(
                text, accelerator, actionCode);

        assertEquals(text, action.getValue(Action.NAME));
        assertEquals(accelerator, action.getValue(Action.ACCELERATOR_KEY));

        // perform the action to check if the action code was assigned/executed
        assertEquals("", out.get());
        action.actionPerformed(new ActionEvent(this, ACTION_PERFORMED, "cmd"));
        assertEquals("baz", out.get());
    }
}