package wig.wallpaperchanger.domain.usecase;

import io.reactivex.Single;
import wig.wallpaperchanger.domain.data.Wallpaper;
import wig.wallpaperchanger.domain.repository.WallpaperRepository;
import wig.wallpaperchanger.domain.store.WallpaperDataStore;

public class UnlikeCurrentWallpaper {

    private final WallpaperDataStore dataStore;
    private final WallpaperRepository repository;
    private final ChangeWallpaper changeWallpaper;

    public UnlikeCurrentWallpaper() {
        this.dataStore = new WallpaperDataStore();
        this.repository = new WallpaperRepository();
        this.changeWallpaper = new ChangeWallpaper();
    } 

    public Single<Wallpaper> invoke(Wallpaper currentWallpaper) {
        final Wallpaper unlikedWallpaper = new Wallpaper(
                 currentWallpaper.id, 
                currentWallpaper.publishDate, 
                currentWallpaper.uri, 
                currentWallpaper.title, 
                currentWallpaper.description, 
                true
            );
        return dataStore.update(unlikedWallpaper)
            .andThen(repository.getNewestWallpaper())
            .flatMap(this::changeWallpaperToNewOne);
    }

    private Single<Wallpaper> changeWallpaperToNewOne(Wallpaper newWallpaper) {
        return changeWallpaper.invoke(newWallpaper).andThen(Single.just(newWallpaper));
    }
}