package com.udacity.gradle.builditbigger.models;

public class JokeData {

    public final String jokeBody, optFollowup;
    public final int statusCode;
    public static final int STATUS_EMPTY = 0;
    public static final int STATUS_ERROR = 1;
    public static final int STATUS_OK = 2;

    public JokeData(String body, String optResponse, int statusCode) {
        this.jokeBody = body;
        this.optFollowup = optResponse;
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "JokeData{" +
                "jokeBody='" + jokeBody + '\'' +
                ", optFollowup='" + optFollowup + '\'' +
                '}';
    }
}
