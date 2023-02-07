package com.gb.trace.service;

import com.gb.trace.entity.Ship;
import com.gb.trace.entity.dto.ShipRequest;
import com.gb.trace.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class ShipService {

    @Autowired
    private ShipRepository shipRepository;

    public List<Ship> getAllShips() {
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

}
