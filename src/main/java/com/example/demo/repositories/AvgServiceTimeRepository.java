package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.AverageTime;

public interface AvgServiceTimeRepository extends JpaRepository<AverageTime, Integer>, AvgServiceTimeRepositoryCustom {

}
