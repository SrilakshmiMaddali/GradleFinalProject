package com.udacity.gradle.builditbigger.repository;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.util.Log;

import com.stevenberdak.jokefountain.JokeSource;
import com.stevenberdak.jokefountain.Models.Joke;

import icanhazdadjoke.IcanhazdadjokeDownloadSeed;

public class NextJokeService extends JobIntentService {
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        JokeSource source = JokeSource.getmInstance();

        Joke result = source.nextJoke(new IcanhazdadjokeDownloadSeed());

        Intent outIntent = new Intent(MainRepository.BROADCAST_IN_JOKE);
        outIntent.putExtra(MainRepository.BUNDLE_KEY_JOKE_BODY, result.jokeBody);
        outIntent.putExtra(MainRepository.BUNDLE_KEY_JOKE_OPT_FOLLOWUP, result.optFollowup);
        sendBroadcast(outIntent);
    }
}
