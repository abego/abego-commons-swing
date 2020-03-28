package org.abego.commons.swing;

import org.abego.commons.lang.exception.MustNotInstantiateException;
import org.abego.commons.range.IntRange;
import org.abego.commons.seq.Seq;
import org.eclipse.jdt.annotation.Nullable;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import javax.swing.text.LayeredHighlighter.LayerPainter;

import java.awt.Rectangle;
import java.util.ArrayList;

import static org.abego.commons.range.IntRangeDefault.newIntRange;
import static org.abego.commons.seq.SeqUtil.newSeq;

public final class JTextComponentUtil {

    public static IntRange getSelectedRange(JTextComponent textComponent) {
        return newIntRange(textComponent.getSelectionStart(), textComponent.getSelectionEnd());
    }

    public static void setSelectedRange(JTextComponent textComponent, IntRange range) {
        textComponent.setSelectionStart(range.getStart());
        textComponent.setSelectionEnd(range.getEnd());
    }

    public static boolean isValidSelection(JTextComponent textComponent, IntRange range) {
        int length = textComponent.getText().length();
        return range.getStart() >= 0 && range.getEnd() >= 0
                && range.getStart() < length && range.getEnd() < length;
    }

    /**
     * Make sure text selection highlighting is painted on top of the emphasized highlight
     */
    public static void mustPaintTextSelectionsOnTopOfHighlights(JTextComponent textComponent) {
        Highlighter highlighter = textComponent.getHighlighter();
        //see https://stackoverflow.com/a/49820141)</p>
        if (highlighter instanceof DefaultHighlighter) {
            ((DefaultHighlighter)highlighter).setDrawsLayeredHighlights(false);
        }
    }

    public static Object addHighlight(JTextComponent textComponent, IntRange range, LayerPainter highlightPainter) {
        try {
            return textComponent.getHighlighter()
                    .addHighlight(range.getStart(), range
                            .getEnd(), highlightPainter);
        } catch (BadLocationException e) {
            throw new IllegalArgumentException("Invalid range", e);
        }
    }

    public static void removeHighlight(JTextComponent textComponent, Object highlightTag) {
        textComponent.getHighlighter().removeHighlight(highlightTag);
    }

    public static Seq<Object> addHighlights(JTextComponent textComponent, Iterable<IntRange> ranges, LayerPainter highlightPainter) {
        ArrayList<Object> tags = new ArrayList<>();
        for (IntRange d : ranges) {
            tags.add(addHighlight(textComponent, d, highlightPainter));
        }
        return newSeq(tags);
    }

    public static void removeHighlights(JTextComponent textComponent, Seq<Object> highlightTags) {
        Highlighter highlighter = textComponent.getHighlighter();
        highlightTags.forEach(highlighter::removeHighlight);
    }


    JTextComponentUtil() {
        throw new MustNotInstantiateException();
    }

	@Nullable
	public static Rectangle modelToView(JTextComponent textComponent, int index) {
		try {
			return textComponent.modelToView(index);
		} catch (BadLocationException e) {
			return null;
		}
	}
	
	public static void scrollRangeToVisible(JTextComponent textComponent, IntRange range, int leftRightPadding, int topBottomPadding) {
		Rectangle r1 = modelToView(textComponent, range.getStart());
        Rectangle r2 = modelToView(textComponent, range.getEnd());
        if (r1 == null) {
        	r1 = r2;
        }
        if (r1 != null && r2 != null) {
            Rectangle fullRangeRect = r1.union(r2);
            fullRangeRect.grow(leftRightPadding, topBottomPadding);
			textComponent.scrollRectToVisible(fullRangeRect);
        }
	}
    
}
