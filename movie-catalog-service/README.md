When using async calls with WebClient, use the following:
Autowire the WebClientBuilder
```$xslt
    @Autowired
    private WebClient.Builder webClientBuilder;
```


Call the endpoint.
```$xslt
  WebClient way (Reactive)
        return ratingsList.stream().map(rating -> {

                Movie movie = webClientBuilder.build()
                .get()
                .uri("http://localhost:8082/movies/" + rating.getMovieId())
                .retrieve()
                .bodyToMono(Movie.class)
                .block(); // wait tiull mono fulfilled

        return new CatalogItem(movie.getName(), "Test", rating.getRating());

        }).collect(Collectors.toList());
```

With Discovery client you can also use the discovery client to get information from Eureka.
```$xslt
    @Autowired
    private DiscoveryClient discoveryClient;
```