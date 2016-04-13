package udacity.nanodegree.android.manishpathak.in.popularmovies.util;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;

import udacity.nanodegree.android.manishpathak.in.popularmovies.constants.AppConstants;
import udacity.nanodegree.android.manishpathak.in.popularmovies.network.api.response.MoviesResponseModel;
import udacity.nanodegree.android.manishpathak.in.popularmovies.network.api.response.VideoResponseModel;

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

    /**
     * Gets url.
     *
     * @param video the video
     * @return the url
     */
    public static String getUrl(@NonNull VideoResponseModel video) {
        if (AppConstants.SITE_YOUTUBE.equalsIgnoreCase(video.getSite())) {
            return String.format("http://www.youtube.com/watch?v=%1$s", video.getVideoId());
        } else {
            return "";
        }
    }

    /**
     * Gets thumbnail url.
     *
     * @param video the video
     * @return the thumbnail url
     */
    public static String getThumbnailUrl(@NonNull VideoResponseModel video) {
        if (AppConstants.SITE_YOUTUBE.equalsIgnoreCase(video.getSite())) {
            return String.format("http://img.youtube.com/vi/%1$s/0.jpg", video.getVideoId());
        } else {
            return "";
        }
    }

    public static void showSnackBar(final Activity activity, @NonNull final String message) {
        Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

}
