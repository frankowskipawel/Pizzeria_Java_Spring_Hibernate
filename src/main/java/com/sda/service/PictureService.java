package com.sda.service;

import com.sda.entity.Picture;
import com.sda.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PictureService {

    @Autowired
    PictureRepository pictureRepository;

    public Picture savePicture(Picture picture){
        return pictureRepository.save(picture);
    }

    public Picture findByFileName(String fileName){
        return pictureRepository.findByFileName(fileName);
    }

}
