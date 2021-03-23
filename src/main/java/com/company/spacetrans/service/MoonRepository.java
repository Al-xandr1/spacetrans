package com.company.spacetrans.service;

import com.company.spacetrans.entity.Moon;
import io.jmix.core.DataManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MoonRepository {

    private final DataManager dataManager;

    public MoonRepository(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public List<Moon> findAll() {
        return dataManager.load(Moon.class).all().list();
    }
}
