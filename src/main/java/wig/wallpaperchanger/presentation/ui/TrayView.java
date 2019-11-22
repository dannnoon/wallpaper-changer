package wig.wallpaperchanger.presentation.ui;

import wig.wallpaperchanger.presentation.util.ImageHelper;

import java.awt.*;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

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
            presenter.dispose();
            systemTray.remove(trayIcon);
            System.exit(0);
        });

        popupMenu = new PopupMenu();
        popupMenu.add(unlikeMenuItem);
        popupMenu.add(rollDiceMenuItem);
        popupMenu.add(exitMenuItem);
    }

    @Override
    public void showCurrentWallpaperName(String name) {
        nameMenuItem = new MenuItem(name);
        nameMenuItem.setEnabled(false);

        SwingUtilities.invokeLater(() -> popupMenu.insert(nameMenuItem, 0));
    }

    @Override
    public void showRollDiceOption() {
        
    }

    @Override
    public void hideRollDiceOption() {
        
    }
}