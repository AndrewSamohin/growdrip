package com.example.growdrip.repository;

import com.example.growdrip.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);//Метод будет искать пользователя по логину
    boolean existsByEmail(String email);//Возвращает true, если пользователь с такой почтой есть
    boolean existsByUsername(String username);//Возвращает true, если пользователь с таким логином есть

}
