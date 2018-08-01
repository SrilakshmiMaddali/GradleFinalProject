package com.udacity.gradle.builditbigger.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;

import com.stevenberdak.jokefountain.Models.JokeData;
import com.udacity.gradle.builditbigger.repository.MainRepository;

public class MainViewModel extends AndroidViewModel implements LifecycleObserver {

    private MainRepository mRepository;
    private MutableLiveData<JokeData> mJokeData;
    private Observer<JokeData> mJokeObserver;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.mRepository = new MainRepository();
        mJokeData = new MutableLiveData<>();
        if (mJokeObserver == null) {
            mJokeObserver = jokeData -> mJokeData.setValue(jokeData);
        }
        mRepository.registerJokeObserver(mJokeObserver);
    }

    public void nextJoke() {
        mRepository.getNextJoke(getApplication());
    }

    public void registerJokeObserver(LifecycleOwner owner, Observer<JokeData> observer) {
        mJokeData.observe(owner, observer);
    }

    public void unregisterJokeObserver(Observer<JokeData> observer) {
        mJokeData.removeObserver(observer);
        mRepository.unregisterJokeObserver(observer);
    }

    public void registerLifecycleOwner(LifecycleOwner owner) {
        owner.getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void registerReceiver() {
        mRepository.registerReceiver(getApplication());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void deregisterReceiver() {
        mRepository.deregisterReceiver(getApplication());
    }
}
