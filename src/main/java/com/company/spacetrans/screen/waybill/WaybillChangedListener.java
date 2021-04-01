package com.company.spacetrans.screen.waybill;

import com.company.spacetrans.entity.Waybill;
import com.company.spacetrans.entity.WaybillItem;
import com.company.spacetrans.service.WaybillItemService;
import io.jmix.core.DataManager;
import io.jmix.core.event.EntityChangedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class WaybillChangedListener {

    public static final String UNKNOWN_CHANGED = "unknown";

    private final DataManager dataManager;

    private final WaybillItemService waybillItemService;

    public WaybillChangedListener(DataManager dataManager, WaybillItemService waybillItemService) {
        this.dataManager = dataManager;
        this.waybillItemService = waybillItemService;
    }

    @EventListener
    public void waybillChanged(EntityChangedEvent<Waybill> event) {
        dataManager.load(event.getEntityId().getEntityClass())
                   .id(event.getEntityId().getValue())
                   .optional()
                   .ifPresent(waybillItemService::updateTotals);
    }

    @EventListener
    public void waybillItemChanged(EntityChangedEvent<WaybillItem> event) {
        dataManager.load(event.getEntityId().getEntityClass())
                   .id(event.getEntityId().getValue())
                   .optional()
                   .ifPresent(item -> waybillItemService.updateTotals(item.getWaybill()));
    }
}
