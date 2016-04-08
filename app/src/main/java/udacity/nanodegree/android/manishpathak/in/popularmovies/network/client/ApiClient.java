package udacity.nanodegree.android.manishpathak.in.popularmovies.network.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import udacity.nanodegree.android.manishpathak.in.popularmovies.constants.AppConstants;
import udacity.nanodegree.android.manishpathak.in.popularmovies.network.service.ApiService;

/**
 * Created by manishpathak on 3/8/16.
 */
public final class ApiClient {

    private final ApiService service;

    public ApiClient(){
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new GSONTypeAdapterFactory())
                .create();

    /*In Retrofit 2.0 , Plugin converter excluded compare to 1.9 */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.MOVIE_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ApiService.class);
    }

    public ApiService getService(){
        return service;
    }

}
