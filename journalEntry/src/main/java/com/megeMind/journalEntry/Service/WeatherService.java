package com.megeMind.journalEntry.Service;

import com.megeMind.journalEntry.cache.AppCache;
import com.megeMind.journalEntry.constant.Placeholder;
import com.megeMind.journalEntry.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class WeatherService {
    private  final String  apiKey ;
//    private static final String API ;
    private final AppCache appCache;
    private final RestTemplate restTemplate;
    public WeatherService(RestTemplate restTemplate, @Value("${weather.api.key}") String apiKey, AppCache appCache) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.appCache = appCache;
    }
    public WeatherResponse  getWeather(String city){
        String baseUrl = appCache.appCacheMap.get(AppCache.keys.WEATHER_API.toString());
        if(baseUrl==null){
            throw new RuntimeException("baseUrl is null");
        }

        String finalapi =baseUrl.replace(Placeholder.API,apiKey).replace(Placeholder.city,city);

       ResponseEntity<WeatherResponse> responseBody= restTemplate.exchange(finalapi, HttpMethod.GET,null,WeatherResponse.class);
       return  responseBody.getBody();
    }
}
