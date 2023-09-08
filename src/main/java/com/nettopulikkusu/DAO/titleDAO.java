package com.nettopulikkusu.DAO;

import com.nettopulikkusu.DTO.ratingDTO;
import com.nettopulikkusu.DTO.titleDTO;
import com.nettopulikkusu.DTO.titleDetailDTO;
import com.nettopulikkusu.DTO.userDTO;

import java.util.ArrayList;
import java.util.List;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class titleDAO {

  @Autowired
  SqlSessionTemplate sqlSession;

  String namespace="com.nettopulikkusu";

  public List<titleDTO> selectMovie(String userId){
    List<titleDTO> titleDTOList=null;

    titleDTOList=sqlSession.selectList(namespace+".selectMovie",userId);

    return titleDTOList;
  }

  public List<titleDTO> selectOccupationMovie(String userId){
    List<titleDTO> titleDTOList=null;

    titleDTOList=sqlSession.selectList(namespace+".selectOccupationMovie",userId);

    return titleDTOList;
  }

  public List<titleDTO> selectAgeMovie(String userId){
    List<titleDTO> titleDTOList=null;

    titleDTOList=sqlSession.selectList(namespace+".selectAgeMovie",userId);

    return titleDTOList;
  }

  public List<ratingDTO> selectRatings(){
    List<ratingDTO> ratingDTOList=null;

    ratingDTOList=sqlSession.selectList(namespace+".selectAllRatings");

    return ratingDTOList;
  }

  public userDTO selectUser(String userId){

    userDTO nowUser=null;

    nowUser=sqlSession.selectOne(namespace+".selectUser",userId);

    return nowUser;
  }

  public List<titleDTO> selectMovieData(List<Integer> movieIdList){
    List<titleDTO> titleDTOList=null;

    titleDTOList=sqlSession.selectList(namespace+".selectMovieData", movieIdList);

    return titleDTOList;
  }

  public titleDetailDTO selectMovieDetail(String userId, String movieId) {
    titleDetailDTO movieDetail=null;
    List<String> genres=null;
    class Param{
      private String userId,movieId;

      public String getUserId() {
        return userId;
      }

      public void setUserId(String userId) {
        this.userId = userId;
      }

      public String getMovieId() {
        return movieId;
      }

      public void setMovieId(String movieId) {
        this.movieId = movieId;
      }
    }
    Param param = new Param();
    param.setMovieId(movieId);
    param.setUserId(userId);
    genres=sqlSession.selectList(namespace+".selectMovieGenres", movieId);
    movieDetail=sqlSession.selectOne(namespace+".selectMovieDetail", param);
    movieDetail.setMovieGenre(genres);
    return movieDetail;
  }

  public List<titleDTO> selectLikeGenreMovie(String userId) {
    List<titleDTO> titleDTOList = new ArrayList<>();
    List<String> genres=null;

    genres=sqlSession.selectList(namespace+".selectLikeGenres", userId);
    for(int i=0;i< genres.size()&&titleDTOList.size()<10;i++){
      titleDTOList.addAll(sqlSession.selectList(namespace+".selectLikeGenreMovies", genres.get(i)));
    }
    while(titleDTOList.size()>10){
      titleDTOList.remove(titleDTOList.size()-1);
    }
    return titleDTOList;
    
  }
}
