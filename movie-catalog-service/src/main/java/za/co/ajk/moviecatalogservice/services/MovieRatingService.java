package za.co.ajk.moviecatalogservice.services;

import za.co.ajk.moviecatalogservice.model.UserRating;

public interface MovieRatingService {

    UserRating getUserRating(String userId);
}
