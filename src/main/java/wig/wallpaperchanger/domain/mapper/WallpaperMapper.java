package wig.wallpaperchanger.domain.mapper;

import wig.wallpaperchanger.domain.data.Wallpaper;
import wig.wallpaperchanger.network.data.WallpaperItemResponse;

public class WallpaperMapper {
    public Wallpaper apply(WallpaperItemResponse item) {
        return new Wallpaper(
            item.getImage().getId(),
            item.getPublishDate(),
            item.getImage().getUri(),
            item.getImage().getTitle(),
            item.getImage().getCaption(),
            false
        );
    }
}