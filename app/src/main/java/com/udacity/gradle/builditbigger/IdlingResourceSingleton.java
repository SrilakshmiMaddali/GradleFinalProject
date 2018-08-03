package com.udacity.gradle.builditbigger;

import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.idling.CountingIdlingResource;

public class IdlingResourceSingleton {

    private static IdlingResourceSingleton mInstance;
    private CountingIdlingResource mCountingIdlingResource;

    private IdlingResourceSingleton() {
        mCountingIdlingResource = new CountingIdlingResource("IdlingResourceSingleton");
    }

    public static IdlingResourceSingleton getInstance() {
        if (mInstance == null) mInstance = new IdlingResourceSingleton();
        return mInstance;
    }

    public static boolean isActive() {
        return mInstance == null;
    }

    public void isNotIdle() {
        if (mCountingIdlingResource.isIdleNow())
            mCountingIdlingResource.increment();
    }

    public void isIdle() {
        if (!mCountingIdlingResource.isIdleNow())
            mCountingIdlingResource.decrement();
    }

    public IdlingResource getIdlingResource() {
        return mCountingIdlingResource;
    }
}
