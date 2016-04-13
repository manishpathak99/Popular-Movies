package udacity.nanodegree.android.manishpathak.in.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.List;

import udacity.nanodegree.android.manishpathak.in.popularmovies.network.api.response.VideoResponseModel;

/**
 * @author Manish Pathak
 * @date 13/4/16
 */
public class VideoModel extends BaseModel {

    public static final Parcelable.Creator<VideoModel> CREATOR = new Parcelable.Creator<VideoModel>() {
        @NonNull
        public VideoModel createFromParcel(@NonNull Parcel source) {
            return new VideoModel(source);
        }

        @NonNull
        public VideoModel[] newArray(int size) {
            return new VideoModel[size];
        }
    };
    private List<VideoResponseModel> results;

    public VideoModel() {
    }

    protected VideoModel(@NonNull Parcel in) {
        super(in);
        this.results = in.createTypedArrayList(VideoResponseModel.CREATOR);
    }

    public List<VideoResponseModel> getResults() {
        return results;
    }

    public void setResults(List<VideoResponseModel> results) {
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
