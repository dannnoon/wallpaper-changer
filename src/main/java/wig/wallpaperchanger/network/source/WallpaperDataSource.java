package wig.wallpaperchanger.network.source;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;

import io.reactivex.Single;
import wig.wallpaperchanger.network.data.CurrentMonthWallpapersResponse;

public class WallpaperDataSource {
    private HttpClient httpClient;
    private Gson gson;

    public WallpaperDataSource() {
        httpClient = HttpClient.newBuilder().build();
        gson = new Gson();
    }

    public Single<CurrentMonthWallpapersResponse> queryWallpapers() {
        return Single.fromCallable(() -> makeQueryWallpapersRequest());
    }

    private CurrentMonthWallpapersResponse makeQueryWallpapersRequest() throws IOException, InterruptedException {
        final HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://www.nationalgeographic.com/photography/photo-of-the-day/_jcr_content/.gallery.2019-11.json"))
            .build();
            
        final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return gson.fromJson(response.body(), CurrentMonthWallpapersResponse.class);
    }
}
