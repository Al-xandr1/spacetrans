package com.company.spacetrans.service;

import com.company.spacetrans.entity.Company;
import io.jmix.core.DataManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompanyService {

    private final DataManager dataManager;

    public CompanyService(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public List<Company> findAll() {
        return dataManager.load(Company.class).all().list();
    }
}
