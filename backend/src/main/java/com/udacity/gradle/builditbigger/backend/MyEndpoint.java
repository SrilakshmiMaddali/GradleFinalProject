package com.udacity.gradle.builditbigger.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.stevenberdak.jokefountain.Icanhazdadjoke.IcanhazdadjokeDownloadSeed;
import com.stevenberdak.jokefountain.JokeSource;
import com.stevenberdak.jokefountain.Models.JokeData;

import javax.inject.Named;

/** An endpoint class we are exposing */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.gradle.udacity.com",
                ownerName = "backend.builditbigger.gradle.udacity.com",
                packagePath = ""
        )
)
public class MyEndpoint {

//    private JokeSource source = new JokeSource(new IcanhazdadjokeDownloadSeed());

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String name) {
        MyBean response = new MyBean();
        response.setData("Hi, " + name);

        return response;
    }

    @ApiMethod(name = "getJoke")
    public JokeBean getJoke() {
        JokeSource jokeSource = new JokeSource();

        JokeData data = jokeSource.nextJoke(new IcanhazdadjokeDownloadSeed());

        JokeBean response = new JokeBean();
        response.setJokeBody(data.jokeBody);
        response.setJokeFollowUp(data.optFollowup);
        response.setJokeStatus(data.statusCode);

        return response;
    }
}
