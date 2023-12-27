package com.example.capstone.service;

import com.example.capstone.model.Payment.Payment;

public interface IPaymentService extends IService<Payment>{
    Payment findPaymentByTnxRef(String tnxRef);
    void deleteByTnxRef(String tnxRef);
}
