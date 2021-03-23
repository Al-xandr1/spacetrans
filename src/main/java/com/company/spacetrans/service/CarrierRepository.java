package com.company.spacetrans.service;

import com.company.spacetrans.entity.Carrier;
import com.company.spacetrans.entity.Spaceport;
import io.jmix.core.DataManager;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarrierRepository {

    private final DataManager dataManager;

    public CarrierRepository(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public List<Carrier> findSuitableCarriers(Spaceport first, Spaceport second) {
        //todo HIGH FIX IT!!!! to db query
        //       List<Carrier> list = dataManager.load(Carrier.class)
        //                                .query("SELECT DISTINCT c FROM st_Carrier c INNER JOIN FETCH c.ports p WHERE p.name IS NOT NULL ")
        //                                .parameter("ports", List.of(first, second))
        //                                .list();
        return dataManager.load(Carrier.class)
                          .query("SELECT DISTINCT c FROM st_Carrier c JOIN FETCH c.ports ")
                          .list()
                          .stream()
                          .filter(carrier -> carrier.getPorts().contains(first) && carrier.getPorts().contains(second))
                          .collect(Collectors.toList());
    }
}
