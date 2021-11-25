package org.abego.commons.swing;

import org.abego.commons.range.IntRange;
import org.abego.commons.seq.Seq;
import org.abego.commons.test.JUnit5Util;
import org.abego.commons.var.VarNullable;
import org.eclipse.jdt.annotation.Nullable;
import org.junit.jupiter.api.Test;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultHighlighter;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Arrays;

import static javax.swing.SwingUtilities.invokeLater;
import static org.abego.commons.range.IntRangeDefault.newIntRange;
import static org.abego.commons.swing.JTextComponentUtil.addHighlight;
import static org.abego.commons.swing.JTextComponentUtil.addHighlights;
import static org.abego.commons.swing.JTextComponentUtil.getSelectedRange;
import static org.abego.commons.swing.JTextComponentUtil.isValidSelection;
import static org.abego.commons.swing.JTextComponentUtil.modelToView;
import static org.abego.commons.swing.JTextComponentUtil.mustPaintTextSelectionsOnTopOfHighlights;
import static org.abego.commons.swing.JTextComponentUtil.removeHighlight;
import static org.abego.commons.swing.JTextComponentUtil.removeHighlights;
import static org.abego.commons.swing.JTextComponentUtil.scrollRangeToVisible;
import static org.abego.commons.swing.JTextComponentUtil.setSelectedRange;
import static org.abego.commons.test.JUnit5Util.assertThrowsWithMessage;
import static org.abego.commons.var.VarUtil.newVarNullable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JTextComponentUtilTest {

    @Test
    void setSelectedRange_getSelectedRange_OK() {
        JTextField field = new JTextField();
        field.setText("foobar");

        setSelectedRange(field, newIntRange(2, 4));
        assertEquals(2, field.getSelectionStart());
        assertEquals(4, field.getSelectionEnd());

        IntRange range = getSelectedRange(field);

        JUnit5Util.assertIntRangeEquals(2, 4, range);
    }

    @Test
    void isValidSelection_OK() {
        JTextField field = new JTextField();
        field.setText("foobar");

        assertTrue(isValidSelection(field, newIntRange(0, 0)));
        assertTrue(isValidSelection(field, newIntRange(6, 6)));
        assertTrue(isValidSelection(field, newIntRange(0, 6)));
        assertTrue(isValidSelection(field, newIntRange(2, 4)));
        assertFalse(isValidSelection(field, newIntRange(-1, 4)));
        assertFalse(isValidSelection(field, newIntRange(5, 7)));
        assertFalse(isValidSelection(field, newIntRange(7, 8)));
    }


    @Test
    void mustPaintTextSelectionsOnTopOfHighlights_OK() {
        JTextField field = new JTextField();

        mustPaintTextSelectionsOnTopOfHighlights(field);

        // This is a white-box test: we check a specific implementation...
        // (we may need to adapt the test when the implementation changes)
        assertFalse(((DefaultHighlighter) field.getHighlighter())
                .getDrawsLayeredHighlights());

    }

    @Test
    void addHighlight_OK() {
        JTextField field = new JTextField();
        field.setText("foobar");
        Object hl = addHighlight(field, newIntRange(2, 4), DefaultHighlighter.DefaultPainter);

        assertNotNull(hl);

        assertThrowsWithMessage(
                IllegalArgumentException.class,
                "Invalid range",
                () -> addHighlight(field, newIntRange(-2, -1), DefaultHighlighter.DefaultPainter));
    }

    @Test
    void removeHighlight_OK() {
        JTextField field = new JTextField();
        field.setText("foobar");

        Object hl = addHighlight(field, newIntRange(2, 4), DefaultHighlighter.DefaultPainter);
        assertEquals(1, field.getHighlighter().getHighlights().length);

        removeHighlight(field, hl);

        assertEquals(0, field.getHighlighter().getHighlights().length);
    }

    @Test
    void addHighlights_OK() {
        JTextField field = new JTextField();
        field.setText("foobar");

        Seq<Object> hls = addHighlights(
                field,
                Arrays.asList(newIntRange(2, 4), newIntRange(4, 6)),
                DefaultHighlighter.DefaultPainter);

        assertEquals(2, field.getHighlighter().getHighlights().length);
        assertEquals(2, hls.size());
    }

    @Test
    void removeHighlights_OK() {
        JTextField field = new JTextField();
        field.setText("foobar");

        Seq<Object> hls = addHighlights(
                field,
                Arrays.asList(newIntRange(2, 4), newIntRange(4, 6)),
                DefaultHighlighter.DefaultPainter);

        assertEquals(2, field.getHighlighter().getHighlights().length);
        assertEquals(2, hls.size());

        removeHighlights(field, hls);

        assertEquals(0, field.getHighlighter().getHighlights().length);
    }

    @Test
    void modelToView_OK() {
        JTextField field = new JTextField();
        field.setText("foobar");
        VarNullable<Rectangle> bounds = newVarNullable();

        JComponentUtil.onJComponentBecomesVisible(field, () -> {
            bounds.set(modelToView(field, 2));
            invokeLater(() -> {
                ComponentUtil.findWindow(field).dispose();
            });
        });
        JDialogUtil.showInDialog(null, field);

        @Nullable Rectangle rectangle = bounds.get();
        assertTrue(rectangle != null && !rectangle.isEmpty());
    }

    @Test
    void scrollRangeToVisible_OK() {
        JTextArea area = new JTextArea();
        area.setText("f\no\no\nb\na\nr\n");
        JScrollPane pane = new JScrollPane(area);
        pane.setPreferredSize(new Dimension(100, 40));

        JComponentUtil.onJComponentBecomesVisible(area, () -> {
            scrollRangeToVisible(area, newIntRange(8, 9), 0, 0);
            invokeLater(() -> {
                ComponentUtil.findWindow(area).dispose();
            });
        });
        JDialogUtil.showInDialog(null, pane);

        // check if it is scrolled
        assertNotEquals(0, area.getVisibleRect().y);
    }
}