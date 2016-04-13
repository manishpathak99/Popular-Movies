package udacity.nanodegree.android.manishpathak.in.popularmovies.network.api.response;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import udacity.nanodegree.android.manishpathak.in.popularmovies.model.VideoModel;

/**
 * @author Manish Pathak
 * @date 13/4/16
 */
public class VideoResponseModel extends VideoModel {

    public static final Parcelable.Creator<VideoResponseModel> CREATOR = new Parcelable.Creator<VideoResponseModel>() {
        @NonNull
        public VideoResponseModel createFromParcel(@NonNull Parcel source) {
            return new VideoResponseModel(source);
        }

        @NonNull
        public VideoResponseModel[] newArray(int size) {
            return new VideoResponseModel[size];
        }
    };
    private String id;
    private String name;
    private String site;
    @SerializedName("key")
    private String videoId;
    private int    size;
    private String type;

    public VideoResponseModel() {
    }

    protected VideoResponseModel(@NonNull Parcel in) {
        super(in);
        this.id = in.readString();
        this.name = in.readString();
        this.site = in.readString();
        this.videoId = in.readString();
        this.size = in.readInt();
        this.type = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.site);
        dest.writeString(this.videoId);
        dest.writeInt(this.size);
        dest.writeString(this.type);
    }
}
