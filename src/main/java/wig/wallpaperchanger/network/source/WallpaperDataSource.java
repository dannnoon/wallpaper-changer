package wig.wallpaperchanger.network.source;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.google.gson.Gson;

import io.reactivex.Single;
import wig.wallpaperchanger.network.data.CurrentMonthWallpapersResponse;

public class WallpaperDataSource {
    private final String FULL_URL = "https://www.nationalgeographic.com/photography/photo-of-the-day/_jcr_content/.gallery.%s.json";

    private final HttpClient httpClient;
    private final Gson gson;

    public WallpaperDataSource() {
        httpClient = HttpClient.newBuilder().build();
        gson = new Gson();
    }

    public Single<CurrentMonthWallpapersResponse> queryWallpapers() {
        return Single.fromCallable(() -> makeQueryWallpapersRequest());
    }

    private CurrentMonthWallpapersResponse makeQueryWallpapersRequest() throws IOException, InterruptedException {
        final String url = String.format(FULL_URL, getCurrentDate());

        final HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .build();
            
        final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return gson.fromJson(response.body(), CurrentMonthWallpapersResponse.class);
    }

    private String getCurrentDate() {
        final Date date = new Date();
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        return formatter.format(date);
    }
}
