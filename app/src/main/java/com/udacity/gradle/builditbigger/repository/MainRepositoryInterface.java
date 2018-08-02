package com.udacity.gradle.builditbigger.repository;

import android.arch.lifecycle.Observer;

import com.udacity.gradle.builditbigger.models.JokeData;

public interface MainRepositoryInterface {

    void addObserver(Observer<JokeData> observer);

    void removeObserver(Observer<JokeData> observer);

    void initNextJoke();
}
