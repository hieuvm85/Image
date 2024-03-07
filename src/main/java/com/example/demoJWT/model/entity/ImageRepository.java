package com.example.demoJWT.model.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ImageRepository extends JpaRepository<Image, Integer> {

}
