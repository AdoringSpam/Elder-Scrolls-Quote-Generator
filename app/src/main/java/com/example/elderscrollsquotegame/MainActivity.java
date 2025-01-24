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
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView quoteTextView;
    private Button generateButton;
    private QuoteService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        //ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
        //Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
        //v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
        //return insets;
        //});
        quoteTextView = findViewById(R.id.quoteTextView);
        generateButton = findViewById(R.id.generateButton);
        // Set up Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("http://localhost:3000/")  // Replace with your Node.js API URL
                .baseUrl("http://10.0.2.2:3000/")  // Use 10.0.2.2 for emulator access
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(QuoteService.class);
        // Initial quote fetch
        fetchRandomQuote();

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
                                // Fetch random quote after animation
                                fetchRandomQuote();
                            })
                            .start();
        });
    }

    private void fetchRandomQuote() {
        service.getRandomQuote().enqueue(new Callback<Quote>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<Quote> call, @NonNull Response<Quote> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Quote quote = response.body();
                    quoteTextView.setText(quote.toString());
                } else {
                    quoteTextView.setText("Failed to get quote");
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<Quote> call, @NonNull Throwable t) {
                Log.e("MainActivity", "Error fetching quote", t);
                quoteTextView.setText("Error fetching quote");
            }
        });
    }
}

