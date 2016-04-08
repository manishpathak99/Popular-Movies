package udacity.nanodegree.android.manishpathak.in.popularmovies.network.api;

import android.support.annotation.Nullable;

import okhttp3.ResponseBody;
import retrofit2.Response;
import udacity.nanodegree.android.manishpathak.in.popularmovies.model.BaseModel;

/**
 * Base API
 * Created by manishpathak on 3/8/16.
 */
public abstract class BaseApi {

    /**
     * Instantiate a new Base api.
     */
    protected BaseApi() {
    }

    protected void successResponse(BaseModel responseModel, Response response) {
        if (getApiListener() != null) {
            getApiListener().onRequestCompleted(responseModel);
            if(response != null && !response.isSuccess() && response.errorBody() != null) {
                getApiListener().onRequestFailed(response.errorBody());
            }
        }
    }

    /**
     * on
     */
    protected void failureResponse(ResponseBody errorBody) {
        if (getApiListener() != null) {
            getApiListener().onRequestFailed(errorBody);
        }
    }

    /**
     * Gets api listener.
     *
     * @return the api listener
     */
    @Nullable
    protected abstract BaseApiListener getApiListener();

    public interface BaseApiListener{

        void onRequestCompleted(BaseModel responseModel);
        void onRequestFailed(ResponseBody errorBody);
        void onRequestFailed(Throwable throwable);
    }
}
