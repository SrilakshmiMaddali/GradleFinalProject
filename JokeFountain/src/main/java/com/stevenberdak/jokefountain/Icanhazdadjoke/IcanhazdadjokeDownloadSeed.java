package com.stevenberdak.jokefountain.Icanhazdadjoke;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.stevenberdak.jokefountain.DownloadSeed;
import com.stevenberdak.jokefountain.LibStrings;
import com.stevenberdak.jokefountain.Models.Joke;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public class IcanhazdadjokeDownloadSeed implements DownloadSeed {

    private static final String BASE_URL = "https://icanhazdadjoke.com/";
    Moshi moshi;
    JsonAdapter<IcanhazdadjokeResponseModel> adapter;
    Retrofit retroFit;

    public IcanhazdadjokeDownloadSeed() {
        moshi = new Moshi.Builder().build();

        adapter = moshi.adapter(IcanhazdadjokeResponseModel.class);

        retroFit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
    }

    @Override
    public Joke getData() {
        IcanhazdadjokeService service = retroFit.create(IcanhazdadjokeService.class);

        Call<IcanhazdadjokeResponseModel> callIchdjResponseMode = service.jokes();

        IcanhazdadjokeResponseModel response = null;
        try {
            response = callIchdjResponseMode.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response.status == 200)
            return new Joke(response.joke, null);
        else
            return new Joke(LibStrings.JOKE_ERROR, null);
    }

    public interface IcanhazdadjokeService {
        @Headers({
                "Accept: application/json",
                "User-Agent: UdacityANDProject5, stevenberdak@gmail.com"
        })
        @GET(".")
        Call<IcanhazdadjokeResponseModel> jokes();
    }
}
