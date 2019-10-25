package za.co.ajk.movieinfoservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import za.co.ajk.movieinfoservice.model.Movie;
import za.co.ajk.movieinfoservice.model.MovieSummary;

@RestController
@RequestMapping("/movies")
public class MoviesController {

    @Value("${api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

//    @GetMapping("/{movieId}")
//    public Movie getMovieInfo(@PathVariable("movieId") String movieId) {
//        System.out.println("Getting movie info for moviId >"+movieId+"<");
//        return new Movie(movieId, "Test name");
//    }

    @GetMapping("/{movieId}")
    public Movie getMovieInfo(@PathVariable("movieId") String movieId) {
        MovieSummary movieSummary = restTemplate.getForObject("https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + apiKey, MovieSummary.class);

        System.out.println("Retrieve movieSummary : " + movieSummary.toString());
        return new Movie(movieId, movieSummary.getTitle(), movieSummary.getOverview());

    }

}
