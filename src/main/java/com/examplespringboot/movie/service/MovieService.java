package com.examplespringboot.movie.service;

import com.examplespringboot.movie.entity.Movie;
import com.examplespringboot.movie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;
    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }

    public Optional<Movie> getMovie(Long id){
        return movieRepository.findById(id);
    }

    public Movie updateMovie(Long id, Movie movie){
        Optional<Movie> movies = movieRepository.findById(id);
        Movie myMovie = movies.get();

        myMovie.setName(movie.getName());
        if(!(movie.getSummary()== null))
            myMovie.setSummary(movie.getSummary());
        if(!(movie.getRating() == 0))
            myMovie.setRating(movie.getRating());
        return movieRepository.save(myMovie);
    }
}
