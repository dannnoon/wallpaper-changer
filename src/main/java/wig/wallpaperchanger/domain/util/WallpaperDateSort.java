package wig.wallpaperchanger.domain.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import wig.wallpaperchanger.domain.data.Wallpaper;

public class WallpaperDateSort {

    static public int sort(Wallpaper wallpaperA, Wallpaper wallpaperB) {
        final SimpleDateFormat formatter = new SimpleDateFormat("MMMMM d, yyyy", Locale.ENGLISH);
        
        try {
            final Date dateA = formatter.parse(wallpaperA.publishDate);
            final Date dateB = formatter.parse(wallpaperB.publishDate);
            return dateB.compareTo(dateA);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}