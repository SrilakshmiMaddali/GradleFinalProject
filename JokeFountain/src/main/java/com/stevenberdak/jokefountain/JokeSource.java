package com.stevenberdak.jokefountain;

import com.stevenberdak.jokefountain.Models.Joke;

public class JokeSource {

    private static JokeSource instance;
    private DownloadSeed downloadSeed;

    public JokeSource() {

    }

    public JokeSource(DownloadSeed downloadSeed) {
        this.downloadSeed = downloadSeed;
    }

    public Joke nextJoke(DownloadSeed downloadSeed) {
        this.downloadSeed = downloadSeed;
        return nextJoke();
    }

    public Joke nextJoke() {
        return downloadSeed.getData();
    }

    public static JokeSource getInstance() {
        if (instance == null ) instance = new JokeSource();
        return instance;
    }
}
