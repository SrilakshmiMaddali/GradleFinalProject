package com.udacity.gradle.builditbigger.viewmodels;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;

import com.udacity.gradle.builditbigger.models.JokeData;

public interface MainViewModelInterface {

    void addJokeObserver(LifecycleOwner owner, Observer<JokeData> observer);

    void removeJokeObserver(Observer<JokeData> observer);

    void initNextJoke();

    int jokeStatus();
}
