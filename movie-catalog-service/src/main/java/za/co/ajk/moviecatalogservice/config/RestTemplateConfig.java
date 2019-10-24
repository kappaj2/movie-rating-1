package za.co.ajk.moviecatalogservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class RestTemplateConfig {

    @Bean
    @LoadBalanced   //  include service discovery...
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public WebClient.Builder getWebClient() {
        return WebClient.builder();
    }
}
