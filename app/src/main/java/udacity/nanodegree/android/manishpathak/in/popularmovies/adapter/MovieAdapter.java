package udacity.nanodegree.android.manishpathak.in.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import udacity.nanodegree.android.manishpathak.in.popularmovies.R;
import udacity.nanodegree.android.manishpathak.in.popularmovies.constants.AppConstants;
import udacity.nanodegree.android.manishpathak.in.popularmovies.network.api.response.MoviesResponseModel;
import udacity.nanodegree.android.manishpathak.in.popularmovies.ui.activities.MovieDetailsActivity;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    @NonNull
    private Context mContext;

    private List<MoviesResponseModel> mMoviesResponseModels = new ArrayList<>();

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        @Bind(R.id.movie_item_genres)
        TextView mMovieId;
        @Bind(R.id.movie_item_title)
        TextView mMovieName;
        @Bind(R.id.image_movie)
        ImageView mImageView;

        @NonNull
        private final View mView;

        public ViewHolder(@NonNull View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MovieAdapter(Context context, List<MoviesResponseModel> moviesResponseModels) {
        this.mContext = context;
        this.mMoviesResponseModels = moviesResponseModels;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_movie, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final MoviesResponseModel movie = mMoviesResponseModels.get(position);
//        holder.mMovieId.setText(CommonUtil.getGenreList(movies));
        holder.mMovieName.setText(movie.getTitle());
        Glide.with(mContext)
                .load(AppConstants.POSTER_API_BASE_URL + movie.getPosterPath())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.mImageView);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra(AppConstants.INTENT_EXTRA, movie);
                context.startActivity(intent);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mMoviesResponseModels.size();
    }


    public List<MoviesResponseModel> getMovies() {
        return mMoviesResponseModels;
    }

    public void setMovies(ArrayList<MoviesResponseModel> moviesResponseModels) {
        this.mMoviesResponseModels = moviesResponseModels;
        // notifyDataSetChanged();
    }

    public void addMovies(MoviesResponseModel moviesResponseModel) {
        if (mMoviesResponseModels == null) {
            mMoviesResponseModels = new ArrayList();
        }
        mMoviesResponseModels.add(moviesResponseModel);
    }
}