package wig.wallpaperchanger.network.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WallpaperItemResponse {
    final WallpaperImageResponse image;
    final String publishDate;
}