package com.example.elderscrollsquotegame;
import retrofit2.Call;
import retrofit2.http.GET;
public interface QuoteService {
    @GET("/quote/random")
    Call<Quote> getRandomQuote();
}
