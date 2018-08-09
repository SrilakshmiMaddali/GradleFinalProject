package com.udacity.gradle.builditbigger.repository;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.udacity.gradle.builditbigger.IdlingResourceSingleton;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;
import com.udacity.gradle.builditbigger.backend.myApi.model.JokeBean;
import com.udacity.gradle.builditbigger.models.JokeData;

import java.io.IOException;

public class MainRepository implements MainRepositoryInterface {

    private static MainRepository mInstance;
    private static final MutableLiveData<JokeData> mJokeData = new MutableLiveData<>();
    private static String apiIpAddress = "10.0.2.2:8080";
    private static int statusCode;

    private MainRepository() {

    }

    public static MainRepository getInstance() {
        if (null == mInstance) mInstance = new MainRepository();
        return mInstance;
    }

    @Override
    public void addObserver(Observer<JokeData> observer) {
        mJokeData.observeForever(observer);
    }

    @Override
    public void removeObserver(Observer<JokeData> observer) {
        mJokeData.removeObserver(observer);
    }

    @Override
    public void initNextJoke() {
        new FetchJokeAsyncTask().execute();
        if (IdlingResourceSingleton.isActive())
            IdlingResourceSingleton.getInstance().plusOne();
    }

    /**
     * Fetches a joke from the endpoint and sets the value within the repository.
     */
    private static class FetchJokeAsyncTask extends AsyncTask<Void, Void, JokeData> {

        private static MyApi myApiService = null;

        @Override
        protected JokeData doInBackground(Void... voids) {
            if (myApiService == null) {
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("http://" + apiIpAddress + "/_ah/api/")
                        .setGoogleClientRequestInitializer(abstractGoogleClientRequest -> abstractGoogleClientRequest.setDisableGZipContent(true));
                myApiService = builder.build();
            }

            try {
                JokeBean bean = myApiService.getJoke().execute();
                statusCode = myApiService.getJoke().getLastStatusCode();
                return new JokeData(bean.getJokeBody(),
                        bean.getJokeFollowUp(),
                        bean.getJokeStatus());
            } catch (IOException e) {
                e.printStackTrace();
                return new JokeData(null, null, JokeData.STATUS_ERROR);
            }
        }

        @Override
        protected void onPostExecute(JokeData jokeData) {
            Log.v(getClass().getSimpleName(), "Joke data received, status code = " + jokeData.statusCode);
            mJokeData.setValue(jokeData);
            if (IdlingResourceSingleton.isActive())
                IdlingResourceSingleton.getInstance().minusOne();
        }
    }
}
