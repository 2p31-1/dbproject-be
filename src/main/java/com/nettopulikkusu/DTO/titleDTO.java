package com.nettopulikkusu.DTO;


import java.util.ArrayList;
import java.util.List;

public class titleDTO {

  int movieId=0;
  String movieTitle="";

  public List<String> getMovieGenre() {
    return movieGenre;
  }

  public void setMovieGenre(List<String> movieGenre) {
    this.movieGenre = movieGenre;
  }

  List<String> movieGenre=new ArrayList<>();
  double ratingScore=0;
  String IMDbUrl="";

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


  public double getRatingScore() {
    return ratingScore;
  }

  public void setRatingScore(double ratingScore) {
    this.ratingScore = ratingScore;
  }

  public String getIMDbUrl() {
    return IMDbUrl;
  }

  public void setIMDbUrl(String imgUrl) {
    this.IMDbUrl = imgUrl;
  }

}
