package udacity.nanodegree.android.manishpathak.in.popularmovies.ui.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import udacity.nanodegree.android.manishpathak.in.popularmovies.R;
import udacity.nanodegree.android.manishpathak.in.popularmovies.adapter.MovieAdapter;
import udacity.nanodegree.android.manishpathak.in.popularmovies.constants.AppConstants;
import udacity.nanodegree.android.manishpathak.in.popularmovies.enums.SortingOrder;
import udacity.nanodegree.android.manishpathak.in.popularmovies.listener.EndlessRecyclerOnScrollListener;
import udacity.nanodegree.android.manishpathak.in.popularmovies.model.FavoriteModel;
import udacity.nanodegree.android.manishpathak.in.popularmovies.model.MovieModel;
import udacity.nanodegree.android.manishpathak.in.popularmovies.network.api.ApiManager;
import udacity.nanodegree.android.manishpathak.in.popularmovies.network.api.response.MoviesResponseModel;
import udacity.nanodegree.android.manishpathak.in.popularmovies.util.AppSettings;
import udacity.nanodegree.android.manishpathak.in.popularmovies.util.FavoriteSharedPreference;
import udacity.nanodegree.android.manishpathak.in.popularmovies.util.NetworkUtil;

public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, DialogInterface.OnClickListener{

    private static final String STATE_MOVIES = "state_movies";
    private static final String STATE_PAGE_COUNT = "state_movie_page_count";

    @Bind(R.id.recycler_view)
    public RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    public SwipeRefreshLayout swipeLayout;
    @Bind(R.id.sort_layout)
    public RelativeLayout mSortFooterLayout;
    FavoriteSharedPreference favoriteSharedPreference;
    private GridLayoutManager mLayoutManager;
    private MovieAdapter mMovieAdapter;
    private int mPageCount = 1;
    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadMore(int current_page) {
            if (!NetworkUtil.checkInternetConnection(MainActivity.this)) {
                NetworkUtil.showNetWorkSnackBar(MainActivity.this);
                return;
            }
            Log.e("PAge Count", mPageCount + "");

            String preSelectedIndexStr = (String) AppSettings.getPrefernce(MainActivity.this, AppSettings.PREF_NAME_POPULAR_MOVIES,
                    AppConstants.AppSettings.CLICKED_SORTED_BY, "0");
            int preSelectedIndex = Integer.parseInt(preSelectedIndexStr);
            if(preSelectedIndex > 1) {
                showFavouriteMovies();
            } else {
                fetchMovieList(SortingOrder.values()[preSelectedIndex].getSortedBy(), mPageCount + 1);
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                showFooter();
            } else {
                hideFooter();
            }
        }

        @Override
        public void onLoadMore() {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        favoriteSharedPreference = new FavoriteSharedPreference();

        if (savedInstanceState != null) {
            mPageCount = savedInstanceState.getInt(STATE_PAGE_COUNT, 1);
        }
        ArrayList<MoviesResponseModel> restoredMovies = savedInstanceState != null
                ? savedInstanceState.<MoviesResponseModel>getParcelableArrayList(STATE_MOVIES) : new ArrayList<MoviesResponseModel>();
        mMovieAdapter = new MovieAdapter(this, restoredMovies);
        init();

        String preSelectedIndexStr = (String) AppSettings.getPrefernce(this, AppSettings.PREF_NAME_POPULAR_MOVIES,
                AppConstants.AppSettings.CLICKED_SORTED_BY, "0");
        int preSelectedIndex = Integer.parseInt(preSelectedIndexStr);
        if(preSelectedIndex > 1) {
            showFavouriteMovies();
        } else {
            fetchMovieList(SortingOrder.values()[preSelectedIndex].getSortedBy(), 1);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        String preSelectedIndexStr = (String) AppSettings.getPrefernce(MainActivity.this, AppSettings.PREF_NAME_POPULAR_MOVIES,
                AppConstants.AppSettings.CLICKED_SORTED_BY, "0");
        int preSelectedIndex = Integer.parseInt(preSelectedIndexStr);
        if(preSelectedIndex > 1) {
            showFavouriteMovies();
        }
    }

    @Override
    protected void init() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mOnScrollListener.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnScrollListener(mOnScrollListener);

        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeResources(android.R.color.holo_red_light);

        // Specify Adapter for RecyclerView
        mRecyclerView.setAdapter(mMovieAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if(id == R.id.action_favorite){
            showFavouriteMovies();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_MOVIES, new ArrayList<>(mMovieAdapter.getMovies()));
        outState.putInt(STATE_PAGE_COUNT, mPageCount);
    }

//    private void fetchMovieList(final int pageCount) {
//        fetchMovieList(SortingOrder.values()[preSelectedIndex].getSortedBy(), pageCount);
//    }
    private void fetchMovieList(final String sortOrder, final int pageCount){
        ApiManager.getInstance().fetchMoviesList(new ApiManager.ProgressListener<MovieModel>() {
            @Override
            public void inProgress() {
                if (!swipeLayout.isRefreshing()) {
                    swipeLayout.setRefreshing(true);
                }
            }

            @Override
            public void failed(String message) {
                if (swipeLayout.isRefreshing()) {
                    swipeLayout.setRefreshing(false);
                }
            }

            @Override
            public void completed(@NonNull MovieModel movieModel) {
                ArrayList<MoviesResponseModel> moviesArrayList = (ArrayList<MoviesResponseModel>) movieModel.getResults();
                mPageCount = movieModel.getPage();
                mMovieAdapter.getMovies().addAll(moviesArrayList);
                mMovieAdapter.notifyDataSetChanged();
                if (swipeLayout.isRefreshing()) {
                    swipeLayout.setRefreshing(false);
                }
            }
        }, sortOrder, pageCount);

    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeLayout.setRefreshing(false);
                hideFooter();
                if (!NetworkUtil.checkInternetConnection(MainActivity.this)) {
                    NetworkUtil.showNetWorkSnackBar(MainActivity.this);
                    return;
                }
                String preSelectedIndexStr = (String) AppSettings.getPrefernce(MainActivity.this, AppSettings.PREF_NAME_POPULAR_MOVIES,
                        AppConstants.AppSettings.CLICKED_SORTED_BY, "0");
                int preSelectedIndex = Integer.parseInt(preSelectedIndexStr);
                if(preSelectedIndex > 1) {
                    showFavouriteMovies();
                } else {
                    fetchMovieList(SortingOrder.values()[preSelectedIndex].getSortedBy(), mPageCount + 1);
                }
            }
        }, 500);
    }

    public void onSortClicked(View v) {
        showSpinner(this);
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int clickedIndex) {
        dialogInterface.dismiss();
        AppSettings.setPreference(this, AppSettings.PREF_NAME_POPULAR_MOVIES,
                AppConstants.AppSettings.CLICKED_SORTED_BY, String.valueOf(clickedIndex));
        if (!NetworkUtil.checkInternetConnection(MainActivity.this)) {
            NetworkUtil.showNetWorkSnackBar(MainActivity.this);
            return;
        }
        clearAdapter();
        if(clickedIndex > 1) {
            showFavouriteMovies();
        } else {
            fetchMovieList(SortingOrder.values()[clickedIndex].getSortedBy(), 1);
        }
    }

    /**
     * Clears the adapter
     */
    private void clearAdapter() {
        mPageCount = 0;
        mMovieAdapter.getMovies().clear();
        if (mOnScrollListener != null){
            mOnScrollListener.resetAllVariables();
        }
    }

    private void showFooter() {
        mSortFooterLayout.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
    }

    public void hideFooter() {
        mSortFooterLayout.animate().translationY(mSortFooterLayout.getHeight()).
                setInterpolator(new AccelerateInterpolator(2));
    }

    /**
     * show Favourite movies from shared preference
     */
    private void showFavouriteMovies() {
        List<FavoriteModel> favoriteModelList = favoriteSharedPreference.loadFavorites(this);
        ArrayList<MoviesResponseModel> mPosterList = new ArrayList<>();
            for(FavoriteModel favoriteModel :favoriteModelList){
                MoviesResponseModel resultModel = new MoviesResponseModel();
                resultModel.setTitle(favoriteModel.getTitle());
                resultModel.setPosterPath(favoriteModel.getPosterPath());
                resultModel.setBackdropPath(favoriteModel.getBackdropPath());
                resultModel.setOriginalTitle(favoriteModel.getOriginalTitle());
                resultModel.setOverview(favoriteModel.getOverview());
                resultModel.setVoteAverage(favoriteModel.getVoteAverage());
                resultModel.setReleaseDate(favoriteModel.getReleaseDate());
                resultModel.setId(favoriteModel.getId());
                resultModel.setGenreId(favoriteModel.getGenreId());
                mPosterList.add(resultModel);
            }
            clearAdapter();
            mMovieAdapter.getMovies().addAll(mPosterList);
            mMovieAdapter.notifyDataSetChanged();
        }
}
