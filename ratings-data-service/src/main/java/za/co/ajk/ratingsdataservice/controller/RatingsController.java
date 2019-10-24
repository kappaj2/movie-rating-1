package za.co.ajk.ratingsdataservice.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.ajk.ratingsdataservice.model.Rating;
import za.co.ajk.ratingsdataservice.model.UserRating;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsController {

    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable String movieId) {
        return new Rating(movieId, 4);
    }

    @RequestMapping("/users/{userId}")
    public UserRating getUserRating(@PathVariable String userId) {

        System.out.println("Getting userRating for userId >"+userId+"<");

        List<Rating> ratingsList = Arrays.asList(
                new Rating("1234", 3),
                new Rating("5678", 3)
        );

        UserRating userRating = new UserRating();
        userRating.setUserRatings(ratingsList);

        return userRating;
    }
}
