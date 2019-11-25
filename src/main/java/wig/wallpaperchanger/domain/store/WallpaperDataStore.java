package wig.wallpaperchanger.domain.store;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;

import io.reactivex.Completable;
import io.reactivex.Single;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import wig.wallpaperchanger.domain.data.Wallpaper;

public class WallpaperDataStore {
    private final String FILE_NAME = "database.json";

    private final Gson gson;

    public WallpaperDataStore() {
        this.gson = new Gson();
    }

    public Completable save(List<Wallpaper> wallpapers) {
        return Completable.fromCallable(() -> {
            File file = new File(FILE_NAME);
            
            try(FileWriter writer = new FileWriter(file, false)) {
                final String wallpapersJson = gson.toJson(wallpapers);
                writer.write(wallpapersJson);
            }

            return Completable.complete();
        });
    }

    public Completable update(Wallpaper wallpaper) {
        return load().flatMapCompletable(wallpapers -> Completable.fromCallable(() -> {
            final Set<Wallpaper> wallpaperSet = new HashSet<Wallpaper>(wallpapers);

            if (wallpapers.contains(wallpaper)) {
                wallpaperSet.remove(wallpaper);
            }

            wallpaperSet.add(wallpaper);
            return save(List.copyOf(wallpaperSet)).blockingGet();
        }));
    }

    public Single<List<Wallpaper>> load() {
        return Single.fromCallable(this::readFileContent)
            .onErrorResumeNext(error -> Single.just(List.of()));
    } 

    public Single<Boolean> isEmpty() {
        return Single.fromCallable(() -> {
            final File file = new File(FILE_NAME);

            if (file.exists()) {
                final List<Wallpaper> wallpapers = readFileContent();
                return wallpapers.isEmpty();
            } else {
                return true;
            }
        })
        .onErrorResumeNext(error -> Single.just(true));
    }

    private List<Wallpaper> readFileContent() throws IOException {
        final String wallpapersJson = new String(Files.readAllBytes(Paths.get(FILE_NAME)));
        final Type type = new TypeToken<List<Wallpaper>>() {}.getType();

        return gson.fromJson(wallpapersJson, type);
    }
}