package wig.wallpaperchanger.domain.data;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode.Include;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Wallpaper {
    @Include public final String id;
    public final String publishDate;
    public final String uri;
    public final String title;
    public final String description;
    public final boolean unliked;
}