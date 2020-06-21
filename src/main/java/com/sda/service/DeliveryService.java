package com.sda.service;

import com.sda.entity.Category;
import com.sda.entity.Delivery;
import com.sda.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeliveryService {

    @Autowired
    DeliveryRepository deliveryRepository;

    public Delivery save(Delivery delivery){
        return deliveryRepository.save(delivery);
    }

    public Page<Delivery> findAllPagination(Pageable pageable) {
        return deliveryRepository.findAll(pageable);
    }

    public Optional<Delivery> findById(int id){
        return deliveryRepository.findById(id);
    }
}
