package com.delivery.tsp.controller;

import com.delivery.tsp.dto.CourierDto;
import com.delivery.tsp.model.DeliveryPoint;

import com.delivery.tsp.service.DeliveryPointService;
import com.delivery.tsp.utils.Distance;
import org.springframework.stereotype.Controller;

@Controller
public class DeliveryPointController {

    private DeliveryPointService deliveryPointService;

    public DeliveryPointController(DeliveryPointService deliveryPointService) {
        this.deliveryPointService = deliveryPointService;
    }

    public String findRout(CourierDto courierDto){

        return deliveryPointService.getMinPath(courierDto);

    }
}
