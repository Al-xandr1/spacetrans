package com.company.spacetrans.service;

import com.company.spacetrans.entity.Waybill;
import io.jmix.core.DataManager;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class WaybillItemRepository {

    private final DataManager dataManager;

    public WaybillItemRepository(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public Integer generateNumber(Waybill waybill, Integer defaultNum) {
        return findMaxNumber(waybill)
                .map(num -> num + 1)
                .orElse(defaultNum);
    }

    private Optional<Integer> findMaxNumber(Waybill waybill) {
        return Optional.ofNullable(waybill)
                       .flatMap(w -> dataManager
                               .loadValue("SELECT max(w.number) FROM st_WaybillItem w WHERE w.waybill = :waybill GROUP BY w.waybill", Integer.class)
                               .parameter("waybill", w)
                               .optional()
                       );
    }
}
