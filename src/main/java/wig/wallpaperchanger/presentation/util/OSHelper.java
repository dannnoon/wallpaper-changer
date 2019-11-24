package wig.wallpaperchanger.presentation.util;

import java.util.Locale;

public class OSHelper {

    static public OS getOS() {
        final String osVersion = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);

        if (osVersion.contains("darwin") || osVersion.contains("mac")) {
            return OS.MAC_OS;
        } else if (osVersion.contains("win")) {
            return OS.WINDOWS;
        } else if (osVersion.contains("nux")) {
            return OS.LINUX;
        } else {
            return OS.OTHER;
        }
    }

    public enum OS {
        WINDOWS,
        MAC_OS,
        LINUX,
        OTHER
    }
}