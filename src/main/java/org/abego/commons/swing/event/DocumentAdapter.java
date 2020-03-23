package org.abego.commons.swing.event;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public abstract class DocumentAdapter implements DocumentListener {

    @Override
    public void insertUpdate(DocumentEvent e) {
        someUpdate(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        someUpdate(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        someUpdate(e);
    }

    public abstract void someUpdate(DocumentEvent e);
}