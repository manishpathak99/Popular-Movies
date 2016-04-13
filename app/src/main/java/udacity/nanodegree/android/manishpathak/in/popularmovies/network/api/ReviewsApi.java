package udacity.nanodegree.android.manishpathak.in.popularmovies.network.api;

import android.support.annotation.Nullable;
import android.util.Log;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import udacity.nanodegree.android.manishpathak.in.popularmovies.PopularMoviesApp;
import udacity.nanodegree.android.manishpathak.in.popularmovies.constants.AppConstants;
import udacity.nanodegree.android.manishpathak.in.popularmovies.model.ReviewModel;

/**
 * The type Reviews api.
 *
 * @author Manish Pathak
 * @date 13/4/16
 */
public class ReviewsApi extends BaseApi implements Callback<ReviewModel> {

    @Nullable
    private BaseApiListener otBaseApiListener = null;

    /**
     * Instantiates a new Movies api.
     */
    public ReviewsApi() {
    }

    /**
     * Fetch movies list.
     *  @param apiListener the api listener
     * @param movieId     the movie id
     * @param pageCount   the page count
     */
    public void fetchReviewsList(BaseApiListener apiListener, long movieId, int pageCount) {
        otBaseApiListener = apiListener;
        Call<ReviewModel> call =  PopularMoviesApp.getApiClient().getService().
                getReviewsList(movieId, AppConstants.API_KEY, pageCount);
        //asynchronous
        call.enqueue(new Callback<ReviewModel>() {
            @Override
            public void onResponse(Call<ReviewModel> call, Response<ReviewModel> response) {
                Log.e("sdfs", "onResponse");
                // response.isSuccess() is true if the response code is 2xx
                if (response.isSuccess()) {
                    ReviewModel movieModel = response.body();
                    otBaseApiListener.onRequestCompleted(movieModel);
                } else {
                    int statusCode = response.code();
                    // handle request errors yourself
                    ResponseBody errorBody = response.errorBody();
                    otBaseApiListener.onRequestFailed(errorBody);
                }
            }

            @Override
            public void onFailure(Call<ReviewModel> call, Throwable t) {
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
    public void onResponse(Call<ReviewModel> call, Response<ReviewModel> response) {
        Log.e("onRESPONSE", response.toString());
    }

    @Override
    public void onFailure(Call<ReviewModel> call, Throwable t) {
        Log.e("on Failure", t.toString());
    }
}
