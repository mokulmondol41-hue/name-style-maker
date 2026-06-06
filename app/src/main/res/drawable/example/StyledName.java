package com.example;

public class StyledName {
    private String styledText;
    private boolean isFavorite;

    public StyledName(String styledText, boolean isFavorite) {
        this.styledText = styledText;
        this.isFavorite = isFavorite;
    }

    public String getStyledText() {
        return styledText;
    }

    public void setStyledText(String styledText) {
        this.styledText = styledText;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
