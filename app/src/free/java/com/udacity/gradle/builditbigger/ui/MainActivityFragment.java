package com.udacity.gradle.builditbigger.ui;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.stevenberdak.displaything.DisplayThingActivity;
import com.udacity.gradle.builditbigger.AppUtils;
import com.udacity.gradle.builditbigger.IdlingResourceSingleton;
import com.udacity.gradle.builditbigger.models.JokeData;
import com.udacity.gradle.builditbigger.viewmodels.MainViewModel;
import com.udacity.gradle.builditbigger.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final int DISPLAY_THING_REQUEST_CODE = 1010;

    @BindView(R.id.button_tell_joke)
    Button mButtonTellJoke;
    @BindView(R.id.main_progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.text_view_status_message)
    TextView mTVStatusMessage;

    private MainViewModel mViewModel;
    private Observer<JokeData> mJokeObserver;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        AdView mAdView = root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        //Initialize the ViewModel
        if (getActivity() != null) {
            if (mViewModel == null)
                mViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        } else
            displayErrorSnackbar(getString(R.string.fatal_error));

        setClickListener();
    }

    @Override
    public void onResume() {
        super.onResume();

        clearViewState();

        //Initialize joke.
        if (getContext() != null) {
            if (AppUtils.isNetworkAvailable(getContext())) {
                mViewModel.initNextJoke();
            }
        } else displayErrorSnackbar(getString(R.string.fatal_error));

        if (IdlingResourceSingleton.isActive())
            IdlingResourceSingleton.getInstance().isIdle();
    }

    @Override
    public void onPause() {
        super.onPause();

        //Clear the observer.
        mViewModel.removeJokeObserver(mJokeObserver);
        mJokeObserver = null;
    }

    /**
     * Set the onClickListener for the 'Tell Joke' button
     */
    public void setClickListener() {
        //Set the click listener to tell the ViewModel to retrieve a new joke.
        mButtonTellJoke.setOnClickListener(v -> beginObserving());
    }

    /**
     * Clears the view state for the loading and error indicator views.
     */
    public void clearViewState() {
        setProgressBarVisible(false);
        setErrorMessageVisible(false);
    }

    /**
     * Sets the progress bar visbility.
     *
     * @param visible whether should be visible or not.
     */
    public void setProgressBarVisible(boolean visible) {
        if (visible) mProgressBar.setVisibility(View.VISIBLE);
        else mProgressBar.setVisibility(View.INVISIBLE);
    }

    /**
     * Sets the error message visbility.
     *
     * @param visible whether should be visible or not.
     */
    public void setErrorMessageVisible(boolean visible) {
        if (visible) mTVStatusMessage.setVisibility(View.VISIBLE);
        else mTVStatusMessage.setVisibility(View.INVISIBLE);
    }

    /**
     * Start observing the data in the ViewModel.
     */
    private void beginObserving() {
        setErrorMessageVisible(false);
        setProgressBarVisible(true);

        if (mViewModel.jokeStatus() == JokeData.STATUS_ERROR) mViewModel.initNextJoke();

        //Clear observer
        if (mJokeObserver != null) {
            mViewModel.removeJokeObserver(mJokeObserver);
            mJokeObserver = null;
        }

        //Ensure that network is available.
        try {
            if (getContext() == null) throw new NullPointerException();
            boolean networkAvailable = AppUtils.isNetworkAvailable(getContext());
            if (!networkAvailable) {
                displayErrorSnackbar(getString(R.string.no_network));
                return;
            }
        } catch (NullPointerException e) {
            displayErrorSnackbar(getString(R.string.fatal_error));
            return;
        }

        //Call tellJoke when the current joke changes.
        mJokeObserver = this::tellJoke;

        //Add observer to the view model.
        mViewModel.addJokeObserver(this, mJokeObserver);

        if (IdlingResourceSingleton.isActive())
            IdlingResourceSingleton.getInstance().isNotIdle();
    }

    /**
     * Display a snackbar with an error message.
     *
     * @param message The message to display.
     */
    private void displayErrorSnackbar(String message) {
        setProgressBarVisible(false);
        setErrorMessageVisible(true);
        AppUtils.summonSnackbarSelfClosing(getView(), message);
    }

    /**
     * Handle if an error joke was received and determine what actions to take.
     */
    public void handleJokeError() {
        try {
            if (getContext() == null) throw new NullPointerException();
            boolean networkAvailable = AppUtils.isNetworkAvailable(getContext());
            if (!networkAvailable) {
                displayErrorSnackbar(getString(R.string.no_network));
                return;
            }
        } catch (NullPointerException e) {
            displayErrorSnackbar(getString(R.string.fatal_error));
            return;
        }
        displayErrorSnackbar(getString(R.string.error_retrieving_joke));
    }

    /**
     * Determines whether to tell joke or not and then displays it.
     *
     * @param jokeData The data for the joke.
     */
    public void tellJoke(JokeData jokeData) {
        if (IdlingResourceSingleton.isActive())
            IdlingResourceSingleton.getInstance().isIdle();

        //Check if data is valid.
        if (jokeData == null || jokeData.statusCode == JokeData.STATUS_EMPTY) return;
        if (jokeData.statusCode == JokeData.STATUS_ERROR) {
            handleJokeError();
            return;
        }
        //Build the message to pass to the Activity.
        StringBuilder sb = new StringBuilder(jokeData.jokeBody);
        if (jokeData.optFollowup != null && jokeData.optFollowup.length() > 0)
            sb.append("\n\n").append(jokeData.optFollowup);

        //Create and launch the intent.
        Intent displayThingActivity = new Intent(getContext(), DisplayThingActivity.class);
        displayThingActivity.putExtra(DisplayThingActivity.DISPLAY_THING_STRING_EXTRA, sb.toString());

        startActivityForResult(displayThingActivity, DISPLAY_THING_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Make sure the Activity successfully received a joke.
        if (requestCode == DISPLAY_THING_REQUEST_CODE && resultCode == Activity.RESULT_CANCELED)
            displayErrorSnackbar(getString(R.string.error_displaying_joke));
    }
}
