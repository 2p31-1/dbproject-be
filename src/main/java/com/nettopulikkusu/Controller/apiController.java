package com.nettopulikkusu.Controller;

import com.nettopulikkusu.DTO.titleDTO;
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

    @GetMapping(value = "/userRated",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<titleDTO> getMovies(@RequestParam("userId") String userId){
        return titleService.selectMovies(userId);
    }
    @GetMapping(value = "/userOccupationRF",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<titleDTO> getOccupationMovies(@RequestParam("userId") String userId){
        return titleService.selectOccupationMovies(userId);
    }
    @GetMapping(value = "/userAgeRF",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<titleDTO> getAgeMovies(@RequestParam("userId") String userId){
        return titleService.selectAgeMovies(userId);
    }
    @GetMapping(value = "/userKNNRF",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<titleDTO> getKNNMovies(@RequestParam("userId") String userId){
        return titleService.selectKNNMovies(userId, 40);
    }
}
