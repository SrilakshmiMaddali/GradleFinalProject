package com.udacity.gradle.builditbigger.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.stevenberdak.jokefountain.Models.Joke;
import com.udacity.gradle.builditbigger.AppUtils;
import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.viewmodels.MainViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    @BindView(R.id.button_tell_joke)
    Button mButtonTellJoke;

    MainViewModel mViewModel;
    Observer<Joke> mJokeObserver;

    public MainActivityFragment() {
    }

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
    public void onResume() {
        super.onResume();
        if (getActivity() != null && mViewModel == null)
            mViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        else
            errorSnackbar();

        if (mJokeObserver == null) mJokeObserver = joke -> {
            if (joke != null) {
                if (!joke.jokeBody.equals(MainViewModel.JOKE_NEGATED))
                    tellJoke(joke);
            } else
                errorSnackbar();
        };

        //Call tellJoke when the current joke changes.
        mViewModel.registerJokeObserver(this, mJokeObserver);
    }

    @Override
    public void onPause() {
        super.onPause();
        mViewModel.unregisterJokeObserver(this, mJokeObserver);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        //Set the click listener to tell the ViewModel to retrieve a new joke.
        mButtonTellJoke.setOnClickListener(v -> mViewModel.nextJoke());
    }

    private void errorSnackbar() {
        AppUtils.summonSnackbar(getView(), getString(R.string.go_back), v -> {
            if (getFragmentManager() != null)
                getFragmentManager().popBackStack();
        });
    }

    public void tellJoke(Joke joke) {
        AppUtils.makeNormalToast(getContext(), joke.jokeBody);
        if (joke.optFollowup != null && joke.optFollowup.length() > 0)
            AppUtils.makeNormalToast(getContext(), joke.optFollowup);
    }
}
