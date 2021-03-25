package com.company.spacetrans.screen.waybill;

import com.company.spacetrans.entity.*;
import com.company.spacetrans.screen.company.CompanyBrowse;
import com.company.spacetrans.screen.individual.IndividualBrowse;
import com.company.spacetrans.service.*;
import io.jmix.core.Messages;
import io.jmix.ui.RemoveOperation;
import io.jmix.ui.action.entitypicker.EntityClearAction;
import io.jmix.ui.action.entitypicker.EntityLookupAction;
import io.jmix.ui.component.*;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@UiController("st_Waybill.edit")
@UiDescriptor("waybill-edit.xml")
@EditedEntityContainer("waybillDc")
public class WaybillEdit extends StandardEditor<Waybill> implements PropertyChangeListener {

    @Autowired
    private EntityComboBox<Customer> shipperField;

    @Named("shipperField.clear")
    private EntityClearAction shipperFieldClear;

    @Autowired
    private EntityPicker<Customer> consigneeField;

    @Named("consigneeField.clear")
    private EntityClearAction consigneeFieldClear;

    @Named("consigneeField.lookup")
    private EntityLookupAction<Customer> consigneeFieldLookup;

    @Autowired
    private IndividualService individualService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private Messages messages;

    @Autowired
    private EntityComboBox<AstronomicalBody> departureField;

    @Autowired
    private EntityPicker<Spaceport> departurePortField;

    @Autowired
    private EntityComboBox<AstronomicalBody> destinationField;

    @Autowired
    private EntityPicker<Spaceport> destinationPortField;

    @Autowired
    private PlanetService planetService;

    @Autowired
    private MoonService moonService;

    @Autowired
    private SpaceportService spaceportService;

    @Named("carrierField.clear")
    private EntityClearAction carrierFieldClear;

    @Autowired
    private EntityComboBox<Carrier> carrierField;

    @Autowired
    private CarrierService carrierService;

    @Autowired
    private TextField<Double> totalWeightField;

    @Autowired
    private TextField<BigDecimal> totalChargeField;

    @Autowired
    private Button upButton;

    @Autowired
    private Button downButton;

    @Autowired
    private Table<WaybillItem> itemsTable;

    @Autowired
    private WaybillItemService waybillItemService;

    @Autowired
    private WaybillChangedListener waybillChangedListener;

    public WaybillEdit() {
        addAfterInitListener(event -> waybillChangedListener.addPropertyChangeListener(this));
    }

    @Subscribe
    public void onInit(InitEvent event) {
        List<AstronomicalBody> astronomicalBodies = new ArrayList<>(planetService.findAll());
        astronomicalBodies.addAll(moonService.findAll());

        departureField.setOptionsList(astronomicalBodies);
        destinationField.setOptionsList(astronomicalBodies);
    }

    @Subscribe("departureField")
    public void onDepartureFieldValueChange(HasValue.ValueChangeEvent<AstronomicalBody> event) {
        onAstronomicalBodyFieldChanged(event, departurePortField);
    }

    @Subscribe("destinationField")
    public void onDestinationFieldValueChange(HasValue.ValueChangeEvent<AstronomicalBody> event) {
        onAstronomicalBodyFieldChanged(event, destinationPortField);
    }

    private void onAstronomicalBodyFieldChanged(HasValue.ValueChangeEvent<AstronomicalBody> event, EntityPicker<Spaceport> destinationPortField) {
        if (!event.isUserOriginated()) {
            return;
        }

        AstronomicalBody destination = event.getValue();
        if (destination != null) {
            Spaceport defaultSpaceport = spaceportService.findDefaultSpaceport(destination);
            if (destinationPortField.getValue() == null || !destinationPortField.getValue().equals(defaultSpaceport)) {
                destinationPortField.setValue(defaultSpaceport);
            }
        }
    }

    @Subscribe("departurePortField")
    public void onDeparturePortFieldValueChange(HasValue.ValueChangeEvent<Spaceport> event) {
        onSpaceportFieldChanged(event, departureField);
    }

    @Subscribe("destinationPortField")
    public void onDestinationPortFieldValueChange(HasValue.ValueChangeEvent<Spaceport> event) {
        onSpaceportFieldChanged(event, destinationField);
    }

    private void onSpaceportFieldChanged(HasValue.ValueChangeEvent<Spaceport> event, EntityComboBox<AstronomicalBody> departureField) {
        updateCarriersField();

        if (!event.isUserOriginated()) {
            return;
        }

        Spaceport departurePort = event.getValue();
        if (departurePort != null) {
            AstronomicalBody currentDeparture = departureField.getValue();
            AstronomicalBody astronomicalBodyForNewPort = spaceportService.getAstronomicalBody(departurePort);
            if (currentDeparture == null || !currentDeparture.equals(astronomicalBodyForNewPort)) {
                departureField.setValue(astronomicalBodyForNewPort);
            }
        }
    }

    private void updateCarriersField() {
        carrierField.setEditable(departurePortField.getValue() != null && destinationPortField.getValue() != null);
        if (carrierField.isEditable()) {
            carrierField.setOptionsList(carrierService.findSuitableCarriers(departurePortField.getValue(), destinationPortField.getValue()));
            carrierField.setDescription(messages.getMessage("com.company.spacetrans.screen.waybill/CarriersSelectCarrier"));
        } else {
            carrierFieldClear.execute();
            carrierField.setDescription(messages.getMessage("com.company.spacetrans.screen.waybill/CarriersSelectAllPortsAtFirst"));
        }
    }

    @Subscribe("shipperTypeChooser")
    @SuppressWarnings("unchecked")
    public void onShipperTypeChooserValueChange(HasValue.ValueChangeEvent<CustomerType> event) {
        CustomerType ct = event.getValue();
        if (ct != null) {
            shipperField.setEditable(true);
            switch (ct) {
                case INDIVIDUAL:
                    shipperField.setOptionsList((List) individualService.findAll());
                    shipperField.setDescription(messages.getMessage("com.company.spacetrans.screen.waybill/ShipperEntityComboboxIndividual"));
                    if (shipperField.getValue() != null && !(shipperField.getValue() instanceof Individual)) {
                        shipperFieldClear.execute();
                    }
                    break;

                case COMPANY:
                    shipperField.setOptionsList((List) companyService.findAll());
                    shipperField.setDescription(messages.getMessage("com.company.spacetrans.screen.waybill/ShipperEntityComboboxCompany"));
                    if (shipperField.getValue() != null && !(shipperField.getValue() instanceof Company)) {
                        shipperFieldClear.execute();
                    }
                    break;

                default:
                    throw new RuntimeException("Unreachable statement");
            }
        }
    }

    @Subscribe("consigneeTypeChooser")
    public void onConsigneeTypeChooserValueChange(HasValue.ValueChangeEvent<CustomerType> event) {
        CustomerType ct = event.getValue();
        if (ct != null) {
            consigneeField.setEditable(true);
            switch (ct) {
                case INDIVIDUAL:
                    consigneeFieldLookup.setScreenClass(IndividualBrowse.class);
                    consigneeField.setDescription(messages.getMessage("com.company.spacetrans.screen.waybill/ConsigneeEntityPickerIndividual"));
                    if (consigneeField.getValue() != null && !(consigneeField.getValue() instanceof Individual)) {
                        consigneeFieldClear.execute();
                    }
                    break;

                case COMPANY:
                    consigneeFieldLookup.setScreenClass(CompanyBrowse.class);
                    consigneeField.setDescription(messages.getMessage("com.company.spacetrans.screen.waybill/ConsigneeEntityPickerCompany"));
                    if (consigneeField.getValue() != null && !(consigneeField.getValue() instanceof Company)) {
                        consigneeFieldClear.execute();
                    }
                    break;

                default:
                    throw new RuntimeException("Unreachable statement");
            }
        }
    }

    @Subscribe("itemsTable")
    public void onItemsTableSelection(Table.SelectionEvent<WaybillItem> event) {
        upButton.setEnabled(!event.getSelected().isEmpty());
        downButton.setEnabled(!event.getSelected().isEmpty());
    }


    @Subscribe("upButton")
    public void onUpButtonClick(Button.ClickEvent event) {
        Iterator<WaybillItem> iterator = itemsTable.getSelected().iterator();
        if (iterator.hasNext()) {
            waybillItemService.updateWaybillItemNumber(iterator.next(), WaybillItemService.IncDec.DECREMENT);
            itemsTable.repaint();
        }
    }

    @Subscribe("downButton")
    public void onDownButtonClick(Button.ClickEvent event) {
        Iterator<WaybillItem> iterator = itemsTable.getSelected().iterator();
        if (iterator.hasNext()) {
            waybillItemService.updateWaybillItemNumber(iterator.next(), WaybillItemService.IncDec.INCREMENT);
            itemsTable.repaint();
        }
    }

    @Subscribe("removeButton")
    public void onRemoveButtonClick(Button.ClickEvent event) {
        Iterator<WaybillItem> iterator = itemsTable.getSelected().iterator();
        if (iterator.hasNext()) {
            waybillItemService.deleteItem(iterator.next());
        }
    }

    @Subscribe("shipperField")
    public void onShipperFieldValueChange(HasValue.ValueChangeEvent<Customer> event) {
        waybillItemService.updateTotals(getEditedEntity());
    }

    @Install(to = "itemsTable.create", subject = "afterCommitHandler")
    private void itemsTableCreateAfterCommitHandler(WaybillItem waybillItem) {
        waybillItemService.updateTotals(getEditedEntity());
    }

    @Install(to = "itemsTable.remove", subject = "afterActionPerformedHandler")
    private void itemsTableRemoveAfterActionPerformedHandler(RemoveOperation.AfterActionPerformedEvent<WaybillItem> afterActionPerformedEvent) {
        waybillItemService.updateTotals(getEditedEntity());
    }

    @Install(to = "itemsTable.edit", subject = "afterCommitHandler")
    private void itemsTableEditAfterCommitHandler(WaybillItem waybillItem) {
        waybillItemService.updateTotals(getEditedEntity());
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        getEditedEntityLoader().load();
    }
}