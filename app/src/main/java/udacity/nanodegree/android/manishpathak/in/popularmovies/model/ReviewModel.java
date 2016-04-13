package udacity.nanodegree.android.manishpathak.in.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.List;

import udacity.nanodegree.android.manishpathak.in.popularmovies.network.api.response.ReviewResponseModel;

/**
 * @author Manish Pathak
 * @date 13/4/16
 */
public class ReviewModel extends BaseModel {

    public static final Parcelable.Creator<ReviewModel> CREATOR = new Parcelable.Creator<ReviewModel>() {
        @NonNull
        public ReviewModel createFromParcel(@NonNull Parcel source) {
            return new ReviewModel(source);
        }

        @NonNull
        public ReviewModel[] newArray(int size) {
            return new ReviewModel[size];
        }
    };
    private List<ReviewResponseModel> results;

    public ReviewModel() {
    }

    protected ReviewModel(@NonNull Parcel in) {
        super(in);
        this.results = in.createTypedArrayList(ReviewResponseModel.CREATOR);
    }

    public List<ReviewResponseModel> getResults() {
        return results;
    }

    public void setResults(List<ReviewResponseModel> results) {
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
}
