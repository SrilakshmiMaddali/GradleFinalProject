package com.udacity.gradle.builditbigger.repository;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.stevenberdak.jokefountain.Models.JokeData;
import com.udacity.gradle.builditbigger.AppUtils;
import com.udacity.gradle.builditbigger.R;

public class MainRepository {

    public static final String BROADCAST_IN_JOKE = "com.udacity.gradle.builditbigger.BROADCAST_IN_JOKE";
    public static final String BUNDLE_KEY_JOKE_BODY = "joke_body";
    public static final String BUNDLE_KEY_JOKE_OPT_FOLLOWUP = "joke_followup";
    public static final String BUNDLE_KEY_JOKE_STATUS_CODE = "joke_status";
    private static final int NEXT_JOKE_SERVICE_ID = 1357908;
    private BroadcastReceiver mBroadcastReceiver;
    private IntentFilter mIntentFilter;
    private MutableLiveData<JokeData> mCurrentJoke;

    public MainRepository() {
        mCurrentJoke = new MutableLiveData<>();
    }

    public void registerReceiver(Context ctx) {
        if (mIntentFilter == null) {
            mIntentFilter = new IntentFilter();
            mIntentFilter.addAction(BROADCAST_IN_JOKE);
        }
        if (mBroadcastReceiver == null) mBroadcastReceiver = new MainRepositoryBroadcastReceiver();
        ctx.registerReceiver(mBroadcastReceiver, mIntentFilter);
    }

    public void deregisterReceiver(Context ctx) {
        ctx.unregisterReceiver(mBroadcastReceiver);
    }

    public void registerJokeObserver(Observer<JokeData> observer) {
        mCurrentJoke.observeForever(observer);
    }

    public void unregisterJokeObserver(Observer<JokeData> observer) {
        mCurrentJoke.removeObserver(observer);
    }

    public void getNextJoke(Context ctx) {
        mCurrentJoke.setValue(null);
        NextJokeService.enqueueWork(ctx, NextJokeService.class, NEXT_JOKE_SERVICE_ID, new Intent());
    }

    private class MainRepositoryBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == null) return;

            if (intent.getAction().equals(BROADCAST_IN_JOKE)) {
                String jokeBody = intent.getStringExtra(BUNDLE_KEY_JOKE_BODY);
                String jokeFollowUp = intent.getStringExtra(BUNDLE_KEY_JOKE_OPT_FOLLOWUP);
                int jokeStatusCode = intent.getIntExtra(BUNDLE_KEY_JOKE_STATUS_CODE, JokeData.STATUS_ERROR);
                mCurrentJoke.setValue(new JokeData(jokeBody, jokeFollowUp, jokeStatusCode));
            } else {
                AppUtils.makeNormalToast(context, context.getString(R.string.error_retrieving_joke_from_source));
            }
        }
    }
}
