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

import com.stevenberdak.jokefountain.Models.Joke;
import com.udacity.gradle.builditbigger.AppUtils;
import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.repository.MainRepository;

public class MainViewModel extends AndroidViewModel implements LifecycleObserver {

    public static final String JOKE_NEGATED = "joke_negated";
    private MainRepository mRepository;
    private MutableLiveData<Joke> mJoke;
    private Observer<Joke> mJokeObserver;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.mRepository = new MainRepository();
        mRepository.initJokeRepository(getApplication());
        mJoke = new MutableLiveData<>();
        mJoke.setValue(new Joke(JOKE_NEGATED, null));
    }

    public void nextJoke() {
        mRepository.getNextJoke(getApplication());
    }

    public void registerJokeObserver(LifecycleOwner owner, Observer<Joke> observer) {
        owner.getLifecycle().addObserver(this);
        mJoke.observe(owner, observer);

        if (mJokeObserver == null) mJokeObserver = joke -> {
            
            if (joke == null)
                AppUtils.makeNormalToast(getApplication(), getApplication().getString(R.string.error_joke));

            else mJoke.setValue(joke);
        };

        mRepository.registerJokeObserver(mJokeObserver);
    }

    public void unregisterJokeObserver(LifecycleOwner owner, Observer<Joke> observer) {
        owner.getLifecycle().removeObserver(this);
        mJoke.removeObserver(observer);
        mRepository.unregisterJokeObserver(observer);
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
