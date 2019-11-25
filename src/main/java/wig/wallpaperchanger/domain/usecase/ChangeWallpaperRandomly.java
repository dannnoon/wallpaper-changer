package wig.wallpaperchanger.domain.usecase;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import io.reactivex.Single;
import wig.wallpaperchanger.domain.data.Wallpaper;
import wig.wallpaperchanger.domain.store.WallpaperDataStore;

public class ChangeWallpaperRandomly {

    private final WallpaperDataStore store;
    private final ChangeWallpaper changeWallpaper;
    private final Random random;

    public ChangeWallpaperRandomly() {
        this.store = new WallpaperDataStore();
        this.changeWallpaper = new ChangeWallpaper();
        this.random = new Random();
    }

    public Single<Wallpaper> invoke(Wallpaper currentWallpaper) {
        return store.load()
            .flatMap(wallpapers -> getRandomWallpaper(wallpapers, currentWallpaper))
            .flatMap(this::changeWallpaperToDrawn);
    }

    private Single<Wallpaper> getRandomWallpaper(List<Wallpaper> wallpapers, Wallpaper current) {
        final List<Wallpaper> whiteList = wallpapers.stream()
            .filter(wallpaper -> !wallpaper.unliked && !wallpaper.equals(current))
            .collect(Collectors.toList());
        final Wallpaper randomWallpaper = whiteList.get(random.nextInt(whiteList.size()));
        return Single.just(randomWallpaper);
    }

    private Single<Wallpaper> changeWallpaperToDrawn(Wallpaper wallpaper) {
        return changeWallpaper.invoke(wallpaper).andThen(Single.just(wallpaper));
    }
}