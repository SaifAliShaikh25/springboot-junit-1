package com.examplespringboot.movie.controller;

import com.examplespringboot.movie.entity.Movie;
import com.examplespringboot.movie.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping
    public Movie add(@RequestBody @Valid Movie movie){
        return movieService.addMovie(movie);
    }

    @GetMapping("/")
    public List<Movie> getMovies(){
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public Movie getMovie(@PathVariable("id") Long id){
        return movieService.getMovie(id).get();
    }

    @PutMapping("/{id}")
    public Movie updateMovie(@PathVariable("id") Long id, @RequestBody Movie movie){
        return movieService.updateMovie(id, movie);
    }

    //Delete to be written by TDD approach
    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable("id") Long id){
        movieService.deleteMovie(id);
    }
}
