package org.abego.commons.swing;

import org.junit.jupiter.api.Test;

import javax.swing.JScrollPane;

import static org.junit.jupiter.api.Assertions.*;

class JScrollPaneUtilTest {

    @Test
    void withAllNamesSet() {
        JScrollPane scrollPane = new JScrollPane();

        JScrollPaneUtil.withAllNamesSet(scrollPane, "mySP");

        assertEquals("mySP", scrollPane.getName());
        assertEquals("mySP.verticalScrollBar", scrollPane.getVerticalScrollBar().getName());
        assertEquals("mySP.horizontalScrollBar", scrollPane.getHorizontalScrollBar().getName());
        assertEquals("mySP.viewport", scrollPane.getViewport().getName());
    }

    @Test
    void withAllNamesSetIfNonNull() {

        // non-null case
        JScrollPane scrollPane = new JScrollPane();

        JScrollPaneUtil.withAllNamesSetIfNonNull(scrollPane, "mySP");

        assertEquals("mySP.scrollPane", scrollPane.getName());
        assertEquals("mySP.verticalScrollBar", scrollPane.getVerticalScrollBar().getName());
        assertEquals("mySP.horizontalScrollBar", scrollPane.getHorizontalScrollBar().getName());
        assertEquals("mySP.viewport", scrollPane.getViewport().getName());

        // null case
        scrollPane = JScrollPaneUtil.withAllNamesSetIfNonNull(null, "mySP");
        assertNull(scrollPane);

    }
}