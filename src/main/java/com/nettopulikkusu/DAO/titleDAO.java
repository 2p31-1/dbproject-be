package com.nettopulikkusu.DAO;

import com.nettopulikkusu.DTO.ratingDTO;
import com.nettopulikkusu.DTO.titleDTO;
import com.nettopulikkusu.DTO.userDTO;
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
}
