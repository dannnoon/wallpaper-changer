package wig.wallpaperchanger.presentation.ui;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import wig.wallpaperchanger.domain.data.Wallpaper;
import wig.wallpaperchanger.domain.usecase.ChangeWallpaper;
import wig.wallpaperchanger.domain.usecase.LoadNewestWallpapers;

class TrayViewPresenter {
    final private TrayView trayView;
    final private LoadNewestWallpapers loadNewestWallpapers;
    final private ChangeWallpaper changeWallpaper;

    private Disposable disposable;
    private Wallpaper currentWallpaper;

    TrayViewPresenter(TrayView trayView) {
        this.trayView = trayView;
        this.loadNewestWallpapers = new LoadNewestWallpapers();
        this.changeWallpaper = new ChangeWallpaper();

        initialize();
    }

    void markCurrentAsUnliked() {
        
    }

    void changeToRandomWallpaper() {

    }

    void dispose() {
        if (disposable != null) {
            disposable.dispose();;
        }
    }

    private void initialize() {
        disposable = loadNewestWallpapers.invoke()
            .flatMap(wallpaper -> changeWallpaper.invoke(wallpaper).andThen(Single.just(wallpaper)))
            .subscribeOn(Schedulers.io())
            .subscribe(
                (wallpaper) -> {
                    currentWallpaper = wallpaper;
                    trayView.showCurrentWallpaperName(wallpaper.title);
                },
                (error) -> {
                    error.printStackTrace();
                }
            );
    }
}