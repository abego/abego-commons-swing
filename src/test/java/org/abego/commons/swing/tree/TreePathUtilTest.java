package org.abego.commons.swing.tree;

import org.abego.commons.lang.exception.MustNotInstantiateException;
import org.abego.commons.tree.TreeNodeDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.junit.jupiter.api.Test;

import javax.swing.tree.TreePath;
import java.util.List;

import static java.util.Arrays.asList;
import static org.abego.commons.swing.tree.TreePathUtil.treePathForValuePath;
import static org.abego.commons.tree.TreeNodeDefault.newTreeNodeDefault;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TreePathUtilTest {
    @Test
    void constructor() {
        assertThrows(MustNotInstantiateException.class, TreePathUtil::new);
    }

    @Test
    void treePathForValuePath_OK() {
        TreeNodeDefault<String> c1 = newTreeNodeDefault("c1");
        TreeNodeDefault<String> c2 = newTreeNodeDefault("c2");
        TreeNodeDefault<String> c3 = newTreeNodeDefault("c3");
        TreeNodeDefault<String> c4 = newTreeNodeDefault("c4");
        TreeNodeDefault<String> b1 = newTreeNodeDefault("b1", c1, c2);
        TreeNodeDefault<String> b2 = newTreeNodeDefault("b2", c3, c4);
        TreeNodeDefault<String> a = newTreeNodeDefault("a", b1, b2);

        // find path
        assertTreePathEquals(treePathForValuePath(a, asList("a")), a);
        assertTreePathEquals(treePathForValuePath(a, asList("a", "b2", "c3")), a, b2, c3);
        assertTreePathEquals(treePathForValuePath(a, asList("a", "b1")), a, b1);
        assertTreePathEquals(treePathForValuePath(a, asList("a", "b1")), a, b1);

        // don't find path
        assertNull(treePathForValuePath(a, asList("a", "b1", "x")));
        assertNull(treePathForValuePath(a, asList("a", "x")));
        assertNull(treePathForValuePath(a, asList("x")));
        assertNull(treePathForValuePath(a, asList()));

        // error: null as root, but non-empty value path
        List<String> aPath = asList("a");
        assertThrows(IllegalArgumentException.class,
                () -> treePathForValuePath(null, aPath));
    }

    private void assertTreePathEquals(@Nullable TreePath treePath, Object... items) {
        if (treePath == null) {
            assertEquals(0, items.length);
        } else {
            assertEquals(items.length, treePath.getPathCount());
            assertArrayEquals(items, treePath.getPath());
        }
    }
}