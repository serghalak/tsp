package com.delivery.tsp.repository;

import com.delivery.tsp.model.DeliveryPoint;
import org.springframework.data.repository.CrudRepository;

public interface DeliveryPointRepository extends CrudRepository<DeliveryPoint,Integer> {
}
