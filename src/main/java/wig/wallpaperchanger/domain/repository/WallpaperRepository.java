package wig.wallpaperchanger.domain.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.Single;
import wig.wallpaperchanger.domain.data.Wallpaper;
import wig.wallpaperchanger.domain.store.WallpaperDataStore;
import wig.wallpaperchanger.domain.store.WallpaperImageStore;
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
            final Set<Wallpaper> wallpapersSet = new HashSet<>(freshData);
            wallpapersSet.addAll(storedData);

            final SimpleDateFormat formatter = new SimpleDateFormat("MMMMM d, yyyy", Locale.ENGLISH);
            final List<Wallpaper> sortedWallpapers = new ArrayList<>(wallpapersSet);
            sortedWallpapers.sort((a, b) -> {
                try {
                    final Date dateA = formatter.parse(a.publishDate);
                    final Date dateB = formatter.parse(b.publishDate);
                    return dateB.compareTo(dateA);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return 0;
                }
            });

            return sortedWallpapers;
        })
        .flatMap(wallpapers -> dataStore.save(wallpapers).andThen(Single.just(wallpapers)))
        .flatMap(wallpapers -> Observable.fromIterable(wallpapers).concatMapCompletable(wallpaper -> imageStore.save(wallpaper)).andThen(Single.just(wallpapers)))
        .map(wallpapers -> {
            return wallpapers.stream()
                .filter(item -> !item.isUnliked())
                .findFirst()
                .get();
        });
    }
}