package com.example.movie.controller;

import com.example.movie.service.MovieJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import com.example.movie.model.Movie;

@RestController
public class MovieController {

    @Autowired
    private MovieJpaService movieService;

    @GetMapping("/movies")
    public ArrayList<Movie> getMovies() {
        return movieService.getMovies();
    }

    @GetMapping("/movies/{movieId}")
    public Movie getMovieById(@PathVariable("movieId") int movieId) {
        return movieService.getMovieById(movieId);
    }

    @PostMapping("/movies")
    public Movie addMovie(@RequestBody Movie movie) {
        return movieService.addMovie(movie);
    }

    @PutMapping("/movies/{movieId}")
    public Movie updateMovie(@PathVariable("movieId") int movieId, @RequestBody Movie movie) {
        return movieService.updateMovie(movieId, movie);
    }

    @DeleteMapping("/movies/{movieId}")
    public void deleteMovie(@PathVariable int movieId) {
        movieService.deleteMovie(movieId);
    }


}
