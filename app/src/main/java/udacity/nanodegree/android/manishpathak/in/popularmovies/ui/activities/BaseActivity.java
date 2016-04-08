package udacity.nanodegree.android.manishpathak.in.popularmovies.ui.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import udacity.nanodegree.android.manishpathak.in.popularmovies.R;
import udacity.nanodegree.android.manishpathak.in.popularmovies.constants.AppConstants;
import udacity.nanodegree.android.manishpathak.in.popularmovies.util.AppSettings;

//import com.google.android.gms.analytics.GoogleAnalytics;
//import com.google.android.gms.analytics.Tracker;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }

    protected abstract void init();

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }

    protected void showSpinner(DialogInterface.OnClickListener listener) {
        if(listener == null) {
            listener = new SpinnerListener();
        }
        String preSelectedIndexStr = (String) AppSettings.getPrefernce(this, AppSettings.PREF_NAME_POPULAR_MOVIES,
                AppConstants.AppSettings.CLICKED_SORTED_BY, "0");
        int preSelectedIndex = Integer.parseInt(preSelectedIndexStr);

        String[] spinnerItems = getResources().getStringArray(R.array.set_as);
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
        localBuilder.setTitle(getString(R.string.sort));
        localBuilder.setSingleChoiceItems(spinnerItems, preSelectedIndex, listener);
        AlertDialog alertBuilderSpinner = localBuilder.create();
        alertBuilderSpinner.show();
    }

    class SpinnerListener implements DialogInterface.OnClickListener {
        private SpinnerListener() {
        }

        public void onClick(DialogInterface arg0, int clickedIndex) {
            arg0.dismiss();
        }
    }
}
