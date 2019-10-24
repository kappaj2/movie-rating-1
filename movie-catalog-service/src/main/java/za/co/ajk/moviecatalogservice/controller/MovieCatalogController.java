package za.co.ajk.moviecatalogservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import za.co.ajk.moviecatalogservice.model.CatalogItem;
import za.co.ajk.moviecatalogservice.model.Movie;
import za.co.ajk.moviecatalogservice.model.UserRating;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;    // This can be used to programatically get hold of services.

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        //  service lookup done with the RestTemplate @LoadBalanced annotation.
        UserRating userRating = restTemplate.getForObject("http://rating-data-service/ratingsdata/users/" + userId, UserRating.class);

        return userRating.getUserRatings().stream().map(rating -> {
            Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
            return new CatalogItem(movie.getName(), "Test", rating.getRating());
        }).collect(Collectors.toList());


    }


}
//  WebClient way (Reactive)
//        return ratingsList.stream().map(rating -> {
//
//                Movie movie = webClientBuilder.build()
//                .get()
//                .uri("http://localhost:8082/movies/" + rating.getMovieId())
//                .retrieve()
//                .bodyToMono(Movie.class)
//                .block(); // wait tiull mono fulfilled
//
//        return new CatalogItem(movie.getName(), "Test", rating.getRating());
//
//        }).collect(Collectors.toList());