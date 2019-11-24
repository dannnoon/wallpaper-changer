package wig.wallpaperchanger.domain.usecase;

import io.reactivex.Completable;
import wig.wallpaperchanger.domain.data.Wallpaper;
import wig.wallpaperchanger.domain.store.WallpaperImageStore;
import wig.wallpaperchanger.presentation.util.wallpaperchanger.WallpaperChanger;
import wig.wallpaperchanger.presentation.util.wallpaperchanger.WallpaperChangerFactory;

public class ChangeWallpaper {

    private WallpaperImageStore imageStore;

    public ChangeWallpaper() {
        this.imageStore = new WallpaperImageStore();
    }

    public Completable invoke(Wallpaper wallpaper) {
        return Completable.fromCallable(() -> {
            final String path = imageStore.absoluteImagePath(wallpaper);
            final WallpaperChanger changer = WallpaperChangerFactory.create();

            changer.changeWallpaper(path);

            return Completable.complete();
        });
    }
}