package org.abego.commons.swing.border;

import org.eclipse.jdt.annotation.Nullable;

import javax.swing.border.AbstractBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.beans.ConstructorProperties;
import java.util.function.Function;

/**
 * A Border inspired by the CSS Box Model, with padding, margin, border color, border width,
 * configurable either individually per-side or for all side.
 */
@SuppressWarnings("serial")
public class BoxModelBorder extends AbstractBorder {
    private final @Nullable Config[] configs = new @Nullable Config[Side
            .values().length];

    public enum Side {
        /**
         * the value to use when no side specific value is defined.
         */
        DEFAULT,
        TOP,
        BOTTOM,
        LEFT,
        RIGHT,
    }

    private static class Config {
        @Nullable Integer borderWidth;
        @Nullable Color borderColor;
        @Nullable Integer padding;
        @Nullable Integer margin;

        Config(@Nullable Integer borderWidth,
               @Nullable Color borderColor,
               @Nullable Integer padding,
               @Nullable Integer margin) {
            this.borderWidth = borderWidth;
            this.borderColor = borderColor;
            this.padding = padding;
            this.margin = margin;
        }

        Config() {
            this(null, null, null, null);
        }
    }

    @ConstructorProperties({"borderWidth", "borderColor", "padding", "margin"})
    private BoxModelBorder(@Nullable Integer borderWidth,
                           @Nullable Color borderColor,
                           @Nullable Integer padding,
                           @Nullable Integer margin) {
        configs[Side.DEFAULT
                .ordinal()] = new Config(borderWidth, borderColor, padding, margin);
    }

    private BoxModelBorder() {
        this(null, Color.BLACK, null, null);
    }

    public static BoxModelBorder newBoxModelBorder() {
        return new BoxModelBorder();
    }

    public static BoxModelBorder newBoxModelBorderWithPadding(int padding) {
        return new BoxModelBorder(null, null, padding, null);
    }

    public static BoxModelBorder newBoxModelBorder(int borderWidth) {
        return new BoxModelBorder(borderWidth, Color.BLACK, null, null);
    }

    public static BoxModelBorder newBoxModelBorder(@Nullable Integer borderWidth,
                                                   @Nullable Color borderColor,
                                                   @Nullable Integer padding,
                                                   @Nullable Integer margin) {
        return new BoxModelBorder(borderWidth, borderColor, padding, margin);
    }

    /**
     * Paints the border for the specified component with the
     * specified position and size.
     *
     * @param c      the component for which this border is being painted
     * @param g      the paint graphics
     * @param x      the x position of the painted border
     * @param y      the y position of the painted border
     * @param width  the width of the painted border
     * @param height the height of the painted border
     */
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        if (!(g instanceof Graphics2D)) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g;

        drawVLine(g2d, Side.LEFT, x + getMargin(Side.LEFT), y, height);
        drawVLine(g2d, Side.RIGHT, x + width - getMargin(Side.RIGHT) - getBorderWidth(Side.RIGHT),
                y,
                height);
        drawHLine(g2d, Side.TOP, x, y + getMargin(Side.TOP), width);
        drawHLine(g2d, Side.BOTTOM, x,
                y + height - getMargin(Side.BOTTOM) - getBorderWidth(Side.BOTTOM), width);
    }

    /**
     * Reinitialize the insets parameter with this Border's current Insets.
     *
     * @param c      the component for which this border insets value applies
     * @param insets the object to be reinitialized
     */
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.set(calcInset(Side.TOP),
                calcInset(Side.LEFT),
                calcInset(Side.BOTTOM),
                calcInset(Side.RIGHT));
        return insets;
    }

    public BoxModelBorder setStyle(Side side,
                                   @Nullable Integer borderWidth,
                                   @Nullable Color borderColor,
                                   @Nullable Integer padding,
                                   @Nullable Integer margin) {
        configs[side.ordinal()] =
                new Config(borderWidth, borderColor, padding, margin);
        return this;
    }

    public BoxModelBorder setBorderWidth(Side side, @Nullable Integer value) {
        getConfig(side).borderWidth = value;
        return this;
    }

    public BoxModelBorder setBorderWidth(@Nullable Integer value) {
        return setBorderWidth(Side.DEFAULT, value);
    }

    public BoxModelBorder setBorderColor(Side side, @Nullable Color value) {
        getConfig(side).borderColor = value;
        return this;
    }

    public BoxModelBorder setBorderColor(@Nullable Color value) {
        return setBorderColor(Side.DEFAULT, value);
    }

    public BoxModelBorder setPadding(Side side, @Nullable Integer value) {
        getConfig(side).padding = value;
        return this;
    }

    public BoxModelBorder setPadding(@Nullable Integer value) {
        return setPadding(Side.DEFAULT, value);
    }

    public BoxModelBorder setMargin(Side side, @Nullable Integer value) {
        getConfig(side).margin = value;
        return this;
    }

    public BoxModelBorder setMargin(@Nullable Integer value) {
        return setMargin(Side.DEFAULT, value);
    }

    public Color getBorderColor(Side side) {
        return getConfigValueOrElse(side, c -> c.borderColor, Color.BLACK);
    }

    public int getBorderWidth(Side side) {
        return getConfigValueOrElse(side, c -> c.borderWidth, 0);
    }

    public int getPadding(Side side) {
        return getConfigValueOrElse(side, c -> c.padding, 0);
    }

    public int getMargin(Side side) {
        return getConfigValueOrElse(side, c -> c.margin, 0);
    }

    private void drawVLine(Graphics2D g2d, Side side, int x, int y, int height) {
        int lineThickness = getBorderWidth(side);
        if (lineThickness > 0) {
            Color oldColor = g2d.getColor();
            g2d.setColor(getBorderColor(side));
            Shape lineShape = new Rectangle2D.Float(
                    x, y + getMargin(Side.TOP),
                    lineThickness, height - getMargin(Side.TOP) - getMargin(Side.BOTTOM));
            g2d.fill(lineShape);
            g2d.setColor(oldColor);
        }
    }

    private void drawHLine(Graphics2D g2d, Side side, int x, int y, int width) {
        int lineThickness = getBorderWidth(side);
        if (lineThickness > 0) {
            Color oldColor = g2d.getColor();
            g2d.setColor(getBorderColor(side));
            Shape lineShape = new Rectangle2D.Float(
                    x + getMargin(Side.LEFT), y,
                    width - getMargin(Side.LEFT) - getMargin(Side.RIGHT), lineThickness);
            g2d.fill(lineShape);
            g2d.setColor(oldColor);
        }
    }

    private Config getConfig(Side side) {
        int index = side.ordinal();
        Config config = configs[index];
        if (config == null) {
            config = new Config();
            configs[index] = config;
        }
        return config;
    }

    private <T> T getConfigValueOrElse(Side side, Function<Config, @Nullable T> value, T defaultValue) {
        Config config = configs[side.ordinal()];
        if (config != null) {
            @Nullable T v = value.apply(config);
            if (v != null) {
                return v;
            }
        }

        // if we haven't found a value when checking for a specific side
        // (not Side.DEFAULT) we can still check if there is a value defined
        // for the "default".
        if (side != Side.DEFAULT) {
            config = configs[Side.DEFAULT.ordinal()];
            if (config != null) {
                @Nullable T v = value.apply(config);
                if (v != null) {
                    return v;
                }
            }
        }

        // no value defined for the side (or the "default" side),
        // so use the provided default value
        return defaultValue;
    }

    private int calcInset(Side side) {
        return getPadding(side) + getBorderWidth(side) + getMargin(side);
    }
}
