package com.stylesnap.services;

import com.stylesnap.models.DeliveryDetail;
import com.stylesnap.repositories.DeliveryDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryDetailRepository deliveryRepository;

    public List<DeliveryDetail> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    public DeliveryDetail saveDelivery(DeliveryDetail deliveryDetail) {
        return deliveryRepository.save(deliveryDetail);
    }
}
