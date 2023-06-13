package com.nettopulikkusu.Service;

import com.nettopulikkusu.DAO.titleDAO;
import com.nettopulikkusu.DTO.titleDTO;
import com.nettopulikkusu.DTO.userDTO;
import com.nettopulikkusu.DTO.ratingDTO;

import com.nettopulikkusu.util.*;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class titleService {

  @Autowired
  titleDAO titleDAO;

  public List<titleDTO> selectMovies(String userId){

    List<titleDTO>titleDTOList=null;

    titleDTOList=titleDAO.selectMovie(userId);

    return titleDTOList;
  }

  public List<titleDTO> selectOccupationMovies(String userId){

    List<titleDTO>titleDTOList=null;

    titleDTOList=titleDAO.selectOccupationMovie(userId);

    return titleDTOList;
  }

  public List<titleDTO> selectAgeMovies(String userId){

    List<titleDTO>titleDTOList=null;

    titleDTOList=titleDAO.selectAgeMovie(userId);

    return titleDTOList;
  }

  public userDTO selectUser(String userId){

    userDTO nowUser=null;

    nowUser=titleDAO.selectUser(userId);

    return nowUser;
  }


  public List<titleDTO> selectKNNMovies(String userId, int k){
    if(userId==null)return null;
    int queryUserIdx = Integer.parseInt(userId);
    List<ratingDTO>ratingDTOList = null;
    List<titleDTO>titleDTOList=null;



    ratingDTOList= titleDAO.selectRatings();

    Map<Integer, Map<Integer, Integer>> ratingTable = new HashMap<>();
    PriorityQueue<KeyValue<Double, Integer>> PCCpq = new PriorityQueue<>(Comparator.reverseOrder());
    Map<Integer, Double> averageRating = new HashMap<>();
    for(ratingDTO rating:ratingDTOList){
      Map<Integer, Integer> userRating =
              ratingTable.getOrDefault(rating.getUserId(), new HashMap<>());
      double sum = averageRating.getOrDefault(rating.getUserId(), 0.0);
      sum+=rating.getRatingScore();
      userRating.put(rating.getMovieId(), rating.getRatingScore());
      averageRating.put(rating.getUserId(), sum);
      ratingTable.put(rating.getUserId(), userRating);
    }

    Map<Integer, Integer> queryUserRatingTable = ratingTable.get(queryUserIdx);
    Set<Integer> queryUserRatingTableKeys = queryUserRatingTable.keySet();
    double queryUserAverageRating = averageRating.get(queryUserIdx)/queryUserRatingTableKeys.size();
    averageRating.put(queryUserIdx, queryUserAverageRating);
    List<Integer> targetUserList = new ArrayList<>(ratingTable.keySet());

    for(int targetUserIdx : targetUserList){
      if(targetUserIdx==queryUserIdx)continue;
      Map<Integer, Integer> targetUserRatingTable = ratingTable.get(targetUserIdx);
      Set<Integer> ratedMovieIdKeys = targetUserRatingTable.keySet();
      double targetUserAverageRating = averageRating.get(targetUserIdx)/targetUserRatingTable.size();
      averageRating.put(targetUserIdx, targetUserAverageRating);
      ratedMovieIdKeys.retainAll(queryUserRatingTableKeys);

      Double dividend=0.0;
      Double divisor1=0.0;
      Double divisor2=0.0;

      for(int targetMovieIdx:ratedMovieIdKeys){
        dividend+=(queryUserRatingTable.get(targetMovieIdx)-queryUserAverageRating)
                *(targetUserRatingTable.get(targetMovieIdx)-targetUserAverageRating);
        divisor1+=Math.pow(queryUserRatingTable.get(targetMovieIdx)-queryUserAverageRating, 2);
        divisor2+=Math.pow((targetUserRatingTable.get(targetMovieIdx)-targetUserAverageRating), 2);
      }
      divisor1=Math.pow(divisor1, 0.5);
      divisor2=Math.pow(divisor2, 0.5);

      if(divisor1==0.0 || divisor2==0.0){
        PCCpq.add(new KeyValue<>(0.0, targetUserIdx));
        continue;
      }
      PCCpq.add(new KeyValue<>(dividend/divisor1/divisor2, targetUserIdx));
    }

    //Calculate predicted rating of movie with k of nearest neighbors
    HashMap<Integer, Double> predictedMovieRatingDividend=new HashMap<>();
    HashMap<Integer, Double> predictedMovieRatingDivisor=new HashMap<>();
    for(int i=0;i<k&&!PCCpq.isEmpty();i++){
      KeyValue<Double, Integer> top = PCCpq.poll();
      Double sim = top.getKey();
      int targetUserIdx = top.getValue();
      double targetUserAverageRating = averageRating.get(targetUserIdx);
      Map<Integer, Integer> targetUserRatingTable = ratingTable.get(targetUserIdx);
      Set<Integer> ratedMovieIdKeys = targetUserRatingTable.keySet();

      for(int targetMovieIdx:ratedMovieIdKeys) {
        Double dividend = predictedMovieRatingDividend.getOrDefault(targetMovieIdx, 0.0);
        Double divisor = predictedMovieRatingDivisor.getOrDefault(targetMovieIdx, 0.0);
        predictedMovieRatingDividend.put(targetMovieIdx, dividend+sim*(targetUserRatingTable.get(targetMovieIdx)-targetUserAverageRating));
        predictedMovieRatingDivisor.put(targetMovieIdx, divisor+sim);
      }
    }
    PriorityQueue<KeyValue<Double, Integer>> moviePq = new PriorityQueue<>(Comparator.reverseOrder());
    Set<Integer> movieIdxKeys = predictedMovieRatingDividend.keySet();
    for(int targetMovieIdx: movieIdxKeys){
      double dividend = predictedMovieRatingDividend.get(targetMovieIdx);
      double divisor = predictedMovieRatingDivisor.get(targetMovieIdx);
      moviePq.add(new KeyValue<>(dividend/divisor+queryUserAverageRating, targetMovieIdx));
    }

    //adding id of highest recommended movies
    List<Integer> movieIdList = new ArrayList<>();
    for(int i=0;i<10;i++){
      movieIdList.add(moviePq.poll().getValue());
    }

    titleDTOList = titleDAO.selectMovieData(movieIdList);

    return titleDTOList;
  }

}
