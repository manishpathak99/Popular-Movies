package udacity.nanodegree.android.manishpathak.in.popularmovies.network.api;

import android.support.annotation.Nullable;
import android.util.Log;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import udacity.nanodegree.android.manishpathak.in.popularmovies.PopularMoviesApp;
import udacity.nanodegree.android.manishpathak.in.popularmovies.constants.AppConstants;
import udacity.nanodegree.android.manishpathak.in.popularmovies.model.VideoModel;

/**
 * The type Videos api.
 *
 * @author Manish Pathak
 * @date 13/4/16
 */
public class VideosApi extends BaseApi implements Callback<VideoModel> {

    @Nullable
    private BaseApiListener otBaseApiListener = null;

    /**
     * Instantiates a new Movies api.
     */
    public VideosApi() {
    }

    /**
     * Fetch movies list.
     *
     * @param apiListener the api listener
     * @param movieId     the movie id
     */
    public void fetchVideosList(BaseApiListener apiListener, long movieId) {
        otBaseApiListener = apiListener;
        Call<VideoModel> call =  PopularMoviesApp.getApiClient().getService().getVideosList(movieId, AppConstants.API_KEY);
        //asynchronous
        call.enqueue(new Callback<VideoModel>() {
            @Override
            public void onResponse(Call<VideoModel> call, Response<VideoModel> response) {
                Log.e("sdfs", "onResponse");
                // response.isSuccess() is true if the response code is 2xx
                if (response.isSuccess()) {
                    VideoModel movieModel = response.body();
                    otBaseApiListener.onRequestCompleted(movieModel);
                } else {
                    int statusCode = response.code();
                    // handle request errors yourself
                    ResponseBody errorBody = response.errorBody();
                    otBaseApiListener.onRequestFailed(errorBody);
                }
            }

            @Override
            public void onFailure(Call<VideoModel> call, Throwable t) {
                Log.e("sdfs", "onFailure");
                otBaseApiListener.onRequestFailed(t);
            }
        });
    }

    /**
     * Clear listener.
     */
    public void clearListener() {
        this.otBaseApiListener = null;
    }

    @Nullable
    @Override
    public BaseApiListener getApiListener() {
        return otBaseApiListener;
    }

    @Override
    public void onResponse(Call<VideoModel> call, Response<VideoModel> response) {
        Log.e("onRESPONSE", response.toString());
    }

    @Override
    public void onFailure(Call<VideoModel> call, Throwable t) {
        Log.e("on Failure", t.toString());
    }
}
