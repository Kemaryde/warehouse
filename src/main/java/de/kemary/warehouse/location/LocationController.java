package de.kemary.warehouse.location;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/location")
@AllArgsConstructor
public class LocationController {
    final LocationRepository locationRepository;

    @GetMapping
    public List<Location> getAllLocations(){
        return locationRepository.findAll();
    }

    @PostMapping
    public void postLocation(@RequestBody Location location){
        if(locationRepository.findByName(location.getName()).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Location already known");
        }
        locationRepository.save(location);
    }

}
