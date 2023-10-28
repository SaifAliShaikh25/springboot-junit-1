package com.examplespringboot.movie.controller;

import com.examplespringboot.movie.entity.Movie;
import com.examplespringboot.movie.repository.MovieRepository;
import com.examplespringboot.movie.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Mock
    private MovieService movieService;
    @InjectMocks
    private MovieController movieController;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    Movie movie1 = Movie.builder().id(1L).name("K3G").rating(5).build();
    Movie movie2 = Movie.builder().id(2L).name("YJHD").rating(4).build();
    Movie movie3 = Movie.builder().id(3L).name("My name is Khan").rating(4).build();
    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
    }

    @Test
    public void getAllMovies() throws Exception{
        List<Movie> records = Lists.newArrayList(Arrays.asList(movie1, movie2, movie3));

        when(movieService.getAllMovies()).thenReturn(records);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/movies/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].name", is("YJHD")));

    }

    @Test
    public void getMovieByIdTest() throws Exception{
        when(movieService.getMovie(1L)).thenReturn(Optional.ofNullable(movie1));
        Movie movieRecord = movieController.getMovie(1L);
        assertThat(movieRecord.getName()).isEqualTo("K3G");

        mockMvc.perform(MockMvcRequestBuilders
                .get("/movies/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.rating", is(5)));
    }

    @Test
    public void addMovieTest() throws Exception{
        Movie movie = Movie.builder().id(6L).name("Raazi").rating(4).build();
        when(movieService.addMovie(movie)).thenReturn(movie);
        String movieRecord = objectWriter.writeValueAsString(movie);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(movieRecord);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Raazi")));
    }


    @Test
    public void updateMovieTest() throws Exception{
        Movie movie = Movie.builder()
                .id(52L)
                .name("Chennai Express")
                .summary("Comedy and Romance")
                .rating(3)
                .build();
        when(movieService.updateMovie(52L, movie)).thenReturn(movie);

        String movieRecord = objectWriter.writeValueAsString(movie);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders
                        .put("/movies/52")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(movieRecord);


        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Chennai Express")));
    }

    @Test
    public void deleteMovieByIdTest() throws Exception{

       mockMvc.perform(
               MockMvcRequestBuilders
                       .delete("/movies/53")
                       .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());

    }
}
