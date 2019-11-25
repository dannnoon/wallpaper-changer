package wig.wallpaperchanger.presentation.ui;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import wig.wallpaperchanger.domain.data.Wallpaper;
import wig.wallpaperchanger.domain.usecase.ChangeWallpaper;
import wig.wallpaperchanger.domain.usecase.ChangeWallpaperRandomly;
import wig.wallpaperchanger.domain.usecase.LoadNewestWallpapers;
import wig.wallpaperchanger.domain.usecase.UnlikeCurrentWallpaper;

class TrayViewPresenter {
    final private TrayView trayView;
    final private LoadNewestWallpapers loadNewestWallpapers;
    final private ChangeWallpaper changeWallpaper;
    final private ChangeWallpaperRandomly changeWallpaperRandomly;
    final private UnlikeCurrentWallpaper unlikeCurrentWallpaper;

    private Disposable initializeDisposable;
    private Disposable changeWallpaperRandomlyDisposable;
    private Disposable unlikeCurrentWallpaperDisposable;

    private Wallpaper currentWallpaper;

    TrayViewPresenter(TrayView trayView) {
        this.trayView = trayView;
        this.loadNewestWallpapers = new LoadNewestWallpapers();
        this.changeWallpaper = new ChangeWallpaper();
        this.changeWallpaperRandomly = new ChangeWallpaperRandomly();
        this.unlikeCurrentWallpaper = new UnlikeCurrentWallpaper();

        initialize();
    }

    void markCurrentAsUnliked() {
        if (unlikeCurrentWallpaperDisposable != null) {
            unlikeCurrentWallpaperDisposable.dispose();
        }

        trayView.hideUnlikeOption();

        unlikeCurrentWallpaperDisposable = unlikeCurrentWallpaper.invoke(currentWallpaper)
            .subscribeOn(Schedulers.io())
            .doOnTerminate(() -> trayView.showUnlikeOption())
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
        if (changeWallpaperRandomlyDisposable != null) {
            changeWallpaperRandomlyDisposable.dispose();
        }
        if (unlikeCurrentWallpaperDisposable != null) {
            unlikeCurrentWallpaperDisposable.dispose();
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
                    trayView.showUnlikeOption();
                },
                (error) -> {
                    error.printStackTrace();
                }
            );
    }
}