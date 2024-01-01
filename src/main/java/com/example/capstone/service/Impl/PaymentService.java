package com.example.capstone.service.Impl;

import com.example.capstone.model.Payment.Payment;
import com.example.capstone.repository.Payment.IPaymentRepository;
import com.example.capstone.service.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PaymentService implements IPaymentService {
    @Autowired
    IPaymentRepository paymentRepository;

    @Override
    public Payment findPaymentByTnxRef(String tnxRef) {
        return this.paymentRepository.findPaymentByTnxRef(tnxRef).orElse(null);
    }

    @Override
    public void deleteByTnxRef(String tnxRef) {
        Payment payment = this.paymentRepository.findPaymentByTnxRef(tnxRef).orElse(null);
        if(payment != null) {
            this.paymentRepository.deleteDetailsByPaymentId(payment.getId());
            this.paymentRepository.deleteById(payment.getId());
        }
    }

    @Override
    public Page<Payment> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Payment findById(Long id) {
        return null;
    }

    @Override
    public Payment update(Payment payment) {
        return this.paymentRepository.save(payment);
    }

    @Override
    public void deleteById(Long id) {

    }
}
