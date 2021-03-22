package com.company.spacetrans.screen.waybill;

import com.company.spacetrans.entity.Company;
import com.company.spacetrans.entity.Customer;
import com.company.spacetrans.entity.Individual;
import com.company.spacetrans.entity.Waybill;
import com.company.spacetrans.screen.company.CompanyBrowse;
import com.company.spacetrans.screen.individual.IndividualBrowse;
import com.company.spacetrans.service.CompanyRepository;
import com.company.spacetrans.service.IndividualRepository;
import io.jmix.core.Messages;
import io.jmix.ui.action.entitypicker.EntityClearAction;
import io.jmix.ui.action.entitypicker.EntityLookupAction;
import io.jmix.ui.component.EntityComboBox;
import io.jmix.ui.component.EntityPicker;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
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