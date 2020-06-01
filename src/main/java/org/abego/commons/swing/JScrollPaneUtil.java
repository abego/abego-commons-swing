package org.abego.commons.swing;

import org.eclipse.jdt.annotation.Nullable;

import javax.swing.JScrollPane;

public final class JScrollPaneUtil {

    /**
     * Sets the names of {@code scrollPane} and its parts to 
     * "{@code baseName}.scrollPane", "{@code baseName}.viewport", 
     * "{@code baseName}.horizontalScrollBar", 
     * "{@code baseName}.verticalScrollBar" and returns {@code scrollPane}.
     */
    public static <T extends JScrollPane> T withAllNamesSet(T scrollPane,
                                                            String baseName) {
        scrollPane.setName(baseName + ".scrollPane");
        scrollPane.getViewport().setName(baseName + ".viewport");
        scrollPane.getHorizontalScrollBar()
                .setName(baseName + ".horizontalScrollBar");
        scrollPane.getVerticalScrollBar()
                .setName(baseName + ".verticalScrollBar");

        return scrollPane;
    }

    /**
     * Set the names of {@code scrollPane} and its parts to "{@code baseName}.scrollPane",
     * "{@code baseName}.viewport", "{@code baseName}.horizontalScrollBar", "{@code baseName}
     * .verticalScrollBar" and return {@code scrollPane}.
     *
     * <p>When {@code scrollPane} is {@code null} just return {@code null}.</p>
     */
    @Nullable
    public static <T extends JScrollPane> T withAllNamesSetIfNonNull(
            @Nullable T scrollPane,
            String baseName) {
        if (scrollPane != null) {
            withAllNamesSet(scrollPane, baseName);
        }
        return scrollPane;
    }
}
