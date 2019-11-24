package wig.wallpaperchanger.presentation.util.wallpaperchanger;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.win32.W32APIOptions;

public interface User32 extends Library {
    User32 INSTANCE = Native.loadLibrary("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);

    boolean SystemParametersInfo(int uiAction, int uiParam, String pvParam, int fWinIni);
}
