package org.abego.commons.swing.event;

import org.junit.jupiter.api.Test;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.text.BadLocationException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DocumentAdapterTest {

    @Test
    void smokeTest() throws BadLocationException {
        StringBuilder log = new StringBuilder();
        JTextField tf = new JTextField();

        tf.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            public void someUpdate(DocumentEvent e) {
                log.append(String.format("%s %d %d\n",
                        e.getType().toString(),
                        e.getOffset(),
                        e.getLength()));
            }
        });
        tf.setText("foo");
        tf.setText("foobar");
        tf.getDocument().remove(2,2);

        assertEquals("INSERT 0 3\n" +
                "REMOVE 0 3\n" +
                "INSERT 0 6\n" +
                "REMOVE 2 2\n",
                log.toString());
    }

}