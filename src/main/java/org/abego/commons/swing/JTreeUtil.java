package org.abego.commons.swing;

import java.awt.Rectangle;
import java.util.EnumSet;

import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.tree.TreePath;

import org.abego.commons.lang.StringUtil;
import org.eclipse.jdt.annotation.NonNull;

public class JTreeUtil {
    private static final String HIDDEN_ITEM_SUFFIX = " (hidden)"; //NON-NLS
    private static final String SELECTED_ITEM_SUFFIX = " (selected)"; //NON-NLS
    
    public static String toDebugString(JTree jTree) {
        return toDebugString(jTree, EnumSet.noneOf(JTreeDebugStringFlag.class));
    }

    public static String toDebugString(JTree jTree,
                                       JTreeDebugStringFlag firstFlag,
                                       @NonNull JTreeDebugStringFlag... moreFlags) {
        return toDebugString(jTree, EnumSet.of(firstFlag, moreFlags));
    }

    public static String toDebugString(JTree jTree, EnumSet<JTreeDebugStringFlag> flags) {
        StringBuilder result = new StringBuilder();
        int n = jTree.getRowCount();
        for (int i = 0; i < n; i++) {
            TreePath path = jTree.getPathForRow(i);
            result.append(StringUtil.repeat("  ", path.getPathCount() - 1));
            String t = jTree.convertValueToText(
                    path.getLastPathComponent(),
                    jTree.isPathSelected(path),
                    jTree.isExpanded(path),
                    true, i, true);
            result.append(t);
            if (jTree.isPathSelected(path) && flags.contains(JTreeDebugStringFlag.MARK_SELECTED_ITEM)) {
                result.append(SELECTED_ITEM_SUFFIX);
            }
            if (!isInViewport(jTree, path) && flags.contains(JTreeDebugStringFlag.MARK_HIDDEN_ITEM)) {
                result.append(HIDDEN_ITEM_SUFFIX);
            }
            result.append("\n");
        }
        return result.toString();
    }

    public enum JTreeDebugStringFlag {
        MARK_SELECTED_ITEM,
        MARK_HIDDEN_ITEM,
    }

    private static boolean isInViewport(JTree jTree, TreePath path) {
        Rectangle nodeRect = jTree.getPathBounds(path);
        if (nodeRect == null || (!(jTree.getParent() instanceof JViewport))) {
            return false;
        }

        JViewport viewport = (JViewport) jTree.getParent();
        Rectangle viewRect = viewport.getViewRect();
        return nodeRect.intersects(viewRect);
    }

}
