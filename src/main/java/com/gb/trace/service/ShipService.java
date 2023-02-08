package com.gb.trace.service;

import com.gb.trace.entity.Ship;
import com.gb.trace.entity.dto.ShipRequest;
import com.gb.trace.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class ShipService {

    @Autowired
    private ShipRepository shipRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<Ship> getAllShips() {
        Ship ship = new Ship();
        updateFlag(ship);
        return shipRepository.findAll();
    }

    public Ship getShip(String shipName) {
//        Optional<Ship> optionalShip = shipRepository.findOneByName(shipName);
//        if (optionalShip.isPresent()) {
//            return optionalShip.get();
//        }
        return new Ship();
    }

    public Ship createShipLocation(ShipRequest request){
        Ship ship = new Ship();
        ship.setName(request.getName());
        ship.setPort(request.getPort());
        ship.setDate(Calendar.getInstance().getTime());
        return shipRepository.save(ship);
    }

    public Ship updateShipLocationByShipId(Long id, String port) {
        Optional<Ship> optionalShip = shipRepository.findById(id);
        if (optionalShip.isPresent()) {
            Ship ship = optionalShip.get();
            ship.setPort(port);
            ship.setDate(Calendar.getInstance().getTime());
            return shipRepository.save(ship);
        }
        return new Ship();
    }

    public Ship updateShipLocationByShipName(String shipName, String port) {
        Optional<Ship> optionalShip = shipRepository.findByName(shipName);
        if (optionalShip.isPresent()) {
            Ship ship = optionalShip.get();
            ship.setPort(port);
            ship.setDate(Calendar.getInstance().getTime());
            shipRepository.save(ship);
            return ship;
        }
        return new Ship();
    }

    private void updateFlag(Ship ship) {
        HttpEntity<Ship> request = new HttpEntity<>(ship);
        ResponseEntity<Ship> response = restTemplate.exchange("http://localhost:8082/api/flags", HttpMethod.GET, request, Ship.class);
        System.out.println("Status for Location: " + response.getStatusCode().value());
    }
}
