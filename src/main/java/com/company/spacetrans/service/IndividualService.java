package com.company.spacetrans.service;

import com.company.spacetrans.entity.Individual;
import io.jmix.core.DataManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IndividualService {

    private final DataManager dataManager;

    public IndividualService(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public List<Individual> findAll() {
        return dataManager.load(Individual.class).all().list();
    }
}
