package com.example.elderscrollsquotegame;

import androidx.annotation.NonNull;

public class Quote {
    private String text;
    private String source;
    private String game;

    // Constructor
    public Quote (String text, String source, String game) {
        this.text = text;
        this.source = source;
        this.game = game;
    }
    // Get functions
    public String getText() {
        return text;
    }

    public String getSource() {
        return source;
    }

    public String getGame() {
        return game;
    }
    // Set functions
    //public void setText(String text) {
        //this.text = text;
    //}

    //public void setSource(String source) {
        //this.source = source;
    //}

    //public void setGame(String game) {
        //this.game = game;
    //}

    @NonNull
    @Override
    public String toString() {
        //return "\"" + text + "\" - " + source + " (" + game + ")";
        return "Text: \"" + text + "\"\nSource: \"" + source + "\"\nGame: \"" + game + "\"";
    }
}
