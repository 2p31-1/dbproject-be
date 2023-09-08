package com.nettopulikkusu.Controller;

import com.nettopulikkusu.DTO.titleDTO;
import com.nettopulikkusu.DTO.titleDetailDTO;
import com.nettopulikkusu.DTO.userDTO;
import com.nettopulikkusu.Service.titleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class apiController {

    @Autowired
    titleService titleService;

    @CrossOrigin
    @GetMapping(value = "/userRated",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<titleDTO> getMovies(@RequestParam("userId") String userId){
        return titleService.selectMovies(userId);
    }
    @CrossOrigin
    @GetMapping(value = "/userOccupationRF",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<titleDTO> getOccupationMovies(@RequestParam("userId") String userId){
        return titleService.selectOccupationMovies(userId);
    }
    @CrossOrigin
    @GetMapping(value = "/userAgeRF",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<titleDTO> getAgeMovies(@RequestParam("userId") String userId){
        return titleService.selectAgeMovies(userId);
    }
    @CrossOrigin
    @GetMapping(value = "/userKNNRF",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<titleDTO> getKNNMovies(@RequestParam("userId") String userId){
        return titleService.selectKNNMovies(userId, 40);
    }
    @CrossOrigin
    @GetMapping(value = "/userGenreRF",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<titleDTO> getLikeGenreMovies(@RequestParam("userId") String userId){
        return titleService.selectLikeGenreMovie(userId);
    }
    @CrossOrigin
    @GetMapping(value = "/userInfo",produces = MediaType.APPLICATION_JSON_VALUE)
    public userDTO getUserInfo(@RequestParam("userId") String userId){
        return titleService.selectUser(userId);
    }

    @CrossOrigin
    @GetMapping(value = "/movieData",produces = MediaType.APPLICATION_JSON_VALUE)
    public titleDetailDTO getMovieData(@RequestParam("userId") String userId, @RequestParam("movieId") String movieId){
        return titleService.selectMovieData(userId, movieId);
    }

}
