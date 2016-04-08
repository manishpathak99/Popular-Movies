package udacity.nanodegree.android.manishpathak.in.popularmovies.network.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.ResponseBody;
import udacity.nanodegree.android.manishpathak.in.popularmovies.model.BaseModel;
import udacity.nanodegree.android.manishpathak.in.popularmovies.model.MovieModel;

/**
 * This class is used to manage all web service api
 * Created by manishpathak on 3/8/16.
 */
public class ApiManager {

    private static ApiManager apiManager;
    private MoviesApi moviesApi;

    @Nullable
    private ProgressListener<MovieModel> moviesFetchListener;

    private boolean isMoviesAPILoading = false;

    /**
     * Gets single instance.
     *
     * @return the instance
     */
    public static ApiManager getInstance() {
        if (apiManager == null) {
            apiManager = new ApiManager();
        }
        return apiManager;
    }

    @NonNull
    private MoviesApi getMoviesApi() {
        if (moviesApi != null) {
            moviesApi.clearListener();
        }
        moviesApi = new MoviesApi();
        return moviesApi;
    }

    /**
     * Fetch movies list.
     *
     * @param listener  the listener
     * @param sortOrder the sort order
     * @param pageCount the page count
     */
    public void fetchMoviesList(ProgressListener<MovieModel> listener, String sortOrder, int pageCount) {

        moviesFetchListener = listener;
        if (moviesFetchListener != null) {
            moviesFetchListener.inProgress();
        }
        if (isMoviesAPILoading) {
            return;
        }
        isMoviesAPILoading = true;

        getMoviesApi().fetchMoviesList(new BaseApi.BaseApiListener() {
            @Override
            public void onRequestCompleted(BaseModel responseModel) {
                isMoviesAPILoading = false;
                // return data
                if (moviesFetchListener != null) {
                    MovieModel responseBean = (MovieModel) responseModel;
                    moviesFetchListener.completed(responseBean);
                    moviesFetchListener = null;
                }
            }

            @Override
            public void onRequestFailed(@NonNull ResponseBody errorBody) {
                isMoviesAPILoading = false;
                // return error
                try {

                    if (moviesFetchListener != null) {
                        moviesFetchListener.failed(errorBody.string());
                        moviesFetchListener = null;
                    }
                } catch (IOException exception) {
                    moviesFetchListener.failed(exception.getMessage());
                }

            }

            @Override
            public void onRequestFailed(Throwable throwable) {
                isMoviesAPILoading = false;
                // return error
                    if (moviesFetchListener != null) {
                        moviesFetchListener.failed(throwable.getMessage());
                        moviesFetchListener = null;
                    }
            }

        }, sortOrder, pageCount);
    }

    /**
     * The interface Progress listener.
     *
     * @param <T> the type parameter
     */
    public interface ProgressListener<T> {

        /**
         * In progress.
         */
        void inProgress();

        /**
         * Failed.
         *
         * @param message the message
         */
        void failed(String message);

        /**
         * Completed.
         *
         * @param object the object
         */
        void completed(T object);

    }
}
