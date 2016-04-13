package udacity.nanodegree.android.manishpathak.in.popularmovies.network.api.response;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import udacity.nanodegree.android.manishpathak.in.popularmovies.model.ReviewModel;

/**
 * @author Manish Pathak
 * @date 13 /4/16
 */
public class ReviewResponseModel extends ReviewModel {

    public static final Parcelable.Creator<ReviewResponseModel> CREATOR = new Parcelable.Creator<ReviewResponseModel>() {
        @NonNull
        public ReviewResponseModel createFromParcel(@NonNull Parcel source) {
            return new ReviewResponseModel(source);
        }

        @NonNull
        public ReviewResponseModel[] newArray(int size) {
            return new ReviewResponseModel[size];
        }
    };
    private String id;
    private String author;
    private String content;
    private String url;

    public ReviewResponseModel() {
    }

    protected ReviewResponseModel(@NonNull Parcel in) {
        super(in);
        this.id = in.readString();
        this.author = in.readString();
        this.content = in.readString();
        this.url = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.id);
        dest.writeString(this.author);
        dest.writeString(this.content);
        dest.writeString(this.url);
    }
}
