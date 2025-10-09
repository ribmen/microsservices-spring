package com.algaworks.algadelivery.courier.management.domain.service;

import com.algaworks.algadelivery.courier.management.domain.model.Courier;
import com.algaworks.algadelivery.courier.management.domain.repository.CourierRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CourierDeliveryService {

    private final CourierRepository courierRepository;

    public void assign(UUID deliveryId) {
        Courier courier = courierRepository.findTop1ByOrderByLastFulfilledDeliveryAtAsc()
                .orElseThrow(() -> new EntityNotFoundException("Nenhum courier disponível para delivery " + deliveryId));

        courier.assign(deliveryId);

        courierRepository.saveAndFlush(courier);

        log.info("Courier: {} assigned to delivery: {}", courier.getId(), deliveryId);
    }

    public void fulfill(UUID deliveryId) {
        Courier courier = courierRepository.findByPendingDeliveries_id(deliveryId)
                .orElseThrow(() -> new EntityNotFoundException("Courier não encontrado para delivery pendente " + deliveryId));

        courier.fulfill(deliveryId);

        courierRepository.saveAndFlush(courier);

        log.info("Courier: {} fulfilled delivery: {}", courier.getId(), deliveryId);
    }
}
