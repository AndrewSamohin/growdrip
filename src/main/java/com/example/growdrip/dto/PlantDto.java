package com.example.growdrip.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlantDto {

    private Long id;
    private Long userId;
    private String name;
    private String description;
    private String species;
    private String recommendation;
    private String createdAt;

}
