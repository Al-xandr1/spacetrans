package com.company.spacetrans.screen.waybill;

import com.company.spacetrans.entity.*;
import com.company.spacetrans.screen.company.CompanyBrowse;
import com.company.spacetrans.screen.individual.IndividualBrowse;
import com.company.spacetrans.service.*;
import io.jmix.core.Messages;
import io.jmix.ui.action.entitypicker.EntityClearAction;
import io.jmix.ui.action.entitypicker.EntityLookupAction;
import io.jmix.ui.component.EntityComboBox;
import io.jmix.ui.component.EntityPicker;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.TextField;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@UiController("st_Waybill.edit")
@UiDescriptor("waybill-edit.xml")
@EditedEntityContainer("waybillDc")
public class WaybillEdit extends StandardEditor<Waybill> {

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
    private IndividualRepository individualRepository;

    @Autowired
    private CompanyRepository companyRepository;

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
    private PlanetRepository planetRepository;

    @Autowired
    private MoonRepository moonRepository;

    @Autowired
    private SpaceportRepository spaceportRepository;


    @Named("carrierField.clear")
    private EntityClearAction carrierFieldClear;

    @Autowired
    private EntityComboBox<Carrier> carrierField;

    @Autowired
    private CarrierRepository carrierRepository;

    @Autowired
    private TextField<Double> totalWeightField;

    @Autowired
    private TextField<BigDecimal> totalChargeField;

    @Subscribe
    public void onInit(InitEvent event) {
        List<AstronomicalBody> astronomicalBodies = new ArrayList<>(planetRepository.findAll());
        astronomicalBodies.addAll(moonRepository.findAll());

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
            Spaceport defaultSpaceport = spaceportRepository.findDefaultSpaceport(destination);
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
            AstronomicalBody astronomicalBodyForNewPort = spaceportRepository.getAstronomicalBody(departurePort);
            if (currentDeparture == null || !currentDeparture.equals(astronomicalBodyForNewPort)) {
                departureField.setValue(astronomicalBodyForNewPort);
            }
        }
    }

    private void updateCarriersField() {
        carrierField.setEditable(departurePortField.getValue() != null && destinationPortField.getValue() != null);
        if (carrierField.isEditable()) {
            carrierField.setOptionsList(carrierRepository.findSuitableCarriers(departurePortField.getValue(), destinationPortField.getValue()));
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
                    shipperField.setOptionsList((List) individualRepository.findAll());
                    shipperField.setDescription(messages.getMessage("com.company.spacetrans.screen.waybill/ShipperEntityComboboxIndividual"));
                    if (shipperField.getValue() != null && !(shipperField.getValue() instanceof Individual)) {
                        shipperFieldClear.execute();
                    }
                    break;

                case COMPANY:
                    shipperField.setOptionsList((List) companyRepository.findAll());
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
}