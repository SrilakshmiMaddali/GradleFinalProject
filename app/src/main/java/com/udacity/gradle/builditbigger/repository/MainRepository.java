package com.udacity.gradle.builditbigger.repository;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.stevenberdak.jokefountain.Models.Joke;
import com.udacity.gradle.builditbigger.AppUtils;
import com.udacity.gradle.builditbigger.R;

public class MainRepository {

    public static final String BROADCAST_IN_JOKE = "com.udacity.gradle.builditbigger.BROADCAST_IN_JOKE";
    public static final String BUNDLE_KEY_JOKE_BODY = "joke_body";
    public static final String BUNDLE_KEY_JOKE_OPT_FOLLOWUP = "joke_followup";
    private BroadcastReceiver mBroadcastReceiver;
    private IntentFilter mIntentFilter;
    private MutableLiveData<Joke> mCurrentJoke, mCachedJoke;

    public MainRepository() {
        mCurrentJoke = new MutableLiveData<>();
        mCachedJoke = new MutableLiveData<>();
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

    public void registerJokeObserver(Observer<Joke> observer) {
        mCurrentJoke.observeForever(observer);
    }

    public void unregisterJokeObserver(Observer<Joke> observer) {
        mCurrentJoke.removeObserver(observer);
    }

    public void getNextJoke(Context ctx) {
        mCurrentJoke.setValue(mCachedJoke.getValue());
        NextJokeService.enqueueWork(ctx, NextJokeService.class, 1357908, new Intent());
    }

    public void initJokeRepository(Context ctx) {
        NextJokeService.enqueueWork(ctx, NextJokeService.class, 1357908, new Intent());
    }

    private class MainRepositoryBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null)
                if (intent.getAction().equals(BROADCAST_IN_JOKE)) {
                    String jokeBody = intent.getStringExtra(BUNDLE_KEY_JOKE_BODY);
                    if (jokeBody == null) jokeBody = context.getString(R.string.error_joke);
                    String jokeFollowUp = intent.getStringExtra(BUNDLE_KEY_JOKE_OPT_FOLLOWUP);
                    if (jokeFollowUp == null) jokeFollowUp = "";
                    mCachedJoke.setValue(new Joke(jokeBody, jokeFollowUp));
                } else {
                    AppUtils.makeNormalToast(context, context.getString(R.string.error_joke));
                }
        }
    }
}
