package com.spring.cache.service;

import com.spring.cache.bean.Movie;
import com.spring.cache.controller.SpringCacheController;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class MovieService {

    private static final Logger LOGGER = Logger.getLogger(MovieService.class.getName());

    private static List<Movie> listMovie = new ArrayList<>();
    static{
        listMovie.add(new Movie("Other State","Jon Ham","5"));
        listMovie.add(new Movie("Talk to Me","Sophia Sane","4"));
        listMovie.add(new Movie("All Out Cake","Jon Doe","3"));
        listMovie.add(new Movie("Me and Monkey","Jon Ham","5"));
    }

    @Cacheable("movies")
    public List<Movie> getAllMovies() {
        LOGGER.info("Here are the list of Movies from the List...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return listMovie;
    }

    @Cacheable(value = "movies")
    public Movie getMovieByTitle(String title) {
        LOGGER.info("Get the movie "+ title);
        return listMovie.stream()
                .filter(movie -> movie.getTitle().equals(title))
                .findFirst()
                .orElse(null);
    }

    @CacheEvict(value = "movies")
    public void evictMovieFromCache(String title){
        boolean isDeleted = listMovie.removeIf(movie -> movie.getTitle().equals(title));
        if (isDeleted)
            LOGGER.info("Movie " + title +" deleted from the cache!");
        else
            LOGGER.info("Movie " + title +" Not Found!");
    }
}
