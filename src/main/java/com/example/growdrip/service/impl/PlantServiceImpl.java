package com.example.growdrip.service.impl;

import com.example.growdrip.dto.PlantDto;
import com.example.growdrip.entity.Plant;
import com.example.growdrip.entity.User;
import com.example.growdrip.mapper.PlantMapper;
import com.example.growdrip.repository.PlantRepository;
import com.example.growdrip.security.CustomUserDetailsService;
import com.example.growdrip.service.PlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlantServiceImpl implements PlantService {

    private final PlantRepository plantRepository;
    private final PlantMapper plantMapper;
    private final CustomUserDetailsService customUserDetailsService;

    //Возвращает текущего пользователя
    private User getCurrentUser() {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        return customUserDetailsService.loadUserEntityByUsername(username);
    }

    //Получить все растения, принадлежащие текущему пользователю
    @Override
    public List<PlantDto> getAllPlants() {
        User user = getCurrentUser();
        return plantRepository
                .findByUser(user)
                .stream()
                .map(plant -> plantMapper.toDto(plant))
                .collect(Collectors.toList());
    }

    //Поиск растений по части названия для текущего пользователя
    @Override
    public List<PlantDto> searchPlants(String query) {
        User user = getCurrentUser();
        return plantRepository
                .findByUserAndName(user, query)
                .stream()
                .map(plant -> plantMapper.toDto(plant))
                .collect(Collectors.toList());
    }

    //Получить одно растение по его ID
    @Override
    public PlantDto getPlantById(Long id) {
        Plant plant = plantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plant not found"));
        return plantMapper.toDto(plant);
    }

    //Сохранить новое растение
    @Override
    public PlantDto savePlant(PlantDto dto) {
        User user = getCurrentUser();
        Plant plant = plantMapper.toEntity(dto);
        plant.setUser(user);
        return plantMapper.toDto(plantRepository.save(plant));
    }

    @Override
    public void deletePlant(Long id) {
        plantRepository.deleteById(id);
    }

}
