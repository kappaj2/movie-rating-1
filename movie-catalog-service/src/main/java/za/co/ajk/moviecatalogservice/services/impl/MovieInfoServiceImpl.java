package za.co.ajk.moviecatalogservice.services.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import za.co.ajk.moviecatalogservice.model.CatalogItem;
import za.co.ajk.moviecatalogservice.model.Movie;
import za.co.ajk.moviecatalogservice.model.Rating;
import za.co.ajk.moviecatalogservice.services.MovieInfoService;

@Service
public class MovieInfoServiceImpl implements MovieInfoService {

    private RestTemplate restTemplate;

    public MovieInfoServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem",
            threadPoolKey = "movieInfoPool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "20"),
                    @HystrixProperty(name = "maxQueueSize", value = "10")
            },
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
            })
    public CatalogItem getCatalogItem(Rating rating) {
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
        return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
    }

    /**
     * Fallback method for Hystrix circuit breaker for getCatalogItem
     */
    private CatalogItem getFallbackCatalogItem(Rating rating) {
        return new CatalogItem("No Movie", "", 0);
    }
}
