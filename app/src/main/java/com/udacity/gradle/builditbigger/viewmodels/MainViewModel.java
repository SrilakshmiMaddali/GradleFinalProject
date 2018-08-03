package com.udacity.gradle.builditbigger.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;

import com.udacity.gradle.builditbigger.models.JokeData;
import com.udacity.gradle.builditbigger.repository.MainRepository;

public class MainViewModel extends AndroidViewModel implements MainViewModelInterface {

    private final MainRepository mRepository;
    private final MutableLiveData<JokeData> mJokeData;
    private final Observer<JokeData> mRepositoryJokeObserver;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.mRepository = MainRepository.getInstance();
        mJokeData = new MutableLiveData<>();
        mRepositoryJokeObserver = mJokeData::setValue;
        mRepository.addObserver(mRepositoryJokeObserver);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mRepository.removeObserver(mRepositoryJokeObserver);
    }

    @Override
    public void addJokeObserver(LifecycleOwner owner, Observer<JokeData> observer) {
        mJokeData.observe(owner, observer);
    }

    @Override
    public void removeJokeObserver(Observer<JokeData> observer) {
        mJokeData.removeObserver(observer);
    }

    @Override
    public void initNextJoke() {
        mRepository.initNextJoke();
    }

    @Override
    public int jokeStatus() {
        if (mJokeData.getValue() != null)
            return mJokeData.getValue().statusCode;
        else return JokeData.STATUS_EMPTY;
    }
}
