package com.spring.cache.controller;

import com.spring.cache.bean.Movie;
import com.spring.cache.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@AllArgsConstructor
public class SpringCacheController {

    private static final Logger LOGGER = Logger.getLogger(SpringCacheController.class.getName());
    MovieService movieService;

    private CacheManager cacheManager;

    @GetMapping("/")
    public ResponseEntity<List<Movie>> getAllMovies(){

        LOGGER.log(Level.INFO, "Request Initiated.....");
        List<Movie> movies = movieService.getAllMovies();
        LOGGER.log(Level.INFO, "Request Finished");

        return new ResponseEntity<>(movies, HttpStatus.OK);
    }


    @GetMapping("/movie")
    public Movie getMovie(@RequestBody Movie movie){
        LOGGER.log(Level.INFO, "Request getMovie Initiated.....");
        Movie aMovie = movieService.getMovieByTitle(movie.getTitle());
        LOGGER.log(Level.INFO, "Request getMovie Finished.....");
        return  aMovie;
    }

    @DeleteMapping("/reset")
    public ResponseEntity<Void> clearAllCaches() {
        cacheManager
                .getCacheNames()
                .stream()
                .forEach(cacheName -> cacheManager.getCache(cacheName).clear());
        LOGGER.info("All Cache deleted!");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
