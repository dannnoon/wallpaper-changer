package wig.wallpaperchanger.domain.store;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import io.reactivex.Completable;
import wig.wallpaperchanger.domain.data.Wallpaper;

public class WallpaperImageStore {
    private final String WALLPAPER_DIRECTORY = "images/";
    private final String IMAGE_EXTENSION = ".jpg";

    public Completable save(Wallpaper wallpaper) {
        return Completable.fromCallable(() -> {
            try(InputStream in = new URL(wallpaper.uri).openStream()) {
                final Path path = getImagePath(wallpaper);
                System.console().printf(path.toAbsolutePath().toString());
                Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);
            }

            return Completable.complete();
        });
    }

    public String imagePath(Wallpaper wallpaper) {
        return getImagePath(wallpaper).toString();
    }

    private Path getImagePath(Wallpaper wallpaper) {
        return Paths.get(WALLPAPER_DIRECTORY, wallpaper.id + IMAGE_EXTENSION);
    }
}