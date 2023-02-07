package com.gb.trace.repository;

import com.gb.trace.entity.track.ShipTrackerEvent;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface ShipTrackerRepository extends Neo4jRepository<ShipTrackerEvent, Long> {
    Optional<ShipTrackerEvent> findFirst1ByShipIdOrderByDateDesc(Long shipId);
    Optional<ShipTrackerEvent> findFirst1ByNameOrderByDateDesc(String name);
}
