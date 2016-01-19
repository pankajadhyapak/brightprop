package com.w3effects.brightprop.Api;

import com.w3effects.brightprop.Models.Media;
import com.w3effects.brightprop.Models.Property;
import com.w3effects.brightprop.Models.PropertyLists;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by pankaj on 14/01/16.
 */
public interface PropertyApi {

    @GET("property-api")
    Call<List<Property>> loadQuestions(@Query("per_page") Integer per_page);

    @GET("media/{mediaId}")
    Call<Media> loadMedia(@Path("mediaId") Integer mediaId);
}
