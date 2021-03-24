package com.company.spacetrans.service;

import com.company.spacetrans.entity.Waybill;
import com.company.spacetrans.entity.WaybillItem;
import io.jmix.core.DataManager;
import io.jmix.core.common.util.Preconditions;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component
public class WaybillItemRepository {

    public static final int START_NUM = 1;

    public static final Function<Integer, Integer> NUM_GEN = num -> num + 1;

    private final DataManager dataManager;


    public WaybillItemRepository(@NotNull DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @NotNull
    public Integer generateNextNumber(@NotNull Waybill waybill) {
        return generateNextNumber(waybill, NUM_GEN);
    }

    @NotNull
    public Integer generateNextNumber(@NotNull Waybill waybill, Function<Integer, Integer> numberGenerator) {
        return findMaxNumber(waybill)
                .map(numberGenerator)
                .orElse(START_NUM);
    }

    @NotNull
    private Optional<Integer> findMaxNumber(@NotNull Waybill waybill) {
        return Optional.of(waybill)
                       .flatMap(w -> w.getItems()
                                      .stream()
                                      .map(WaybillItem::getNumber)
                                      .max(Integer::compareTo)
                       );
    }

    @NotNull
    public Integer generateNumberDb(@NotNull Waybill waybill) {
        return findMaxNumberDb(waybill)
                .map(NUM_GEN)
                .orElse(START_NUM);
    }

    @NotNull
    private Optional<Integer> findMaxNumberDb(@NotNull Waybill waybill) {
        return Optional.of(waybill)
                       .flatMap(w -> dataManager
                               .loadValue("SELECT max(w.number) FROM st_WaybillItem w WHERE w.waybill = :waybill GROUP BY w.waybill", Integer.class)
                               .parameter("waybill", w)
                               .optional()
                       );
    }

    public void updateWaybillItemNumber(@NotNull WaybillItem waybillItem, @NotNull IncDec incDec) {
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

    public void deleteItem(@NotNull WaybillItem item) {
        item.getWaybill().getItems().stream().filter(i -> i.getNumber() > item.getNumber()).forEach(i -> i.setNumber(i.getNumber() - 1));
    }

    public enum IncDec {
        INCREMENT, DECREMENT
    }
}
