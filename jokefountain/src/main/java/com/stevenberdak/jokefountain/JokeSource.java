package com.stevenberdak.jokefountain;

import com.stevenberdak.jokefountain.Models.JokeData;

public class JokeSource {

    private DownloadSeed downloadSeed;

    public JokeSource() {

    }

    public JokeSource(DownloadSeed downloadSeed) {
        this.downloadSeed = downloadSeed;
    }

    public JokeData nextJoke(DownloadSeed downloadSeed) {
        this.downloadSeed = downloadSeed;
        return nextJoke();
    }

    public JokeData nextJoke() {
        return downloadSeed.getData();
    }
}
