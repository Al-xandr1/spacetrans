package com.company.spacetrans.service;

import com.company.spacetrans.entity.Moon;
import io.jmix.core.DataManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MoonService {

    private final DataManager dataManager;

    public MoonService(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public List<Moon> findAll() {
        return dataManager.load(Moon.class).all().list();
    }
}
