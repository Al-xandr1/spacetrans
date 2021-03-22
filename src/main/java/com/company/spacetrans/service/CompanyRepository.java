package com.company.spacetrans.service;

import com.company.spacetrans.entity.Company;
import io.jmix.core.DataManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompanyRepository {

    private final DataManager dataManager;

    public CompanyRepository(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public List<Company> findAll() {
        return dataManager.load(Company.class).all().list();
    }
}
