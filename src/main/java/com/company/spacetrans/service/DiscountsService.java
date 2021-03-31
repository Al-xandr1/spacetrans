package com.company.spacetrans.service;

import com.company.spacetrans.entity.CustomerGrade;
import com.company.spacetrans.entity.Discounts;
import io.jmix.core.DataManager;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DiscountsService {

    private final DataManager dataManager;

    public DiscountsService(DataManager dataManager) {
        this.dataManager = dataManager;
    }


    public Optional<Discounts> findDiscount(CustomerGrade grade) {
        return dataManager.load(Discounts.class)
                          .query("select d from st_Discounts d where d.grade = :grade")
                          .parameter("grade", grade)
                          .optional();
    }
}
