package com.delivery.tsp.service;


import com.delivery.tsp.dto.CourierDto;
import com.delivery.tsp.model.DeliveryPoint;

public interface DeliveryPointService {

    Iterable<DeliveryPoint> getAllDeliveryPoint();

    String getMinPath(CourierDto courierDto);
    String getMinWaitTime(CourierDto courierDto);
    String getMinWorkTime(CourierDto courierDto);

}
