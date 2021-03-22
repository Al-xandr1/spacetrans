package com.company.spacetrans.service;

import com.company.spacetrans.entity.Individual;
import io.jmix.core.DataManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IndividualRepository {

    private final DataManager dataManager;

    public IndividualRepository(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public List<Individual> findAll() {
        return dataManager.load(Individual.class).all().list();
    }
}
