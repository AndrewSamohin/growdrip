package com.example.growdrip.controller;

import com.example.growdrip.dto.PlantDto;
import com.example.growdrip.service.PlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plants")
@RequiredArgsConstructor
public class PlantController {

    private final PlantService plantService;

    //Получение всех растений
    @GetMapping
    public ResponseEntity<List<PlantDto>> getAllPlants() {
        return ResponseEntity.ok(plantService.getAllPlants());
    }

    //Поиск по названию
    @GetMapping("/search")
    public ResponseEntity<List<PlantDto>> searchPlants(@RequestParam String query) {
        return ResponseEntity.ok(plantService.searchPlants(query));
    }

    //Детали одного растения по ID
    @GetMapping("/{id}")
    public ResponseEntity<PlantDto> getPlantById(@PathVariable long id) {
        return ResponseEntity.ok(plantService.getPlantById(id));
    }

    //Добавить растение в избранное
    @PostMapping
    public ResponseEntity<PlantDto> createPlant(@RequestBody PlantDto dto) {
        return ResponseEntity.ok(plantService.savePlant(dto));
    }

    //Удалить из избранного
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlant(@PathVariable long id) {
        plantService.deletePlant(id);
        return ResponseEntity.noContent().build();
    }

}
