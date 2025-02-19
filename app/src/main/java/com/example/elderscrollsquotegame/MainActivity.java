package com.example.elderscrollsquotegame;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.graphics.Typeface;
import android.util.Log;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView quoteTextView;
    private Button generateButton;
    private TextView titleTextView;
    private QuoteService service;
    private List<Quote> quotes = new ArrayList<>();
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        quoteTextView = findViewById(R.id.quoteTextView);
        generateButton = findViewById(R.id.generateButton);
        titleTextView = findViewById(R.id.titleTextView);
        // Set up Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://elder-scrolls-quote-generator-api.onrender.com/")  // Render's base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(QuoteService.class);
        // Fetch all Quotes from the API
        fetchAllQuotes();

        // Button click listener to fetch a new quote
        generateButton.setOnClickListener(view -> {
            // Start button press animation
            view.animate()
                            .scaleX(0.85f) // Scale down to 85%
                            .scaleY(0.85f)
                            .setDuration(100) // Animation duration (100ms)
                            .withEndAction(() -> {
                                // Reverse animation to original size
                                view.animate().scaleX(1f).scaleY(1f).setDuration(100);
                                // Show random quote after animation
                                showNextQuote();
                            })
                            .start();
        });
    }

    private void fetchAllQuotes() {
        service.getAllQuotes().enqueue(new Callback<List<Quote>>() {
            @Override
            public void onResponse(@NonNull Call<List<Quote>> call, @NonNull Response<List<Quote>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    quotes = response.body();
                    shuffleQuotes();
                    showNextQuote();
                } else {
                    quoteTextView.setText("Failed to load quotes");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Quote>> call, @NonNull Throwable t) {
                Log.e("MainActivity", "Error fetching quotes", t);
                quoteTextView.setText("Error fetching quotes");
            }
        });
    }

    private void shuffleQuotes() {
        Collections.shuffle(quotes); // Randomize quote order
        currentIndex = 0; // Reset index
    }

private void showNextQuote() {
    if (quotes.isEmpty()) {
        quoteTextView.setText("No quotes available");
        return;
    }

    // Display the quote using the toString() method
    quoteTextView.setText(quotes.get(currentIndex).toString());

    // Move to the next quote
    currentIndex++;

    // If all quotes have been shown, reshuffle
    if (currentIndex >= quotes.size()) {
        shuffleQuotes();
    }
}
}

