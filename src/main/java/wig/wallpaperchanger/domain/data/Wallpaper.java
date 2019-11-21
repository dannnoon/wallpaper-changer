package wig.wallpaperchanger.domain.data;

import io.reactivex.annotations.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Wallpaper {
    public final String id;
    public final String publishDate;
    public final String uri;
    public final String title;
    public final String description;
    @Nullable public final boolean unliked;
}