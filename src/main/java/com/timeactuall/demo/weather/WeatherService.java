package com.timeactuall.demo.weather;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URL;

@Service
public class WeatherService {
    private String apiKey = "be11474518c35898acf9afda66235cf8";
    private String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=";
    private String temp;
    private String main;
    private String description;
    private String icon;

    public WeatherDao getWeather(String city) {
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);;
        String serviceApi = apiUrl + city + "&units=metric&appid=" + apiKey;
        WeatherDao weatherDao = null;
        try {
            weatherDao = objectMapper.readValue(new URL(serviceApi).openStream(), WeatherDao.class);

            temp = weatherDao.getMain().get("temp").asText() + "°C";
            weatherDao.setTemp(temp);
            main = weatherDao.getWeather().get(0).get("main").asText();
            weatherDao.setWeatherMain(main);
            description = weatherDao.getWeather().get(0).get("description").asText();
            weatherDao.setDescription(description);
            icon = weatherDao.getWeather().get(0).get("icon").asText();
            weatherDao.setIcon(icon);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return weatherDao;
    }
}


