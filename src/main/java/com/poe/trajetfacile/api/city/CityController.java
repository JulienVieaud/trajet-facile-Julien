package com.poe.trajetfacile.api.city;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/city")
public class CityController {


    @GetMapping
    public List<City> search(@RequestParam("search") String search) {

        List<City> cities = new ArrayList<>();
        cities.add(new City("Angers"));
        cities.add(new City("Nantes"));
        cities.add(new City("Rennes"));
        cities.add(new City("Brest"));
        cities.add(new City("Paris"));
        cities.add(new City("Caen"));
        cities.add(new City("Lille"));
        cities.add(new City("Madrid"));

        List<City> ret = new ArrayList<>();
        for(City city : cities) {
            if(city.getName().toLowerCase().contains(search.toLowerCase())) {
                ret.add(city);
            }
        }
        return ret;

    }

}
