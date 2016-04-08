package udacity.nanodegree.android.manishpathak.in.popularmovies.model;

import android.os.Parcel;
import android.support.annotation.NonNull;

import java.util.List;

import udacity.nanodegree.android.manishpathak.in.popularmovies.network.api.response.MoviesResponseModel;

/**
 * Created by manishpathak on 3/8/16.
 */
public class MovieModel extends BaseModel{
    private List<MoviesResponseModel> results;

    public List<MoviesResponseModel> getResults() {
        return results;
    }

    public void setResults(List<MoviesResponseModel> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(results);
    }

    public MovieModel() {
    }

    protected MovieModel(@NonNull Parcel in) {
        super(in);
        this.results = in.createTypedArrayList(MoviesResponseModel.CREATOR);
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @NonNull
        public MovieModel createFromParcel(@NonNull Parcel source) {
            return new MovieModel(source);
        }

        @NonNull
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };
}
