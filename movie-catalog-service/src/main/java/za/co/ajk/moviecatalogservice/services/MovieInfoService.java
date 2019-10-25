package za.co.ajk.moviecatalogservice.services;

import za.co.ajk.moviecatalogservice.model.CatalogItem;
import za.co.ajk.moviecatalogservice.model.Rating;

public interface MovieInfoService {

    CatalogItem getCatalogItem(Rating rating);
}
