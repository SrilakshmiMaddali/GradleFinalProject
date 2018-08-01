package com.stevenberdak.jokefountain.Icanhazdadjoke;

import com.stevenberdak.jokefountain.DownloadSeed;
import com.stevenberdak.jokefountain.Models.JokeData;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public class IcanhazdadjokeDownloadSeed implements DownloadSeed {

    private static final String BASE_URL = "https://icanhazdadjoke.com/";
    private Retrofit retroFit;

    public IcanhazdadjokeDownloadSeed() {
        retroFit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
    }

    @Override
    public JokeData getData() {
        IcanhazdadjokeService service = retroFit.create(IcanhazdadjokeService.class);

        Call<IcanhazdadjokeResponseModel> call = service.jokes();

        IcanhazdadjokeResponseModel response = null;
        try {
            response = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response != null && response.status == 200)
            return new JokeData(response.joke, null, JokeData.STATUS_OK);
        else
            return new JokeData(null, null, JokeData.STATUS_ERROR);
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
