package com.company.spacetrans.service.utility;

import com.company.spacetrans.entity.WaybillItem;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class ChargeUtility {


    public static final int VOLUME_UNIT_COAST = 10;

    public static final int WEIGHT_UNIT_COAST = 5;

    private ChargeUtility() {
    }

    public static BigDecimal calculateCharge(@NotNull WaybillItem item) {
        BigDecimal charge = null;
        if (item.getWeight() != null && item.getDim().isFilled()) {
            charge = new BigDecimal(item.getWeight() * WEIGHT_UNIT_COAST + item.getDim().getVolume() * VOLUME_UNIT_COAST);
        }
        return charge;
    }
}
