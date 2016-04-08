package udacity.nanodegree.android.manishpathak.in.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by manishpathak on 3/8/16.
 */
public class BaseModel implements Parcelable{

    private int page;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public BaseModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(this.page);
        parcel.writeInt(this.totalResults);
        parcel.writeInt(this.totalPages);
    }

    protected BaseModel(@NonNull Parcel in) {
        this.page = in.readInt();
        this.totalResults = in.readInt();
        this.totalPages = in.readInt();
    }

    public static final Creator<BaseModel> CREATOR = new Creator<BaseModel>() {
        @NonNull
        public BaseModel createFromParcel(@NonNull Parcel source) {
            return new BaseModel(source);
        }

        @NonNull
        public BaseModel[] newArray(int size) {
            return new BaseModel[size];
        }
    };
}
