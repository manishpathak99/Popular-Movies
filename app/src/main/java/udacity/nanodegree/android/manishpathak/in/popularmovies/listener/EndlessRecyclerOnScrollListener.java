package udacity.nanodegree.android.manishpathak.in.popularmovies.listener;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;


public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    public static String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();


    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 4; // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItem, visibleItemCount, totalItemCount;


    private int current_page = 1;


    private GridLayoutManager mGridLayoutManager;

    public void setLayoutManager(GridLayoutManager gridLayoutManager){
        this.mGridLayoutManager = gridLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mGridLayoutManager.getItemCount();
        firstVisibleItem = mGridLayoutManager.findFirstVisibleItemPosition();

        if (loading && totalItemCount > previousTotal) {
            loading = false;
            previousTotal = totalItemCount;
        }

        if (!loading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached
            // Do something 
            current_page++;
            onLoadMore(current_page);
            loading = true;
        }
    }


    public abstract void onLoadMore(int current_page);

    public abstract void onLoadMore();

    /**
     * Reset item count variables.
     */
    public void resetAllVariables(){
        visibleItemCount = 0;
        totalItemCount = 0;
        firstVisibleItem = 0;
        previousTotal = 0;
    }

}