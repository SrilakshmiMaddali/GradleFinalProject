package com.udacity.gradle.builditbigger.backend;

public class JokeBean {

    private String jokeBody, jokeFollowUp;
    private int jokeStatus;

    public String getJokeBody() {
        return jokeBody;
    }

    public void setJokeBody(String jokeBody) {
        this.jokeBody = jokeBody;
    }

    public String getJokeFollowUp() {
        return jokeFollowUp;
    }

    public void setJokeFollowUp(String jokeFollowUp) {
        this.jokeFollowUp = jokeFollowUp;
    }

    public int getJokeStatus() {
        return jokeStatus;
    }

    public void setJokeStatus(int jokeStatus) {
        this.jokeStatus = jokeStatus;
    }
}
