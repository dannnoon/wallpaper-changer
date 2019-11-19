package wig.wallpaperchanger;

import java.awt.AWTException;

import wig.wallpaperchanger.presentation.ui.TrayView;

public class WallpaperChangerRuntime {
    public static void main(String[] args) {
        final TrayView trayView = new TrayView();
        
        try {
            trayView.show();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
