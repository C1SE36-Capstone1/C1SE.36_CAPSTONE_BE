package com.example.capstone.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IService <T>{
    Page<T> findAll(Pageable pageable);

    T findById(Integer id);

    T update(T t);

    void deleteById(Integer id);
}
