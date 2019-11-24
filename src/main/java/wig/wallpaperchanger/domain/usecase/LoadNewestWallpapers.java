package wig.wallpaperchanger.domain.usecase;

import io.reactivex.Single;
import wig.wallpaperchanger.domain.data.Wallpaper;
import wig.wallpaperchanger.domain.repository.WallpaperRepository;

public class LoadNewestWallpapers {

    private final WallpaperRepository repository;

    public LoadNewestWallpapers() {
        this.repository = new WallpaperRepository();
    }

    public Single<Wallpaper> invoke() {
        return repository.getNewestWallpaper();
    }
}