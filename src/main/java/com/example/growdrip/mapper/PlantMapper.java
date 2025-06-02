package com.example.growdrip.mapper;

import com.example.growdrip.dto.PlantDto;
import com.example.growdrip.entity.Plant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlantMapper extends Mappable<Plant, PlantDto> {
}
