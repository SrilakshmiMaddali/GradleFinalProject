package com.udacity.gradle.builditbigger.repository;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;

import com.stevenberdak.jokefountain.JokeSource;
import com.stevenberdak.jokefountain.Models.JokeData;

import com.stevenberdak.jokefountain.Icanhazdadjoke.IcanhazdadjokeDownloadSeed;

public class NextJokeService extends JobIntentService {
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        JokeSource source = JokeSource.getInstance();

        JokeData result = source.nextJoke(new IcanhazdadjokeDownloadSeed());

        Intent outIntent = new Intent(MainRepository.BROADCAST_IN_JOKE);
        outIntent.putExtra(MainRepository.BUNDLE_KEY_JOKE_BODY, result.jokeBody);
        outIntent.putExtra(MainRepository.BUNDLE_KEY_JOKE_OPT_FOLLOWUP, result.optFollowup);
        outIntent.putExtra(MainRepository.BUNDLE_KEY_JOKE_STATUS_CODE, result.statusCode);
        sendBroadcast(outIntent);
    }
}
