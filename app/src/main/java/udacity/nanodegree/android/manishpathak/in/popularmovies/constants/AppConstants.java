package udacity.nanodegree.android.manishpathak.in.popularmovies.constants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by manishpathak on 3/8/16.
 */
public final class AppConstants {

    public static final String MOVIE_API_BASE_URL = "https://api.themoviedb.org/";
    public static final String POSTER_API_BASE_URL = "http://image.tmdb.org/t/p/w185";
    public static final String BACKDROP_BASE_URL = "http://image.tmdb.org/t/p/w500";
    public static final String API_KEY           = "PUT_YOUR_KEY";
    public static final String SORT_BY_POPULARITY        = "popularity.desc";
    public static final String SORT_BY_RATING      = "vote_average.desc";
    public static final String INTENT_EXTRA      = "intent_extra";
    public static final String SITE_YOUTUBE      = "YouTube";
    public static Map<Integer, String> genreMap = createGenreMap();

    private static Map<Integer, String> createGenreMap(){
        HashMap<Integer, String> genreMap = new HashMap<>();
        genreMap.put(28, "Action");
        genreMap.put(12, "Adventure");
        genreMap.put(16, "Animation");
        genreMap.put(35, "Comedy");
        genreMap.put(80, "Crime");
        genreMap.put(99, "Documentary");
        genreMap.put(18, "Drama");
        genreMap.put(10751, "Family");
        genreMap.put(14, "Fantasy");
        genreMap.put(10769, "Foreign");
        genreMap.put(36, "History");
        genreMap.put(27, "Horror");
        genreMap.put(10402, "Music");
        genreMap.put(9648, "Mystery");
        genreMap.put(10749, "Romance");
        genreMap.put(878, "Science Fiction");
        genreMap.put(10770, "TV Movie");
        genreMap.put(53, "Thriller");
        genreMap.put(10752, "War");
        genreMap.put(37, "Western");
        Collections.unmodifiableMap(genreMap);
        return genreMap;
    }

    // AppSetting constants
    public static final class AppSettings {
        public static final String CLICKED_SORTED_BY = "clicked_sort_by";
    }
}