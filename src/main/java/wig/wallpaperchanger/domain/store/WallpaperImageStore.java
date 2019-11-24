package wig.wallpaperchanger.domain.store;

import java.io.InputStream;
import java.net.URL;
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
            final Path image = getImagePath(wallpaper);

            if (image.toFile().exists()) {
                return Completable.complete();
            }

            Files.createDirectories(image.getParent());

            try(InputStream in = new URL(wallpaper.uri).openStream()) {
                Files.copy(in, image, StandardCopyOption.REPLACE_EXISTING);
            }

            return Completable.complete();
        });
    }

    public String imagePath(Wallpaper wallpaper) {
        return getImagePath(wallpaper).toString();
    }

    public String absoluteImagePath(Wallpaper wallpaper) {
        return getImagePath(wallpaper).toAbsolutePath().toString();
    }

    private Path getImagePath(Wallpaper wallpaper) {
        return Paths.get(WALLPAPER_DIRECTORY, wallpaper.id + IMAGE_EXTENSION);
    }
}