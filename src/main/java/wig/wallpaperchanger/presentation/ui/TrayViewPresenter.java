package wig.wallpaperchanger.presentation.ui;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import wig.wallpaperchanger.domain.data.Wallpaper;
import wig.wallpaperchanger.domain.repository.WallpaperRepository;

class TrayViewPresenter {
    final private TrayView trayView;
    final private WallpaperRepository repository;

    private Disposable disposable;
    private Wallpaper currentWallpaper;

    TrayViewPresenter(TrayView trayView) {
        this.trayView = trayView;
        this.repository = new WallpaperRepository();

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
        disposable = repository.getNewestWallpaper()
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