package com.stevenberdak.jokefountain.repos.ichdj;

import com.stevenberdak.jokefountain.models.JokeData;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public class IchdjDownloadSeed implements com.stevenberdak.jokefountain.repos.DownloadSeed {

    private static final String BASE_URL = "https://icanhazdadjoke.com/";
    private Retrofit retroFit;

    public IchdjDownloadSeed() {

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

    /**
     * An interface used by Retrofit the link the response to the pojo.
     */
    public interface IcanhazdadjokeService {
        @Headers({
                "Accept: application/json",
                "User-Agent: UdacityANDProject5, stevenberdak@gmail.com"
        })
        @GET(".")
        Call<IcanhazdadjokeResponseModel> jokes();
    }
}
