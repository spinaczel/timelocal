package com.timeactuall.demo.weather;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URL;

@Service
@Component
public class WeatherService {


   @Value("${API_URL}")
    String apiUrl;

    Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing().load();
    String apiKey = dotenv.get("API_KEY");

    public WeatherDao getWeather(String city)  {

         String temp;
         String main;
         String description;
         String icon;

        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);;
        String serviceApi = apiUrl + city + "&units=metric&appid=" + apiKey;
        WeatherDao weatherDao = null;
        try {
            weatherDao = objectMapper.readValue(new URL(serviceApi).openStream(), WeatherDao.class);

            temp = weatherDao.getMain().get("temp").asText();
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


