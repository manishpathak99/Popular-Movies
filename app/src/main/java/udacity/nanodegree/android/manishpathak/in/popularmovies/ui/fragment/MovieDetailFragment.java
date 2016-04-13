package udacity.nanodegree.android.manishpathak.in.popularmovies.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import udacity.nanodegree.android.manishpathak.in.popularmovies.R;
import udacity.nanodegree.android.manishpathak.in.popularmovies.constants.AppConstants;
import udacity.nanodegree.android.manishpathak.in.popularmovies.model.ReviewModel;
import udacity.nanodegree.android.manishpathak.in.popularmovies.model.VideoModel;
import udacity.nanodegree.android.manishpathak.in.popularmovies.network.api.ApiManager;
import udacity.nanodegree.android.manishpathak.in.popularmovies.network.api.response.MoviesResponseModel;
import udacity.nanodegree.android.manishpathak.in.popularmovies.network.api.response.ReviewResponseModel;
import udacity.nanodegree.android.manishpathak.in.popularmovies.network.api.response.VideoResponseModel;
import udacity.nanodegree.android.manishpathak.in.popularmovies.util.CommonUtil;

/**
 * Created by manishpathak on 4/8/16.
 */
public class MovieDetailFragment extends Fragment implements View.OnClickListener{

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
    @Bind(R.id.trailers_label)
    public TextView mTrailerLabel;
    @Bind(R.id.trailers_container)
    public HorizontalScrollView mTrailersScrollView;
    @Bind(R.id.trailers)
    public LinearLayout mTrailersView;
    @Bind(R.id.reviews_label)
    public TextView mReviewsLabel;
    @Bind(R.id.reviews)
    public LinearLayout mReviewsView;
    @Bind(R.id.fab)
    public FloatingActionButton mFavorite;

    @Nullable
    private MoviesResponseModel moviesResponseModel;
    @Nullable
    private Toolbar mToolbar = null;
    private List<VideoResponseModel> videoArrayList;

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
        fetchVideoList();
        fetchReviewsList();
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
        ImageView backdropImage = (ImageView) view.findViewById(R.id.backdrop);
        Glide.with(getContext())
                .load(AppConstants.BACKDROP_BASE_URL +
                        moviesResponseModel.getBackdropPath())
                .centerCrop()
                .error(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(backdropImage);
    }

    private void fetchVideoList() {
        ApiManager.getInstance().fetchVideosList(new ApiManager.ProgressListener<VideoModel>() {
            @Override
            public void inProgress() {

            }

            @Override
            public void failed(String message) {

            }

            @Override
            public void completed(@NonNull VideoModel response) {
                videoArrayList = response.getResults();
                showTrailers(videoArrayList);
            }
        }, moviesResponseModel.getId());
    }

    private void fetchReviewsList() {
        ApiManager.getInstance().fetchReviewsList(new ApiManager.ProgressListener<ReviewModel>() {
            @Override
            public void inProgress() {

            }

            @Override
            public void failed(String message) {

            }

            @Override
            public void completed(@NonNull ReviewModel response) {
                ArrayList<ReviewResponseModel> reviewArrayList = (ArrayList<ReviewResponseModel>) response.getResults();
                showReviews(reviewArrayList);
            }
        }, moviesResponseModel.getId(), 1);
    }

    private void showTrailers(@NonNull List<VideoResponseModel> trailers) {
        if (trailers.isEmpty()) {
            mTrailerLabel.setVisibility(View.GONE);
            mTrailersView.setVisibility(View.GONE);
            mTrailersScrollView.setVisibility(View.GONE);

        } else {
            mTrailerLabel.setVisibility(View.VISIBLE);
            mTrailersView.setVisibility(View.VISIBLE);
            mTrailersScrollView.setVisibility(View.VISIBLE);
            mTrailersView.removeAllViews();
            if (getActivity() != null && isAdded()) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                shareVideos();
                for (VideoResponseModel trailer : trailers) {
                    ViewGroup thumbContainer = (ViewGroup) inflater.inflate(R.layout.video, mTrailersView, false);
                    ImageView thumbView = (ImageView) thumbContainer.findViewById(R.id.video_thumb);
                    thumbView.setTag(R.id.video_thumb, CommonUtil.getUrl(trailer));
                    thumbView.requestLayout();
                    thumbView.setOnClickListener(this);
                    Glide.with(this)
                            .load(CommonUtil.getThumbnailUrl(trailer))
                            .centerCrop()
                            .error(R.drawable.placeholder)
                            .placeholder(R.color.cardview_shadow_start_color)
                            .into(thumbView);
                    mTrailersView.addView(thumbContainer);
                }
            }
        }
    }

    public void showReviews(@NonNull List<ReviewResponseModel> reviews) {
        if (reviews.isEmpty()) {
            mReviewsLabel.setVisibility(View.GONE);
            mReviewsView.setVisibility(View.GONE);
        } else {
            mReviewsLabel.setVisibility(View.VISIBLE);
            mReviewsView.setVisibility(View.VISIBLE);
            mReviewsView.removeAllViews();
            if (getActivity() != null && isAdded()) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                for (ReviewResponseModel review : reviews) {
                    ViewGroup reviewContainer = (ViewGroup) inflater.inflate(R.layout.review, mReviewsView,
                            false);
                    TextView reviewAuthor = (TextView) reviewContainer.findViewById(R.id.review_author);
                    TextView reviewContent = (TextView) reviewContainer.findViewById(R.id.review_content);
                    reviewAuthor.setText(review.getAuthor());
                    reviewContent.setText(review.getContent());
                    reviewAuthor.setPadding(10, 10, 10, 10);
                    reviewContent.setPadding(10, 10, 10, 10);
                    reviewContent.setOnClickListener(this);
                    mReviewsView.addView(reviewContainer);
                }
            }
        }
    }

    private void shareVideos() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String trailersVideo = "No Videos to Share";
        shareIntent.putExtra(Intent.EXTRA_TEXT, trailersVideo);
        try {
            if (videoArrayList != null) {
                trailersVideo = CommonUtil.getUrl(videoArrayList.get(0));
            }
        } catch (IndexOutOfBoundsException ignored) {
        }

        shareIntent.putExtra(Intent.EXTRA_TEXT, trailersVideo);
//        if (shareActionProvider != null) {
//            shareActionProvider.setShareIntent(shareIntent);
//        }
    }

    @Override
    public void onClick(@NonNull View view) {
        {
            switch (view.getId()) {
                case R.id.video_thumb:
                    String videoUrl = (String) view.getTag(R.id.video_thumb);
                    Intent playVideoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
                    startActivity(playVideoIntent);
                    break;

                case R.id.review_content:
                    TextView review = (TextView) view;
                    if (review.getMaxLines() == 5) {
                        review.setMaxLines(500);
                    } else {
                        review.setMaxLines(5);
                    }
                    break;
                case R.id.fab:
                default:
                    break;
            }
        }
    }
}
