package com.example.growdrip.mapper;

import java.util.List;

public interface Mappable<E, D> {

    D toDto(E entity);

    List<D> toDto(List<E> entityList);

    E toEntity(D dto);

}
