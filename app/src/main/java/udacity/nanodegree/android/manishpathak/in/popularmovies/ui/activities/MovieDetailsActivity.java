package udacity.nanodegree.android.manishpathak.in.popularmovies.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import butterknife.ButterKnife;
import udacity.nanodegree.android.manishpathak.in.popularmovies.R;
import udacity.nanodegree.android.manishpathak.in.popularmovies.constants.AppConstants;
import udacity.nanodegree.android.manishpathak.in.popularmovies.ui.fragment.MovieDetailFragment;

/**
 * Created by manishpathak on 4/8/16.
 */
public class MovieDetailsActivity extends BaseActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);
        loadDetailsFragment(savedInstanceState);
    }

    @Override
    protected void init() {

    }

    private void loadDetailsFragment(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(AppConstants.INTENT_EXTRA, getIntent().getParcelableExtra(AppConstants.INTENT_EXTRA));
            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
