package com.company.spacetrans;

import com.company.spacetrans.entity.*;
import com.company.spacetrans.service.DiscountsService;
import com.company.spacetrans.service.WaybillItemService;
import io.jmix.core.DataManager;
import io.jmix.core.FluentLoader;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class SpacetransApplicationTests {

    public static final int SCALE = 6;

    @Test
    void newWaybill() {
        WaybillItemService waybillItemService = buildWaybillItemService();

        Waybill waybill = createWaybill(waybillItemService);
        waybillItemService.updateTotals(waybill);

        Assertions.assertEquals(scaled(BigDecimal.valueOf(2.75)), scaled(waybill.getTotalCharge()));
        Assertions.assertEquals(5.0, waybill.getTotalWeight());
        for (WaybillItem item : waybill.getItems()) {
            Assertions.assertEquals(scaled(BigDecimal.valueOf(1.1)), scaled(item.getCharge()));
        }
    }

    @Test
    void addWaybillItem() {
        WaybillItemService waybillItemService = buildWaybillItemService();

        Waybill waybill = createWaybill(waybillItemService);
        waybillItemService.updateTotals(waybill);

        waybill.getItems().add(createWaybillItem(waybill, "1", 1.0, 1.0, 1.0, 1.0, waybillItemService));
        waybillItemService.updateTotals(waybill);

        Assertions.assertEquals(scaled(BigDecimal.valueOf(3.3)), scaled(waybill.getTotalCharge()));
        Assertions.assertEquals(6.0, waybill.getTotalWeight());
        for (WaybillItem item : waybill.getItems()) {
            Assertions.assertEquals(scaled(BigDecimal.valueOf(1.1)), scaled(item.getCharge()));
        }
    }

    @Test
    void changeExistedWaybillItem() {
        WaybillItemService waybillItemService = buildWaybillItemService();

        Waybill waybill = createWaybill(waybillItemService);
        waybillItemService.updateTotals(waybill);

        WaybillItem changed = waybill.getItems().iterator().next();
        changed.setDim(new Dimensions() {{
            setLength(2.0);
            setWidth(2.0);
            setHeight(2.0);
        }});
        changed.setWeight(10.0);
        waybillItemService.updateTotals(waybill);

        Assertions.assertEquals(scaled(BigDecimal.valueOf(7.6)), scaled(waybill.getTotalCharge()));
        Assertions.assertEquals(14.0, waybill.getTotalWeight());
        for (WaybillItem item : waybill.getItems()) {
            if (item == changed) {
                Assertions.assertEquals(scaled(BigDecimal.valueOf(10.8)), scaled(item.getCharge()));
            } else {
                Assertions.assertEquals(scaled(BigDecimal.valueOf(1.1)), scaled(item.getCharge()));
            }
        }
    }

    @NotNull
    private static BigDecimal scaled(@NotNull BigDecimal decimal) {
        return decimal.setScale(SCALE, RoundingMode.HALF_UP);
    }

    @NotNull
    private static Waybill createWaybill(@NotNull WaybillItemService waybillItemService) {
        Waybill waybill = new Waybill();
        waybill.setReference("12345");
        waybill.setCreator(new User());
        waybill.setShipper(new Individual());
        waybill.setConsignee(new Individual());
        waybill.setDeparturePort(new Spaceport());
        waybill.setDestinationPort(new Spaceport());
        waybill.setCarrier(new Carrier());
        waybill.setItems(new ArrayList<>() {
            {
                add(createWaybillItem(waybill, "1", 1.0, 1.0, 1.0, 1.0, waybillItemService));
                add(createWaybillItem(waybill, "1", 1.0, 1.0, 1.0, 1.0, waybillItemService));
                add(createWaybillItem(waybill, "1", 1.0, 1.0, 1.0, 1.0, waybillItemService));
                add(createWaybillItem(waybill, "1", 1.0, 1.0, 1.0, 1.0, waybillItemService));
                add(createWaybillItem(waybill, "1", 1.0, 1.0, 1.0, 1.0, waybillItemService));
            }
        });
        return waybill;
    }

    @NotNull
    private static WaybillItem createWaybillItem(@NotNull Waybill waybill,
                                                 @NotNull String name,
                                                 double weight,
                                                 double height,
                                                 double width,
                                                 double length,
                                                 @NotNull WaybillItemService waybillItemService) {
        WaybillItem item = new WaybillItem();
        item.setNumber(waybillItemService.generateNextNumber(waybill));
        item.setName(name);
        item.setWeight(weight);
        Dimensions dim = new Dimensions();
        dim.setHeight(height);
        dim.setWidth(width);
        dim.setLength(length);
        item.setDim(dim);
        item.setWaybill(waybill);
        return item;
    }

    @NotNull
    @SuppressWarnings("unchecked")
    private static WaybillItemService buildWaybillItemService() {
        DataManager dataManager = mock(DataManager.class);
        FluentLoader<Discounts> fluentLoader = mock(FluentLoader.class);
        FluentLoader.ByQuery<Discounts> byQuery = mock(FluentLoader.ByQuery.class);
        Optional<Discounts> optional = Optional.of(new Discounts() {{
            setGrade(CustomerGrade.SILVER);
            setValue(BigDecimal.valueOf(50));
        }});

        when(dataManager.load(Mockito.<Class<Discounts>>any())).thenReturn(fluentLoader);
        when(fluentLoader.query(any())).thenReturn(byQuery);
        when(byQuery.parameter(any(), any())).thenReturn(byQuery);
        when(byQuery.optional()).thenReturn(optional);

        return new WaybillItemService(dataManager, new DiscountsService(dataManager));
    }
}
