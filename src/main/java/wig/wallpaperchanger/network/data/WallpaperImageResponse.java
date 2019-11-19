package wig.wallpaperchanger.network.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WallpaperImageResponse {
    final String id;
    final String uri;
    final String title;
    final String caption;
}