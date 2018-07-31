package com.stevenberdak.jokefountain.Models;

public class Joke {

    public String jokeBody, optFollowup;

    public Joke(String body, String optResponse) {
        this.jokeBody = body;
        this.optFollowup = optResponse == null ? "" : optResponse;
    }

    @Override
    public String toString() {
        return "Joke{" +
                "jokeBody='" + jokeBody + '\'' +
                ", optFollowup='" + optFollowup + '\'' +
                '}';
    }
}
