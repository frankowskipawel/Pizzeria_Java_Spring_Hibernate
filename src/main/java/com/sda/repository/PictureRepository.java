package com.sda.repository;

import com.sda.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Integer> {

    Picture findByFileName(String filename);
}
