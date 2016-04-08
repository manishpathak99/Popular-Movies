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
import udacity.nanodegree.android.manishpathak.in.popularmovies.model.MovieModel;
import udacity.nanodegree.android.manishpathak.in.popularmovies.network.api.ApiManager;
import udacity.nanodegree.android.manishpathak.in.popularmovies.network.api.response.MoviesResponseModel;
import udacity.nanodegree.android.manishpathak.in.popularmovies.util.AppSettings;
import udacity.nanodegree.android.manishpathak.in.popularmovies.util.NetworkUtil;

public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, DialogInterface.OnClickListener{

    @Bind(R.id.recycler_view)
    public RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    public SwipeRefreshLayout swipeLayout;
    @Bind(R.id.sort_layout)
    public RelativeLayout mSortFooterLayout;

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
            fetchMovieList(mPageCount + 1);
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
    };;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();
        init();
        fetchMovieList(1);
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
        List data = new ArrayList<>();
        mMovieAdapter = new MovieAdapter(this, data);
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
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchMovieList(final int pageCount) {
        String preSelectedIndexStr = (String) AppSettings.getPrefernce(this, AppSettings.PREF_NAME_POPULAR_MOVIES,
                AppConstants.AppSettings.CLICKED_SORTED_BY, "0");
        int preSelectedIndex = Integer.parseInt(preSelectedIndexStr);
        fetchMovieList(SortingOrder.values()[preSelectedIndex].getSortedBy(), pageCount);
    }
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
                fetchMovieList(1);
            }
        }, 500);
    }

    public void onSortClicked(View v) {
        showSpinner(this);
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int clickedIndex) {
        dialogInterface.dismiss();
        if (!NetworkUtil.checkInternetConnection(MainActivity.this)) {
            NetworkUtil.showNetWorkSnackBar(MainActivity.this);
            return;
        }
        clearAdapter();
        AppSettings.setPreference(this, AppSettings.PREF_NAME_POPULAR_MOVIES,
                AppConstants.AppSettings.CLICKED_SORTED_BY, String.valueOf(clickedIndex));
        fetchMovieList(1);
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
}
