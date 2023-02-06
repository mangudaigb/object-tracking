package com.gb.trace.controller;

import com.gb.trace.entity.Ship;
import com.gb.trace.entity.dto.ShipRequest;
import com.gb.trace.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/ships")
public class ShipController {

    @Autowired
    private ShipService shipService;

//    @Autowired
//    private Tracer tracer;

    @GetMapping("/meta")
    public String metadata() {
//        Map<String, String> baggageMap = tracer.getAllBaggage();
        StringBuilder sb = new StringBuilder();
//        for (Map.Entry<String, String> entry: baggageMap.entrySet()) {
//            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append(" ");
//        }
        return sb.toString();
    }

    @GetMapping
    public ResponseEntity<List<Ship>> getAll() {
        return ResponseEntity.ok(shipService.getAllShips());
    }

    @GetMapping("/filter")
    public ResponseEntity<Ship> getByName(@RequestParam("name") String name) {
        Ship ship = shipService.getShip(name);
        return ResponseEntity.ok(ship);
    }

    @PostMapping
    public ResponseEntity<Ship> addShip(@RequestBody ShipRequest request) {
        Ship ship = shipService.createShipLocation(request);
        if (ship.getId() == null) return ResponseEntity.internalServerError().build();
        return ResponseEntity.ok(ship);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ship> updateShip(@PathVariable("id") Long shipId, String port) {
        Ship ship = shipService.updateShipLocationByShipId(shipId, port);
        if (ship.getId() == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ship);
    }
}
