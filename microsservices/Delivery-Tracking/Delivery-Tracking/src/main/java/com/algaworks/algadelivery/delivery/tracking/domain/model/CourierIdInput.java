package com.algaworks.algadelivery.delivery.tracking.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CourierIdInput {
    private UUID courierId;
}
