package com.udacity.gradle.builditbigger.ui.repository;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.udacity.gradle.builditbigger.IdlingResourceSingleton;
import com.udacity.gradle.builditbigger.models.JokeData;
import com.udacity.gradle.builditbigger.repository.MainRepository;
import com.udacity.gradle.builditbigger.ui.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainRepositoryTest {

    private final IdlingResource mIdlingResource = IdlingResourceSingleton.getInstance().getIdlingResource();

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource() {
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }

    @Test
    public void testAsyncTask_fetchesData() {
        MutableLiveData<JokeData> jokeDataMutableLiveData = new MutableLiveData<>();
        Observer<JokeData> jokeDataObserver = jokeData -> jokeDataMutableLiveData.setValue(jokeData);
        MainRepository.getInstance().addObserver(jokeDataObserver);
        MainRepository.getInstance().initNextJoke();

        assert (jokeDataMutableLiveData.getValue() != null);
    }
}
