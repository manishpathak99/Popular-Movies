package udacity.nanodegree.android.manishpathak.in.popularmovies.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.Bind;
import butterknife.ButterKnife;
import udacity.nanodegree.android.manishpathak.in.popularmovies.R;
import udacity.nanodegree.android.manishpathak.in.popularmovies.constants.AppConstants;
import udacity.nanodegree.android.manishpathak.in.popularmovies.network.api.response.MoviesResponseModel;
import udacity.nanodegree.android.manishpathak.in.popularmovies.util.CommonUtil;

/**
 * Created by manishpathak on 4/8/16.
 */
public class MovieDetailFragment extends Fragment{

    @Bind(R.id.poster_image)
    public ImageView posterImage;
    @Bind(R.id.movie_name)
    public TextView mTitleView;
    @Bind(R.id.release_date)
    public TextView mReleaseDate;
    @Bind(R.id.rating)
    public TextView mRating;
    @Bind(R.id.description)
    public TextView mDescription;
    @Bind(R.id.votes)
    public TextView mVotes;
    @Bind(R.id.genres)
    public TextView mGenre;
    @Nullable
    private MoviesResponseModel moviesResponseModel;
    @Nullable
    private Toolbar mToolbar = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(AppConstants.INTENT_EXTRA)) {
            moviesResponseModel = getArguments().getParcelable(AppConstants.INTENT_EXTRA);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_movie_details, container, false);
        ButterKnife.bind(this, rootView);
        init(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolBar(view);
//        initValues();
//        setToolBar(view);
        loadBackdrop(view);
//        createContentValues();
//        fetchVideoList();
//        fetchReviewsList();
    }

    private void init(View view){
        mTitleView.setText(moviesResponseModel.getOriginalTitle());
        mReleaseDate.setText(moviesResponseModel.getReleaseDate());
        mRating.setText(getString(R.string.movie_details_rating, moviesResponseModel.getVoteAverage()));
        mDescription.setText(moviesResponseModel.getOverview());
        mVotes.setText(getString(R.string.movie_details_votes, moviesResponseModel.getVoteCount()));
        mGenre.setText(getString(R.string.movie_details_genre, CommonUtil.getGenreList(moviesResponseModel)));
        Glide.with(getContext())
                .load(AppConstants.POSTER_API_BASE_URL + moviesResponseModel.getPosterPath())
                .error(R.drawable.placeholder)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(posterImage);

    }

    private void setToolBar(@NonNull View view) {
        mToolbar = (Toolbar) getActivity().findViewById(R.id.detail_toolbar);
        mToolbar.setTitle(moviesResponseModel.getTitle());
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
    }
    /**
     * Loads the backdrop image
     *
     * @param view backdrop
     */
    private void loadBackdrop(@NonNull View view) {
        ImageView backdropImage = (ImageView) view.findViewById(R.id.poster_image);
        Glide.with(getContext())
                .load(AppConstants.BACKDROP_BASE_URL +
                        moviesResponseModel.getBackdropPath())
                .centerCrop()
                .error(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(backdropImage);
    }
}
