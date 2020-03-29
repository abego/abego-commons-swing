package org.abego.commons.swing;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;

import org.abego.commons.lang.exception.MustNotInstantiateException;
import org.eclipse.jdt.annotation.Nullable;

public final class JFrameUtil {

    /**
     * @param title    when null the frame will have not title bar. As this also
     *                 removes the "close window" button in most platforms you need
     *                 to make sure there is an alternative to close the frame.
     * @param component the component to display.
     * @param position top left position of the frame. when null the frame is "centered"
     * @param size     the size of the frame, when null the frame is "packed"
     */
	public static JFrame showInFrame(
            @Nullable String title, @Nullable Component component, @Nullable Point position, @Nullable Dimension size) {
        JFrame frame = new JFrame();
        SwingUtilitiesUtil.runInEDT(runnableForShowFrame(frame, title, component, position, size));
        return frame;
    }

    public static JFrame showInFrame(@Nullable Component component, @Nullable Point position, @Nullable Dimension size) {
    	return showInFrame(null, component, position, size);
    }

    public static JFrame showInFrame(@Nullable Component component) {
    	return showInFrame(null, component, null, null);
    }

    private static Runnable runnableForShowFrame(
            JFrame frame,
            @Nullable String title,
            @Nullable Component component,
            @Nullable Point position,
            @Nullable Dimension size) {

        return () -> {
            if (title == null) {
                frame.setUndecorated(true);
            } else {
                frame.setTitle(title);
            }

            if (component != null) {
                frame.getContentPane().add(component);
            }

            if (size != null) {
                frame.setSize(size);
            } else {
                frame.pack();
            }

            if (position != null) {
                frame.setLocation(position);
            } else {
                WindowUtil.centerOnScreen(frame);
            }

            frame.setVisible(true);
        };
    }

    JFrameUtil() {
        throw new MustNotInstantiateException();
    }


}
