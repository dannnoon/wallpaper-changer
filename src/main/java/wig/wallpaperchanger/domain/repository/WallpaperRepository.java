package wig.wallpaperchanger.domain.repository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import wig.wallpaperchanger.domain.data.Wallpaper;
import wig.wallpaperchanger.domain.store.WallpaperDataStore;
import wig.wallpaperchanger.domain.store.WallpaperImageStore;
import wig.wallpaperchanger.domain.util.WallpaperDateSort;
import wig.wallpaperchanger.network.source.WallpaperDataSource;

public class WallpaperRepository {
    private final WallpaperDataSource dataSource;
    private final WallpaperDataStore dataStore;
    private final WallpaperImageStore imageStore;

    public WallpaperRepository() {
        this.dataSource = new WallpaperDataSource();
        this.dataStore = new WallpaperDataStore();
        this.imageStore = new WallpaperImageStore();
    }

    public Single<Wallpaper> getNewestWallpaper() {
        return Single.zip(dataSource.queryWallpapers(), dataStore.load(), (freshData, storedData) -> {
            final List<Wallpaper> sortedWallpapers = new ArrayList<>(storedData);
            freshData.forEach(newWallpaper -> {
                if (!sortedWallpapers.contains(newWallpaper)) {
                    sortedWallpapers.add(newWallpaper);
                }
            });

            sortedWallpapers.sort(WallpaperDateSort::sort);

            return sortedWallpapers;
        })
        .flatMap(this::storeWallpapersData)
        .flatMap(this::storeWallpapersImage)
        .map(this::findNewestWallpaper);
    }

    private Single<List<Wallpaper>> storeWallpapersData(List<Wallpaper> wallpapers) {
        return dataStore.save(wallpapers).andThen(Single.just(wallpapers));
    }

    private Single<List<Wallpaper>> storeWallpapersImage(List<Wallpaper> wallpapers) {
        return Observable.fromIterable(wallpapers)
            .concatMapCompletable(wallpaper -> imageStore.save(wallpaper))
            .andThen(Single.just(wallpapers));
    }

    private Wallpaper findNewestWallpaper(List<Wallpaper> wallpapers) {
        return wallpapers.stream()
                .filter(item -> !item.isUnliked())
                .findFirst()
                .get();
    }
}