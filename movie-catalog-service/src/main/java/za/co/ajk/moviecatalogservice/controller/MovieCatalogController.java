package za.co.ajk.moviecatalogservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.ajk.moviecatalogservice.model.CatalogItem;
import za.co.ajk.moviecatalogservice.model.UserRating;
import za.co.ajk.moviecatalogservice.services.MovieInfoService;
import za.co.ajk.moviecatalogservice.services.MovieRatingService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

    private MovieInfoService movieInfoService;
    private MovieRatingService movieRatingService;

    public MovieCatalogController(MovieInfoService movieInfoService, MovieRatingService movieRatingService) {
        this.movieInfoService = movieInfoService;
        this.movieRatingService = movieRatingService;
    }

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        //  service lookup done with the RestTemplate @LoadBalanced annotation.
        UserRating userRating = movieRatingService.getUserRating(userId);

        return userRating.getRatings().stream()
                .map(rating -> movieInfoService.getCatalogItem(rating))
                .collect(Collectors.toList());

    }

}