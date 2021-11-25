package org.abego.commons.swing;

import org.abego.commons.lang.exception.MustNotInstantiateException;
import org.junit.jupiter.api.Test;

import javax.swing.ImageIcon;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static org.abego.commons.io.FileUtil.toFile;
import static org.abego.commons.test.JUnit5Util.assertThrowsWithMessage;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ImageIconUtilTest {

    @Test
    void constructor() {
        assertThrows(MustNotInstantiateException.class, ImageIconUtil::new);
    }


    @Test
    void icon_URL() throws MalformedURLException {
        URL url = new URL("file:/org/abego/commons/swing/ignore.png");

        ImageIcon icon = ImageIconUtil.icon(url);

        assertNotNull(icon);
    }

    @Test
    void iconFromResource_OK() {
        ImageIcon icon =
                ImageIconUtil.iconFromResource("ignore.png", getClass());

        assertNotNull(icon);
    }

    @Test
    void iconFromResource_missing() {
        assertThrowsWithMessage(
                IllegalArgumentException.class,
                "ImageIcon not found: foo. Looking in class org.abego.commons.swing.ImageIconUtilTest",
                () -> ImageIconUtil.iconFromResource("foo", getClass()));
    }

    @Test
    void icon_File() throws MalformedURLException {
        File file = toFile(new URL("file:/org/abego/commons/swing/ignore.png"));

        ImageIcon icon = ImageIconUtil.icon(file);

        assertNotNull(icon);
    }

}