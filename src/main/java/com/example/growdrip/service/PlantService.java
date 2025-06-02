package com.example.growdrip.service;

import com.example.growdrip.dto.PlantDto;

import java.util.List;

public interface PlantService {
    //Все избранные растения текущего пользователя
    List<PlantDto> getAllPlants();

    //Поиск по названию
    List<PlantDto> searchPlants(String query);

    //Детальная инфа по ID
    PlantDto getPlantById(Long id);

    //Добавить в избранное
    PlantDto savePlant(PlantDto dto);

    //Удалить из избранного
    void deletePlant(Long id);

}
