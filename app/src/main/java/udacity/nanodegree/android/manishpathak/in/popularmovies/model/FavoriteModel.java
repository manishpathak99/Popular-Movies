package udacity.nanodegree.android.manishpathak.in.popularmovies.model;

/**
 * Created by manishpathak on 13/4/16.
 * Model for saving favorite movie in the sharedPreference
 */
public class FavoriteModel {
 
    private long id;
    private String title;
    private String  posterPath;
    private String  overview;
    private String  releaseDate;
    private String  originalTitle;
    private String  backdropPath;
    private double  voteAverage;
    private int[]   genreId;

    public FavoriteModel() {
        super(); 
    } 
 
    public FavoriteModel(long id, String title) {
        super(); 
        this.id = id;
        this.title = title;
    }
 
    public long getId() {
        return id;
    } 
 
    public void setId(long id) {
        this.id = id;
    } 
 
    public String getTitle() {
        return title;
    } 
 
    public void setTitle(String title) {
        this.title = title;
    }
    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int[] getGenreId() {
        return genreId;
    }

    public void setGenreId(int[] genreId) {
        this.genreId = genreId;
    }
    @Override 
    public boolean equals(Object obj) {
        if (this == obj)
            return true; 
        if (obj == null)
            return false; 
        if (getClass() != obj.getClass())
            return false; 
        FavoriteModel other = (FavoriteModel) obj;
        return id == other.id;
    }
} 