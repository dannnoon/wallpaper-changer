package wig.wallpaperchanger.presentation.ui;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import wig.wallpaperchanger.domain.data.Wallpaper;
import wig.wallpaperchanger.domain.usecase.ChangeWallpaper;
import wig.wallpaperchanger.domain.usecase.ChangeWallpaperRandomly;
import wig.wallpaperchanger.domain.usecase.LoadNewestWallpapers;

class TrayViewPresenter {
    final private TrayView trayView;
    final private LoadNewestWallpapers loadNewestWallpapers;
    final private ChangeWallpaper changeWallpaper;
    final private ChangeWallpaperRandomly changeWallpaperRandomly;

    private Disposable initializeDisposable;
    private Disposable changeWallpaperRandomlyDisposable;
    private Wallpaper currentWallpaper;

    TrayViewPresenter(TrayView trayView) {
        this.trayView = trayView;
        this.loadNewestWallpapers = new LoadNewestWallpapers();
        this.changeWallpaper = new ChangeWallpaper();
        this.changeWallpaperRandomly = new ChangeWallpaperRandomly();

        initialize();
    }

    void markCurrentAsUnliked() {
        
    }

    void changeToRandomWallpaper() {
        if (changeWallpaperRandomlyDisposable != null) {
            changeWallpaperRandomlyDisposable.dispose();
        }

        trayView.hideRollDiceOption();

        changeWallpaperRandomlyDisposable = changeWallpaperRandomly.invoke(currentWallpaper)
            .subscribeOn(Schedulers.io())
            .doOnTerminate(() -> trayView.showRollDiceOption())
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

    void dispose() {
        if (initializeDisposable != null) {
            initializeDisposable.dispose();;
        }
    }

    private void initialize() {
        if (initializeDisposable != null) {
            initializeDisposable.dispose();
        }

        initializeDisposable = loadNewestWallpapers.invoke()
            .flatMap(wallpaper -> changeWallpaper.invoke(wallpaper).andThen(Single.just(wallpaper)))
            .subscribeOn(Schedulers.io())
            .subscribe(
                (wallpaper) -> {
                    currentWallpaper = wallpaper;
                    trayView.showCurrentWallpaperName(wallpaper.title);
                    trayView.showRollDiceOption();
                },
                (error) -> {
                    error.printStackTrace();
                }
            );
    }
}