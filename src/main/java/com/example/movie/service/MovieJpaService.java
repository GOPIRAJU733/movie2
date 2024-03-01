package com.example.movie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

import com.example.movie.model.Movie;
import com.example.movie.repository.MovieRepository;
import com.example.movie.repository.MovieJpaRepository;

@Service
public class MovieJpaService implements MovieRepository {

    @Autowired
    private MovieJpaRepository movieJpaRepository;

    @Override
    public ArrayList<Movie> getMovies() {
        List<Movie> movieCollection = movieJpaRepository.findAll();
        ArrayList<Movie> movies = new ArrayList<>(movieCollection);

        return movies;
    }

    public void hello() {

        System.out.println("hello world");
    }
    @Override
    public Movie getMovieById(int movieId) {
        try {
            Movie movie = movieJpaRepository.findById(movieId).get();
            return movie;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Movie addMovie(Movie movie) {
        movieJpaRepository.save(movie);
        return movie;
    }

    @Override
    public Movie updateMovie(int movieId, Movie movie) {
        try {
            Movie newMovie = movieJpaRepository.findById(movieId).get();
            if (movie.getMovieName() != null) {
                newMovie.setMovieName(movie.getMovieName());
            }
            if (movie.getLeadActor() != null) {
                newMovie.setLeadActor(movie.getLeadActor());
            }
            movieJpaRepository.save(newMovie);
            return newMovie;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteMovie(int movieId) {
        try {
            movieJpaRepository.deleteById(movieId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

}
