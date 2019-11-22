package wig.wallpaperchanger.domain.data;

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
    public final boolean unliked;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Wallpaper) {
            if (((Wallpaper)obj).id == this.id) {
                return true;
            }
            return false;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return id.hashCode() * 31 + title.hashCode();
    }
}