package com.company.spacetrans.screen.waybill;

import com.company.spacetrans.entity.*;
import com.company.spacetrans.screen.company.CompanyBrowse;
import com.company.spacetrans.screen.individual.IndividualBrowse;
import com.company.spacetrans.security.DatabaseUserRepository;
import com.company.spacetrans.service.*;
import com.vaadin.server.VaadinServletRequest;
import io.jmix.core.Messages;
import io.jmix.core.metamodel.model.MetaPropertyPath;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.entitypicker.EntityClearAction;
import io.jmix.ui.action.entitypicker.EntityLookupAction;
import io.jmix.ui.component.EntityComboBox;
import io.jmix.ui.component.EntityPicker;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.Table;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@UiController("st_Waybill.edit")
@UiDescriptor("waybill-edit.xml")
@EditedEntityContainer("waybillDc")
public class WaybillEdit extends StandardEditor<Waybill> {

    @Autowired
    private EntityComboBox<Customer> shipperField;

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
    private Table<WaybillItem> itemsTable;

    @Autowired
    private WaybillItemService waybillItemService;

    @Autowired
    private DatabaseUserRepository databaseUserRepository;

    @Subscribe
    public void onInit(InitEvent event) {
        List<AstronomicalBody> astronomicalBodies = new ArrayList<>(planetService.findAll());
        astronomicalBodies.addAll(moonService.findAll());

        departureField.setOptionsList(astronomicalBodies);
        destinationField.setOptionsList(astronomicalBodies);
    }

    @Subscribe
    public void onInitEntity(InitEntityEvent<Waybill> event) {
        Principal userPrincipal = VaadinServletRequest.getCurrent().getUserPrincipal();
        User currentUser = databaseUserRepository.loadUserByUsername(userPrincipal.getName());
        event.getEntity().setCreator(currentUser);
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
                        getEditedEntity().setShipper(null);
                    }
                    break;

                case COMPANY:
                    shipperField.setOptionsList((List) companyService.findAll());
                    shipperField.setDescription(messages.getMessage("com.company.spacetrans.screen.waybill/ShipperEntityComboboxCompany"));
                    if (shipperField.getValue() != null && !(shipperField.getValue() instanceof Company)) {
                        getEditedEntity().setShipper(null);
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

    @Subscribe("itemsTable.up")
    public void onItemsTableUp(Action.ActionPerformedEvent event) {
        Iterator<WaybillItem> iterator = itemsTable.getSelected().iterator();
        if (iterator.hasNext()) {
            WaybillItem waybillItem = iterator.next();
            waybillItemService.updateWaybillItemNumber(waybillItem, WaybillItemService.IncDec.DECREMENT);
            updateRepresentation(waybillItem);
        }
    }

    @Subscribe("itemsTable.down")
    public void onItemsTableDown(Action.ActionPerformedEvent event) {
        Iterator<WaybillItem> iterator = itemsTable.getSelected().iterator();
        if (iterator.hasNext()) {
            WaybillItem waybillItem = iterator.next();
            waybillItemService.updateWaybillItemNumber(waybillItem, WaybillItemService.IncDec.INCREMENT);
            updateRepresentation(waybillItem);
        }
    }

    private void updateRepresentation(WaybillItem waybillItem) {
        Table.SortInfo sortInfo = itemsTable.getSortInfo();
        if (sortInfo != null) {
            if (Arrays.asList(((MetaPropertyPath) sortInfo.getPropertyId()).getPath()).contains(WaybillItem.NUMBER)) {
                itemsTable.sort(WaybillItem.NUMBER, sortInfo.getAscending() ? Table.SortDirection.ASCENDING : Table.SortDirection.DESCENDING);
            }
        } else {
            itemsTable.sort(WaybillItem.NUMBER, Table.SortDirection.ASCENDING);
        }
        itemsTable.requestFocus(waybillItem, WaybillItem.NUMBER);
    }

    @Subscribe("shipperField")
    public void onShipperFieldValueChange(HasValue.ValueChangeEvent<Customer> event) {
        waybillItemService.updateTotals(getEditedEntity());
    }

    @Subscribe(id = "itemsDc", target = Target.DATA_CONTAINER)
    public void onItemsDcCollectionChange(CollectionContainer.CollectionChangeEvent<WaybillItem> event) {
        switch (event.getChangeType()) {
            case REMOVE_ITEMS: {
                event.getChanges().forEach(waybillItemService::updateWaybillItemNumbers);
            }
            case SET_ITEM:
            case ADD_ITEMS: {
                waybillItemService.updateTotals(getEditedEntity());
            }
        }
    }
}