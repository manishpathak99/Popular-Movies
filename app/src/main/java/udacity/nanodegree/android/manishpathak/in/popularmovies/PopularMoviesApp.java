package udacity.nanodegree.android.manishpathak.in.popularmovies;

import android.app.Application;

import udacity.nanodegree.android.manishpathak.in.popularmovies.network.client.ApiClient;

/**
 * Created by manishpathak on 3/8/16.
 */
public class PopularMoviesApp extends Application {
    private static ApiClient apiClient;
    private static PopularMoviesApp popularMoviesApp;

    /**
     * Gets Api client.
     *
     * @return the api client
     */
    public synchronized static ApiClient getApiClient() {
        return apiClient;
    }

    /**
     * Gets PopularMoviesApp app.
     *
     * @return the PopularMoviesApp app
     */
    public synchronized static PopularMoviesApp getInstance() {
        return popularMoviesApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        apiClient = new ApiClient();
        popularMoviesApp = this;
    }
}
