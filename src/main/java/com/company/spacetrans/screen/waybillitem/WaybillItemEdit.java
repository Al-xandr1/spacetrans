package com.company.spacetrans.screen.waybillitem;

import com.company.spacetrans.entity.Waybill;
import com.company.spacetrans.entity.WaybillItem;
import com.company.spacetrans.service.WaybillItemRepository;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.TextField;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("st_WaybillItem.edit")
@UiDescriptor("waybill-item-edit.xml")
@EditedEntityContainer("waybillItemDc")
public class WaybillItemEdit extends StandardEditor<WaybillItem> {

    @Autowired
    private TextField<Integer> numberField;

    @Autowired
    private WaybillItemRepository waybillItemRepository;

    @Autowired
    private Button upButton;

    @Autowired
    private Button downButton;

    @Subscribe
    public void onInitEntity(InitEntityEvent<WaybillItem> event) {
        WaybillItem newEntity = event.getEntity();
        if (newEntity.getWaybill() != null) {
            Integer number = waybillItemRepository.generateNextNumber(newEntity.getWaybill());
            newEntity.setNumber(number);
        }
    }

    @Subscribe("waybillField")
    public void onWaybillFieldValueChange(HasValue.ValueChangeEvent<Waybill> event) {
        Waybill newWaybill = event.getValue();
        if (newWaybill != null) {
            Integer number = waybillItemRepository.generateNextNumber(newWaybill);
            numberField.setValue(number);
        } else {
            numberField.setValue(null);
        }
    }

    @Subscribe("numberField")
    public void onNumberFieldValueChange(HasValue.ValueChangeEvent<Integer> event) {
        upButton.setEnabled(event.getValue() != null);
        downButton.setEnabled(event.getValue() != null);
    }

    @Subscribe("upButton")
    public void onUpButtonClick(Button.ClickEvent event) {
        WaybillItem waybillItem = getEditedEntity();
        waybillItemRepository.updateWaybillItemNumber(waybillItem, WaybillItemRepository.IncDec.DECREMENT);
    }

    @Subscribe("downButton")
    public void onDownButtonClick(Button.ClickEvent event) {
        WaybillItem waybillItem = getEditedEntity();
        waybillItemRepository.updateWaybillItemNumber(waybillItem, WaybillItemRepository.IncDec.INCREMENT);
    }
}
