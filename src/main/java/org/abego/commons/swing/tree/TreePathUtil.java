package org.abego.commons.swing.tree;

import org.abego.commons.lang.IterableUtil;
import org.abego.commons.lang.exception.MustNotInstantiateException;
import org.abego.commons.seq.Seq;
import org.abego.commons.tree.TreeNode;
import org.eclipse.jdt.annotation.Nullable;

import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class TreePathUtil {

    TreePathUtil() {
        throw new MustNotInstantiateException();
    }
    
    @Nullable
    public static <T> TreePath treePathForValuePath(
            @Nullable TreeNode<T> root, Iterable<T> valuePath) {
        if (root == null) {
            if (!IterableUtil.isEmpty(valuePath)) {
                throw new IllegalArgumentException("valuePath defined, but root is null");
            }
            return null;
        }

        @Nullable TreeNode<T> p = null;

        List<TreeNode<T>> path = new ArrayList<>();
        for (T value : valuePath) {
            // Handle the first value (to match with root value) differently
            if (p == null) {
                p = Objects.equals(value, root.getValue()) ? root : null;
            } else {
                p = findChildWithValue(p, value);
            }
            if (p == null) {
                return null;
            }
            path.add(p);
        }

        return path.isEmpty() ? null : new TreePath(path.toArray());
    }

    @Nullable
    private static <T> TreeNode<T> findChildWithValue(
            TreeNode<T> parent, T value) {
        Seq<TreeNode<T>> result = parent.getChildren().filter(
                c -> Objects.equals(c.getValue(), value));
        return result.size() == 1 ? result.first() : null;
    }
}
