package udacity.nanodegree.android.manishpathak.in.popularmovies.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import udacity.nanodegree.android.manishpathak.in.popularmovies.model.FavoriteModel;

public class FavoriteSharedPreference {
    public static final String PREFS_NAME = "POPULAR_MOVIE_APP";
    public static final String FAVORITES = "Favorite";
    public FavoriteSharedPreference() {
        super();
    }
    public boolean storeFavorites(Context context, List favorites) {
// used for store arrayList in json format
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);
        editor.putString(FAVORITES, jsonFavorites);
        return editor.commit();
    }
    public ArrayList loadFavorites(Context context) {
// used for retrieving arraylist from json formatted string
        SharedPreferences settings;
        List favorites;
        settings = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            FavoriteModel[] favoriteItems = gson.fromJson(jsonFavorites,FavoriteModel[].class);
            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList(favorites);
        } else
            return null;
        return (ArrayList) favorites;
    }

    public boolean isFavoriteStored(Context context){
        boolean isFavorite = false;
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            if(!StringUtils.containsNullOrEmpty(jsonFavorites)) {
                isFavorite = true;
            }
        }
         return isFavorite;
    }

    public boolean addFavorite(Context context, FavoriteModel favoriteList) {
        List favorites = loadFavorites(context);
        if (favorites == null)
            favorites = new ArrayList();
        favorites.add(favoriteList);
        return storeFavorites(context, favorites);
    }
    public boolean removeFavorite(Context context, FavoriteModel favoriteList) {
        ArrayList favorites = loadFavorites(context);
        if (favorites != null) {
            favorites.remove(favoriteList);
            return storeFavorites(context, favorites);
        }
        return false;
    }
}