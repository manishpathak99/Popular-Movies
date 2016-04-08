package udacity.nanodegree.android.manishpathak.in.popularmovies.network.api;

import android.support.annotation.Nullable;
import android.util.Log;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import udacity.nanodegree.android.manishpathak.in.popularmovies.PopularMoviesApp;
import udacity.nanodegree.android.manishpathak.in.popularmovies.constants.AppConstants;
import udacity.nanodegree.android.manishpathak.in.popularmovies.model.MovieModel;

/**
 * Created by manishpathak on 3/8/16.
 */
public class MoviesApi extends BaseApi implements Callback<MovieModel> {

    private BaseApiListener baseApiListener = null;
    /**
     * Instantiates a new Movies api.
     */
    public MoviesApi() {
    }

    @Nullable
    @Override
    protected BaseApiListener getApiListener() {
        return null;
    }

    /**
     * Fetch movies list.
     *
     * @param apiListener the api listener
     * @param sortOrder   the sort order
     * @param pageCount   the page count
     */
    public void fetchMoviesList(BaseApiListener apiListener, String sortOrder, int pageCount) {
        baseApiListener = apiListener;
        Call<MovieModel> call =  PopularMoviesApp.getApiClient().getService()
                .getMovieList(sortOrder, AppConstants.API_KEY, pageCount, false);
        //asynchronous
        call.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                Log.e("sdfs", "onResponse");
                // response.isSuccess() is true if the response code is 2xx
                if (response.isSuccess()) {
                    MovieModel movieModel = response.body();
                    baseApiListener.onRequestCompleted(movieModel);
                } else {
                    int statusCode = response.code();
                    // handle request errors yourself
                    ResponseBody errorBody = response.errorBody();
                    baseApiListener.onRequestFailed(errorBody);
                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                Log.e("sdfs", "onFailure");
                baseApiListener.onRequestFailed(t);
            }
        });
    }

    /**
     * Clear listener.
     */
    public void clearListener() {
        this.baseApiListener = null;
    }

    @Override
    public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
        Log.e("onRESPONSE", response.toString());
    }

    @Override
    public void onFailure(Call<MovieModel> call, Throwable t) {
        Log.e("on Failure", t.toString());
    }
}
