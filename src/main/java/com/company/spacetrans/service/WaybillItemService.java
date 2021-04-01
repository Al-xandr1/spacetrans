package com.company.spacetrans.service;

import com.company.spacetrans.entity.Waybill;
import com.company.spacetrans.entity.WaybillItem;
import io.jmix.core.DataManager;
import io.jmix.core.common.util.Preconditions;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.function.Function;

@Component
public class WaybillItemService {

    public static final int START_NUM = 1;

    public static final Function<Integer, Integer> NUM_GEN = num -> num + 1;

    public static final double VOLUME_UNIT_COAST = 0.1;

    public static final double WEIGHT_UNIT_COAST = 1;

    private final DataManager dataManager;

    private final DiscountsService discountsService;


    public WaybillItemService(DataManager dataManager, DiscountsService discountsService) {
        this.dataManager = dataManager;
        this.discountsService = discountsService;
    }

    @Nullable
    public static BigDecimal calculateCharge(WaybillItem item) {
        BigDecimal charge = null;
        if (item.getWeight() != null && item.getDim().isFilled()) {
            charge = new BigDecimal(item.getWeight() * WEIGHT_UNIT_COAST + item.getDim().getVolume() * VOLUME_UNIT_COAST);
        }
        return charge;
    }

    public Integer generateNextNumber(Waybill waybill) {
        return generateNextNumber(waybill, NUM_GEN);
    }


    public Integer generateNextNumber(Waybill waybill, Function<Integer, Integer> numberGenerator) {
        return findMaxNumber(waybill)
                .map(numberGenerator)
                .orElse(START_NUM);
    }


    private Optional<Integer> findMaxNumber(Waybill waybill) {
        return Optional.of(waybill)
                       .map(Waybill::getItems)
                       .flatMap(items -> items.stream()
                                              .map(WaybillItem::getNumber)
                                              .max(Integer::compareTo)
                       );
    }


    public Integer generateNumberDb(Waybill waybill) {
        return findMaxNumberDb(waybill)
                .map(NUM_GEN)
                .orElse(START_NUM);
    }


    private Optional<Integer> findMaxNumberDb(Waybill waybill) {
        return Optional.of(waybill)
                       .flatMap(w -> dataManager
                               .loadValue("SELECT max(w.number) FROM st_WaybillItem w WHERE w.waybill = :waybill GROUP BY w.waybill", Integer.class)
                               .parameter("waybill", w)
                               .optional()
                       );
    }

    public void updateWaybillItemNumber(WaybillItem waybillItem, IncDec incDec) {
        final Integer currentNumber = waybillItem.getNumber();
        Preconditions.checkNotNullArgument(currentNumber);

        final Integer newNumber;
        switch (incDec) {
            case INCREMENT:
                int maxNumber = generateNextNumber(waybillItem.getWaybill(), Function.identity());
                newNumber = Math.min(currentNumber + 1, maxNumber);
                break;

            case DECREMENT:
                newNumber = Math.max(currentNumber - 1, START_NUM);
                break;

            default:
                throw new RuntimeException("Unreachable statement");
        }

        waybillItem.setNumber(newNumber);
        waybillItem.getWaybill()
                   .getItems()
                   .stream()
                   .filter(item -> newNumber.equals(item.getNumber()) && !item.getId().equals(waybillItem.getId()))
                   .findAny()
                   .ifPresent(item -> item.setNumber(currentNumber));
    }

    public void updateWaybillItemNumbers(WaybillItem removed) {
        removed.getWaybill().getItems().stream().filter(i -> i.getNumber() > removed.getNumber()).forEach(i -> i.setNumber(i.getNumber() - 1));
    }

    public void updateCharge(WaybillItem item) {
        item.setCharge(calculateCharge(item));
    }

    public void updateTotals(Waybill waybill) {
        double totalWeight = 0;
        BigDecimal accumulatedCharge = new BigDecimal(0);
        if (waybill.getItems() != null) {
            for (WaybillItem item : waybill.getItems()) {
                totalWeight += item.getWeight();
                updateCharge(item);
                accumulatedCharge = accumulatedCharge.add(item.getCharge());
            }
            waybill.setTotalWeight(totalWeight);

            final BigDecimal totalCharge = accumulatedCharge;
            waybill.setTotalCharge(Optional.ofNullable(waybill.getShipper())
                                           .flatMap(shipper -> discountsService.findDiscount(shipper.getGrade()))
                                           .map(discounts -> BigDecimal.valueOf(100)
                                                                       .subtract(discounts.getValue())
                                                                       .max(BigDecimal.valueOf(0))
                                                                       .multiply(totalCharge)
                                                                       .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP))
                                           .orElse(totalCharge)
            );
        }
    }

    public enum IncDec {
        INCREMENT, DECREMENT
    }
}
