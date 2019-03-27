package com.onepointsolution.onemarketplace.rest;

import com.onepointsolution.onemarketplace.model.ArticlesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {
    @GET("/v2/top-headlines")
    Call<ArticlesResponse> getTopHeadLines(@Query("country") String country, @Query("apiKey") String apiKey);

    @GET("/v2/everything")
    Call<ArticlesResponse> getEverything(@Query("apiKey") String apiKey);

    @GET("/v2/sources")
    Call<ArticlesResponse> getSources(@Query("apiKey") String apiKey);
}
