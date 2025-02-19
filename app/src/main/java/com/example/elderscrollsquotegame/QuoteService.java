package com.example.elderscrollsquotegame;
import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;
public interface QuoteService {
    @GET("/quote/random")
    Call<Quote> getRandomQuote();
    @GET("/quotes")  // Add an endpoint to fetch all quotes
    Call<List<Quote>> getAllQuotes();
}
