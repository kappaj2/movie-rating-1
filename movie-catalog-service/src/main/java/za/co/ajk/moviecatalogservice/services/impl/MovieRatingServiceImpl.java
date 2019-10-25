package za.co.ajk.moviecatalogservice.services.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import za.co.ajk.moviecatalogservice.model.Rating;
import za.co.ajk.moviecatalogservice.model.UserRating;
import za.co.ajk.moviecatalogservice.services.MovieRatingService;

import java.util.Arrays;

@Service
public class MovieRatingServiceImpl implements MovieRatingService {

    private RestTemplate restTemplate;

    public MovieRatingServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    @HystrixCommand(fallbackMethod = "getFallbackUserRating",
            threadPoolKey = "movieRatingPool",
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
    public UserRating getUserRating(String userId) {
        return restTemplate.getForObject("http://rating-data-service/ratingsdata/users/" + userId, UserRating.class);
    }

    /**
     * Fallback method for Hystrix circuit breaker for getUserRating
     */
    private UserRating getFallbackUserRating(String userId) {
        UserRating userRating = new UserRating();
        userRating.setUserId(userId);
        userRating.setRatings(
                Arrays.asList(new Rating("0", 0))
        );
        return userRating;
    }
}
