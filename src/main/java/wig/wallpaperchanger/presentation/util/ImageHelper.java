package wig.wallpaperchanger.presentation.util;

import java.awt.*;
import javax.swing.*;
import java.net.URL;

public class ImageHelper {
    public static final String TRAY_ICON_PATH = "/images/tray_icon.png";

    public static Image loadImage(String path) {
        final URL imageUrl = getResourceUrl(path);
        return new ImageIcon(imageUrl).getImage();
    }

    public static URL getResourceUrl(String path) {
        return ImageHelper.class.getResource(path);
    }
}