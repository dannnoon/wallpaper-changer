package wig.wallpaperchanger.presentation.util.wallpaperchanger;

class WindowsWallpaperChanger implements WallpaperChanger {
    private static final int SET_WALLPAPER_ACTION = 0x0014;
    private static final int SET_WALLPAPER_UI_PARAM = 0;
    private static final int SET_WALLPAPER_WIN_INIT = 1;

    @Override
    public void changeWallpaper(String imagePath) {
        User32.INSTANCE.SystemParametersInfo(SET_WALLPAPER_ACTION, SET_WALLPAPER_UI_PARAM, imagePath, SET_WALLPAPER_WIN_INIT);
    }
}
