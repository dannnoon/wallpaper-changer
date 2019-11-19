package wig.wallpaperchanger.presentation.ui;

import wig.wallpaperchanger.network.source.WallpaperDataSource;
import wig.wallpaperchanger.presentation.util.ImageHelper;

import java.awt.*;

import io.reactivex.schedulers.Schedulers;

public class TrayView implements TrayViewInterface {
    private TrayViewPresenter presenter;
    private SystemTray systemTray;
    private TrayIcon trayIcon;

    private PopupMenu popupMenu;
    private MenuItem nameMenuItem;
    private MenuItem unlikeMenuItem;
    private MenuItem rollDiceMenuItem;
    private MenuItem exitMenuItem;

    public TrayView() {
        this.presenter = new TrayViewPresenter(this);
        systemTray = SystemTray.getSystemTray();
        initialize();
    }

    public void show() throws AWTException {
        this.trayIcon = new TrayIcon(ImageHelper.loadImage(ImageHelper.TRAY_ICON_PATH), "Wallpaper Changer", popupMenu);
        trayIcon.setImageAutoSize(true);
        systemTray.add(trayIcon);
    }

    private void initialize() {
        initializeMenuItems();
    }

    private void initializeMenuItems() {
        nameMenuItem = new MenuItem();
        nameMenuItem.setEnabled(false);

        unlikeMenuItem = new MenuItem("Nie lubię");
        unlikeMenuItem.setEnabled(false);
        unlikeMenuItem.addActionListener(action -> {
            presenter.markCurrentAsUnliked();
        });

        rollDiceMenuItem = new MenuItem("Losowa");
        rollDiceMenuItem.setEnabled(false);
        rollDiceMenuItem.addActionListener(action -> {
            presenter.changeToRandomWallpaper();
        });

        exitMenuItem = new MenuItem("Wyjście");
        exitMenuItem.addActionListener(action -> {
            systemTray.remove(trayIcon);
            System.exit(0);
        });

        popupMenu = new PopupMenu();
        popupMenu.add(nameMenuItem);
        popupMenu.add(unlikeMenuItem);
        popupMenu.add(rollDiceMenuItem);
        popupMenu.add(exitMenuItem);
    }

    @Override
    public void showCurrentWallpaperName(String name) {
        
    }

    @Override
    public void showRollDiceOption() {
        
    }

    @Override
    public void hideRollDiceOption() {
        
    }
}