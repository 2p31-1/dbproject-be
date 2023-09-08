package com.nettopulikkusu.DTO;

import java.util.List;

public class titleDetailDTO {

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public List<String> getMovieGenre() {
        return movieGenre;
    }

    public void setMovieGenre(List<String> movieGenre) {
        this.movieGenre = movieGenre;
    }

    public double getRatingScore() {
        return ratingScore;
    }

    public void setRatingScore(double ratingScore) {
        this.ratingScore = ratingScore;
    }

    public double getMyRatingScore() {
        return myRatingScore;
    }

    public void setMyRatingScore(double myRatingScore) {
        this.myRatingScore = myRatingScore;
    }

    int movieId=0;
    String movieTitle="";
    List<String> movieGenre;
    double ratingScore=0;
    double myRatingScore=0;
}
