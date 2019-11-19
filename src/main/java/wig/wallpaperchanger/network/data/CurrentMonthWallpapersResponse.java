package wig.wallpaperchanger.network.data;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CurrentMonthWallpapersResponse {
    final List<WallpaperItemResponse> items;
}