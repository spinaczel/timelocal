package com.timeactuall.demo;

import com.timeactuall.demo.weather.WeatherDao;
import com.timeactuall.demo.weather.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class TimeZoneController {

    private WeatherService weatherService;

    @Autowired
    public TimeZoneController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public String showTimeZone(Model model) {

        model.addAttribute("timeZone", new TimeZone());
        model.addAttribute("zone", new TimeZoneDao().getZone());
        return "home";

    }

    @GetMapping("/results")
    public String showTime(@RequestParam String zoneName, Model model) {
        WeatherDao weather;
    try {
        try {
            if (zoneName.contains("/")) {
                String city[] = zoneName.split("/");
                weather = weatherService.getWeather(city[city.length-1].replace("_"," "));
            } else {
                weather = weatherService.getWeather(zoneName.replace("_"," "));
            }

            model.addAttribute("temp", weather.getTemp());
            model.addAttribute("description", weather.getDescription());
            model.addAttribute("weatherMain", weather.getWeatherMain());
            model.addAttribute("icon", weather.getIcon());

        } catch (Exception e) {
                model.addAttribute("temp", "weather for this zone is unavailable");
            }

        model.addAttribute("zoneName", zoneName);
        model.addAttribute("zoneValue", new TimeZoneDao().getTime(zoneName));

    } catch (Exception e){
        model.addAttribute("zoneName", "invalid zone, try again");
    }

    return "results";

    }
}


