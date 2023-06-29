package com.example.swplanetapi.controller;

import com.example.swplanetapi.domain.Planet;
import com.example.swplanetapi.domain.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/planets")
public class PlanetController {

    @Autowired
    private PlanetService planetService;

    @PostMapping
    public ResponseEntity<Planet> create(@RequestBody Planet planet){
        Planet planetaCrated = planetService.create(planet);
        return ResponseEntity.status(HttpStatus.CREATED).body(planetaCrated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Planet> get(@PathVariable("id") Long id){
        return planetService.get(id).map(planet -> ResponseEntity.ok(planet))
                .orElseGet(()-> ResponseEntity.notFound().build());
    }
}
