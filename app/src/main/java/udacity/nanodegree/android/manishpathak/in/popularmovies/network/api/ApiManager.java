package udacity.nanodegree.android.manishpathak.in.popularmovies.network.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.ResponseBody;
import udacity.nanodegree.android.manishpathak.in.popularmovies.model.BaseModel;
import udacity.nanodegree.android.manishpathak.in.popularmovies.model.MovieModel;
import udacity.nanodegree.android.manishpathak.in.popularmovies.model.ReviewModel;
import udacity.nanodegree.android.manishpathak.in.popularmovies.model.VideoModel;

/**
 * This class is used to manage all web service api
 * Created by manishpathak on 3/8/16.
 */
public class ApiManager {

    private static ApiManager apiManager;

    @Nullable
    private MoviesApi mMoviesApi = null;
    @Nullable
    private ReviewsApi mReviewsApi = null;
    @Nullable
    private VideosApi mVideosApi = null;

    @Nullable
    private ProgressListener<MovieModel> moviesFetchListener;
    @Nullable
    private ProgressListener<ReviewModel> reviewFetchListener;
    @Nullable
    private ProgressListener<VideoModel> videoFetchListener;

    private boolean isMoviesAPILoading = false;
    private boolean isReviewsAPILoading = false;
    private boolean isVideosAPILoading = false;

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
    private MoviesApi getmMoviesApi() {
        if (mMoviesApi != null) {
            mMoviesApi.clearListener();
        }
        mMoviesApi = new MoviesApi();
        return mMoviesApi;
    }

    @NonNull
    private ReviewsApi getReviewsApi() {
        if (mReviewsApi != null) {
            mReviewsApi.clearListener();
        }
        mReviewsApi = new ReviewsApi();
        return mReviewsApi;
    }

    @NonNull
    private VideosApi getVideosApi() {
        if (mVideosApi != null) {
            mVideosApi.clearListener();
        }
        mVideosApi = new VideosApi();
        return mVideosApi;
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

        getmMoviesApi().fetchMoviesList(new BaseApi.BaseApiListener() {
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
     * Fetch reviews list.
     *
     * @param listener  the listener
     * @param movieId   the movie id
     * @param pageCount the page count
     */
    public void fetchReviewsList(ProgressListener<ReviewModel> listener, long movieId, int pageCount) {

        reviewFetchListener = listener;
        if (reviewFetchListener != null) {
            reviewFetchListener.inProgress();
        }
        if (isReviewsAPILoading) {
            return;
        }
        isReviewsAPILoading = true;

        getReviewsApi().fetchReviewsList(new BaseApi.BaseApiListener() {
            @Override
            public void onRequestCompleted(BaseModel responseModel) {
                isReviewsAPILoading = false;
                // return data
                if (reviewFetchListener != null) {
                    ReviewModel responseBean = (ReviewModel) responseModel;
                    reviewFetchListener.completed(responseBean);
                    reviewFetchListener = null;
                }
            }

            @Override
            public void onRequestFailed(ResponseBody errorBody) {
                // return error
                isReviewsAPILoading = false;
                try {
                if (reviewFetchListener != null) {
                    reviewFetchListener.failed(errorBody.string());
                    reviewFetchListener = null;
                }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestFailed(Throwable throwable) {
                // return error
                isReviewsAPILoading = false;
                if (reviewFetchListener != null) {
                    reviewFetchListener.failed(throwable.getMessage());
                    reviewFetchListener = null;
                }
            }
        }, movieId, pageCount);
    }

    /**
     * Fetch videos list.
     *
     * @param listener the listener
     * @param movieId  the movie id
     */
    public void fetchVideosList(ProgressListener<VideoModel> listener, long movieId) {

        videoFetchListener = listener;
        if (videoFetchListener != null) {
            videoFetchListener.inProgress();
        }
        if (isVideosAPILoading) {
            return;
        }
        isVideosAPILoading = true;

        getVideosApi().fetchVideosList(new BaseApi.BaseApiListener() {
            @Override
            public void onRequestCompleted(BaseModel responseModel) {
                isVideosAPILoading = false;
                // return data
                if (videoFetchListener != null) {
                    VideoModel responseBean = (VideoModel)responseModel;
                    videoFetchListener.completed(responseBean);
                    videoFetchListener = null;
                }
            }

            @Override
            public void onRequestFailed(ResponseBody errorBody) {
                isVideosAPILoading = false;
                // return error
                try {
                if (videoFetchListener != null) {

                        videoFetchListener.failed(errorBody.string());

                    videoFetchListener = null;
                }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestFailed(Throwable throwable) {
                isVideosAPILoading = false;
                // return error
                if (videoFetchListener != null) {
                    videoFetchListener.failed(throwable.getMessage());
                    videoFetchListener = null;
                }
            }


        }, movieId);
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
