package com.stevenberdak.jokefountain;

import com.stevenberdak.jokefountain.Models.Joke;

import java.util.ArrayList;

public class JokeSource {

    private static JokeSource mInstance;

    private JokeSource() {

    }

    public Joke nextJoke(DownloadSeed downloadSeed) {
        return downloadSeed.getData();
    }

    public static JokeSource getmInstance() {
        if (mInstance == null ) mInstance = new JokeSource();
        return mInstance;
    }
}
