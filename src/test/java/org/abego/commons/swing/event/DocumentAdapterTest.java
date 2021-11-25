package org.abego.commons.swing.event;

import org.junit.jupiter.api.Test;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DocumentAdapterTest {

    @Test
    void smokeTest() throws BadLocationException {
        StringBuilder log = new StringBuilder();
        JTextField tf = new JTextField();

        DocumentAdapter listener = new DocumentAdapter() {
            @Override
            public void someUpdate(DocumentEvent e) {
                log.append(String.format("%s %d %d\n",
                        e.getType().toString(),
                        e.getOffset(),
                        e.getLength()));
            }
        };
        tf.getDocument().addDocumentListener(listener);

        tf.setText("foo");
        tf.setText("foobar");
        tf.getDocument().remove(2, 2);

        listener.changedUpdate(newChangedEventSample());

        assertEquals("INSERT 0 3\n" +
                        "REMOVE 0 3\n" +
                        "INSERT 0 6\n" +
                        "REMOVE 2 2\n" +
                        "CHANGE 1 2\n",
                log.toString());
    }

    private DocumentEvent newChangedEventSample() {
        return new DocumentEvent() {
            @Override
            public int getOffset() {
                return 1;
            }

            @Override
            public int getLength() {
                return 2;
            }

            @Override
            public Document getDocument() {
                return null;
            }

            @Override
            public EventType getType() {
                return EventType.CHANGE;
            }

            @Override
            public ElementChange getChange(Element elem) {
                return null;
            }
        };
    }

}