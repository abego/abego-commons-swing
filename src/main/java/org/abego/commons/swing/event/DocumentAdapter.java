package org.abego.commons.swing.event;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public interface DocumentAdapter extends DocumentListener {

    default void insertUpdate(DocumentEvent e) {
        someUpdate(e);
    }

    default void removeUpdate(DocumentEvent e) {
        someUpdate(e);
    }

    default void changedUpdate(DocumentEvent e) {
        someUpdate(e);
    }

    void someUpdate(DocumentEvent e);
}