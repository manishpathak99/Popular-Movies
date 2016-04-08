package udacity.nanodegree.android.manishpathak.in.popularmovies.util;

import android.support.annotation.NonNull;

import udacity.nanodegree.android.manishpathak.in.popularmovies.constants.AppConstants;
import udacity.nanodegree.android.manishpathak.in.popularmovies.network.api.response.MoviesResponseModel;

/**
 * Created by manishpathak on 4/9/16.
 */
public class CommonUtil {

    /**
     * Get genre list string.
     *
     * @param movies the movies
     * @return the genre
     */
    public static String getGenreList(@NonNull MoviesResponseModel movies) {
        String genre = "";
        if (movies.getGenreId() != null) {
            for (int id : movies.getGenreId()) {
                if (AppConstants.genreMap.get(id) != null) {
                    genre += AppConstants.genreMap.get(id).concat(", ");
                }
            }
        }
        genre = genre.replaceAll(" $", "").replaceAll(",$", "");
        return genre;
    }
}
