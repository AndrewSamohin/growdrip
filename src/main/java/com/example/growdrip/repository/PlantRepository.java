package com.example.growdrip.repository;

import com.example.growdrip.entity.Plant;
import com.example.growdrip.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlantRepository extends JpaRepository<Plant, Long> {

    //Поиск по части имени
    List<Plant> findByName(String name);

    //Выборка всех избранных растений конкретного пользователя
    List<Plant> findByUser(User user);

    //поиск по названию у текущего пользователя
    List<Plant> findByUserAndName(User user, String namePart);

}
