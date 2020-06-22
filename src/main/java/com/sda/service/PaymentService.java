package com.sda.service;

import com.sda.entity.Delivery;
import com.sda.entity.Payment;
import com.sda.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    public Payment save(Payment payment){
        return paymentRepository.save(payment);
    }

    public Page<Payment> findAllPagination(Pageable pageable) {
        return paymentRepository.findAll(pageable);
    }

    public Optional<Payment> findById(int id){
        return paymentRepository.findById(id);
    }

    public void delete(Payment payment){
        paymentRepository.delete(payment);
    }

    public List<Payment> findAll(){
        return paymentRepository.findAll();
    }
}
