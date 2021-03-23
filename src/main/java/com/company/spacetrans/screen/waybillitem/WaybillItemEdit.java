package com.company.spacetrans.screen.waybillitem;

import com.company.spacetrans.entity.Waybill;
import com.company.spacetrans.entity.WaybillItem;
import com.company.spacetrans.service.WaybillItemRepository;
import io.jmix.core.common.util.Preconditions;
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
        Integer number = waybillItemRepository.generateNumber(newEntity.getWaybill(), null);
        newEntity.setNumber(number);
    }

    @Subscribe("waybillField")
    public void onWaybillFieldValueChange(HasValue.ValueChangeEvent<Waybill> event) {
        Waybill newWaybill = event.getValue();
        if (newWaybill != null) {
            Integer number = waybillItemRepository.generateNumber(newWaybill, 0);
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
        updateWaybillItemNumber(waybillItem, IncDec.DECREMENT);
    }

    @Subscribe("downButton")
    public void onDownButtonClick(Button.ClickEvent event) {
        WaybillItem waybillItem = getEditedEntity();
        updateWaybillItemNumber(waybillItem, IncDec.INCREMENT);
    }

    private void updateWaybillItemNumber(WaybillItem waybillItem, IncDec incDec) {
        Integer currentNumber = waybillItem.getNumber();
        Preconditions.checkNotNullArgument(currentNumber);

        Integer newNumber;
        switch (incDec) {
            case INCREMENT:
                newNumber = currentNumber + 1;
                break;

            case DECREMENT:
                newNumber = Math.max(currentNumber - 1, 0);
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

    private enum IncDec {
        INCREMENT, DECREMENT
    }
}
