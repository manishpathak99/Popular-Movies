package udacity.nanodegree.android.manishpathak.in.popularmovies.network.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import udacity.nanodegree.android.manishpathak.in.popularmovies.model.MovieModel;
import udacity.nanodegree.android.manishpathak.in.popularmovies.model.ReviewModel;
import udacity.nanodegree.android.manishpathak.in.popularmovies.model.VideoModel;

/**
 *
 *This class is used to defining the Endpoints
 * Created by manishpathak on 3/8/16.
 */
public interface ApiService {

//    What are the highest rated movies rated R?
//    /discover/movie/?certification_country=US&certification=R&sort_by=vote_average.desc

//    What are the most popular movies?
//    /discover/movie?sort_by=popularity.desc

        @GET ("/3/discover/movie")
        Call<MovieModel> getMovieList(@Query("sort_by") String sort_type,
                                      @Query("api_key") String api_key,
                                      @Query("page") int page,
                                      @Query("include_adult") boolean includeAdult);

        /**
         * Gets videos list.
         *
         * @param movieId  the movie id
         * @param api_key  the api key
         */
        @GET("/3/movie/{id}/videos")
        Call<VideoModel>  getVideosList(@Path("id") long movieId, @Query("api_key") String api_key);

        /**
         * Gets reviews list.
         *
         * @param movieId  the movie id
         * @param api_key  the api key
         * @param page     the page
         */
        @GET("/3/movie/{id}/reviews")
        Call<ReviewModel>  getReviewsList(@Path("id") long movieId, @Query("api_key") String api_key, @Query("page") int page);
}
