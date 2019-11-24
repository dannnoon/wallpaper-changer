package wig.wallpaperchanger.presentation.util.wallpaperchanger;

import wig.wallpaperchanger.presentation.util.OSHelper;
import wig.wallpaperchanger.presentation.util.OSHelper.OS;

public class WallpaperChangerFactory {

    static public WallpaperChanger create() throws Exception {
        final OS os = OSHelper.getOS();

        switch(os) {
            case WINDOWS:
                return new WindowsWallpaperChanger();
            
            default:
                throw new Exception("This OS is not supported.");
        }
    }
}